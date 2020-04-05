package com.dinokeylas.melijoonline.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Transaction
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.util.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_vegetable.*
import java.util.*

class DetailVegetableActivity : AppCompatActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_vegetable)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Detail Sayur"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid
        loadUserInformation(userId)

        var quantity = 0
        var totalPay = 0

        val vegetableName = intent.getStringExtra("vegetableName")
        val vegetablePrice = intent.getIntExtra("vegetablePrice", 0)
        val vegetableStringPrice = "Rp $totalPay,-"
        val vegetableDesc = intent.getStringExtra("vegetableDesc")
        val sellerName = intent.getStringExtra("sellerName")
        val imageUrl = intent.getStringExtra("imageUrl")

        tv_vegetable_name.text = vegetableName
        tv_vegetable_price.text = String.format("Rp $vegetablePrice,-")
        tv_vegetable_desc.text = vegetableDesc
        tv_quantity.text = quantity.toString()
        tv_total_pay.text = vegetableStringPrice
        Glide.with(this).load(imageUrl).into(iv_detail_vegetable)

        btn_plus.setOnClickListener {
            totalPay = (++quantity)*vegetablePrice
            tv_total_pay.text = totalPay.toString()
            tv_quantity.text = quantity.toString()
        }

        btn_minus.setOnClickListener {
            if ((quantity-1)>=0){
                totalPay = (--quantity)*vegetablePrice
                tv_total_pay.text = totalPay.toString()
                tv_quantity.text = quantity.toString()
            } else {
                Toast.makeText(this, "Maaf, jumlah tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        btn_add_to_bucket.setOnClickListener {
            val transactionCode = generateString(Random())
            val date = Calendar.getInstance().time
            val transaction = Transaction("tranId", transactionCode, userId!!, user.email, date,
                vegetableName, vegetablePrice, imageUrl, user.address, quantity, totalPay, sellerName,
                false, "inTrolley" )
            if(isValidData(transaction)){
                showInformationDialog(transaction)
            } else {
                Toast.makeText(this, "Harap lengkapi data pemesanan\nJumlah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showInformationDialog(transaction: Transaction){
        val message = "Detail sayur Anda\n\n" +
                "Nama: ${transaction.itemName}\n" +
                "Jumlah: ${transaction.itemQty}\n" +
                "Total Bayar: Rp ${transaction.totalPay},-\n\n" +
                "Pastikan alamat Anda sudah benar saat melakukan pemesanan ini"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detail Sayur")
        builder.setMessage(message)
        builder.setPositiveButton("Oke"){_, _ ->
            progress_bar.visibility = View.VISIBLE
            uploadTransactionData(transaction)
        }
        builder.setNeutralButton("Batal"){_,_ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInformationDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informasi")
        builder.setMessage("Permintaan Berhasil Diproses\nSilahkan menuju ke menu Keranjang untuk melanjutkan pemesanan")
        builder.setPositiveButton("Oke"){_, _ ->
            startActivity(Intent(this, VegetableActivity::class.java))
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun generateString(random: Random): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
        val text = CharArray(6)
        for (i in 0 until 6) {
            text[i] = characters[random.nextInt(characters.length)]
        }
        return String(text)
    }

    private fun uploadTransactionData(transaction: Transaction){
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(Constant.Collection.TRANSACTION).add(transaction)
            .addOnSuccessListener {
            progress_bar.visibility = View.GONE
            showInformationDialog()
        }
            .addOnFailureListener {
            Toast.makeText(this, "Gagal memproses transaksi anda", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidData(transaction: Transaction): Boolean {
        if((transaction.itemQty!=0) && (transaction.totalPay!=0)){ return true }
        return false
    }

    private fun loadUserInformation(userId: String?){
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(Constant.Collection.USER).document(userId ?: "user").get()
            .addOnSuccessListener {document ->
                if(document!=null){
                    user = document.toObject(User::class.java)!!
                } else {
                    Log.d("USER-DATA", "fail to catch user data")
                }
            }.addOnFailureListener {
                Log.d("USER-DATA", "failure catch data from firebase")
            }
    }

    override fun onBackPressed(){
        super.onBackPressed()
        startActivity(Intent(this, VegetableActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}