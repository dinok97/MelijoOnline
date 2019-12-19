package com.dinokeylas.melijoonline

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.model.Transaction
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.TRANSACTION
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_seasoning.*
import java.util.*

class DetailSeasoningActivity : AppCompatActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_seasoning)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Detail Rempah"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid
        loadUserInformation(userId)

        var quantity = 0
        var totalPay = 0

        val seasoningName = intent.getStringExtra("seasoningName")
        val seasoningPrice = intent.getIntExtra("seasoningPrice", 0)
        val seasoningStringPrice = "Rp $totalPay,-"
        val seasoningDesc = intent.getStringExtra("seasoningDesc")
        val sellerName = intent.getStringExtra("sellerName")
        val imageUrl = intent.getStringExtra("imageUrl")

        tv_seasoning_name.text = seasoningName
        tv_seasoning_price.text = String.format("Rp $seasoningPrice,-")
        tv_seasoning_desc.text = seasoningDesc
        tv_quantity.text = quantity.toString()
        tv_total_pay.text = seasoningStringPrice
        Glide.with(this).load(imageUrl).into(iv_detail_seasoning)

        btn_plus.setOnClickListener {
            totalPay = (++quantity)*seasoningPrice
            tv_total_pay.text = totalPay.toString()
            tv_quantity.text = quantity.toString()
        }

        btn_minus.setOnClickListener {
            if ((quantity-1)>=0){
                totalPay = (--quantity)*seasoningPrice
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
                seasoningName, seasoningPrice, imageUrl, user.address, quantity, totalPay, sellerName,
                false, "inTrolley" )
            if(isValidData(transaction)){
                showInformationDialog(transaction)
            } else {
                Toast.makeText(this, "Harap lengkapi data pemesanan\nJumlah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showInformationDialog(transaction: Transaction){
        val message = "Detail rempah Anda\n\n" +
                "Nama: ${transaction.itemName}\n" +
                "Jumlah: ${transaction.itemQty}\n" +
                "Total Bayar: Rp ${transaction.totalPay},-\n\n" +
                "Pastikan alamat kamu sudah benar ya saat melakukan pemesanan ini"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detail Rempah")
        builder.setMessage(message)
        builder.setPositiveButton("Oke"){_, _ ->
            progress_bar.visibility = View.VISIBLE
            uploadTransactionData(transaction)
        }
        builder.setNeutralButton("Batal"){_,_ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun uploadTransactionData(transaction: Transaction){
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(TRANSACTION).add(transaction)
            .addOnSuccessListener {
                progress_bar.visibility = View.GONE
                showInformationDialog()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memproses transaksi anda", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showInformationDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informasi")
        builder.setMessage("Permintaan Berhasil Diproses\nSilahkan menuju ke menu Keranjang untuk melanjutkan pemesanan")
        builder.setPositiveButton("Oke"){_, _ ->
            startActivity(Intent(this, SeasoningActivity::class.java))
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

    private fun isValidData(transaction: Transaction): Boolean {
        if((transaction.itemQty!=0) && (transaction.totalPay!=0)){ return true }
        return false
    }

    private fun loadUserInformation(userId: String?){
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(USER).document(userId ?: "user").get()
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

}
