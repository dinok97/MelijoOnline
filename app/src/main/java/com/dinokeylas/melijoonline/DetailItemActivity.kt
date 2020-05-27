package com.dinokeylas.melijoonline

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.model.Transaction
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.util.Constant
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.TRANSACTION
import com.dinokeylas.melijoonline.util.Constant.TransactionProgress.Companion.IN_TROLLEY
import com.dinokeylas.melijoonline.util.IdGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_item.*
import kotlinx.android.synthetic.main.layout_item_sell.tv_item_name
import kotlinx.android.synthetic.main.layout_item_sell.tv_item_price
import java.util.*

class DetailItemActivity : AppCompatActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid
        loadUserInformation(userId)

        var quantity = 0
        var totalPay = 0

        val itemName = intent.getStringExtra("itemName")
        val itemPrice = intent.getIntExtra("itemPrice", 0)
        val itemDesc = intent.getStringExtra("itemDesc")
        val sellerName = intent.getStringExtra("sellerName")
        val unitSale = intent.getStringExtra("unitSale")
        val discount = intent.getIntExtra("discount", 0)
        val imageUrl = intent.getStringExtra("imageUrl")
        val actualPrice = itemPrice - (itemPrice.toDouble() / 100)

        tv_item_name.text = itemName
        tv_item_desc.text = itemDesc
        tv_quantity.text = quantity.toString()
        tv_total_pay.text = String.format("Rp $totalPay,-")
        Glide.with(this).load(imageUrl).into(iv_detail_item)

        if (discount != 0) {
            tv_item_price.text = String.format("Rp ${actualPrice.toInt()} / $unitSale")
            tv_discount.text = String.format("$discount%%")
            tv_item_price_streak.text = String.format("Rp $itemPrice")
        } else {
            tv_item_price_streak.visibility = View.GONE
            tv_discount.visibility = View.GONE
            tv_item_price.text = String.format("Rp $itemPrice / $unitSale")
        }

        btn_plus.setOnClickListener {
            totalPay = (++quantity) * actualPrice.toInt()
            tv_total_pay.text = String.format("Rp $totalPay")
            tv_quantity.text = quantity.toString()
        }

        btn_minus.setOnClickListener {
            if ((quantity - 1) >= 0) {
                totalPay = (--quantity) * actualPrice.toInt()
                tv_total_pay.text = String.format("Rp $totalPay")
                tv_quantity.text = quantity.toString()
            } else {
                Toast.makeText(this, "Maaf, jumlah tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        btn_add_to_bucket.setOnClickListener {
            val transactionCode = IdGenerator.generateId(Random())
            val date = Calendar.getInstance().time
            val transaction = Transaction(
                "tranId", transactionCode, userId!!, user.email, date,
                itemName, itemPrice, imageUrl, user.address, quantity, totalPay, sellerName,
                false, IN_TROLLEY
            )
            if (isValidData(transaction)) {
                showInformationDialog(transaction)
            } else {
                Toast.makeText(
                    this,
                    "Harap lengkapi data pemesanan\nJumlah tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showInformationDialog(transaction: Transaction) {
        val message = "\n" +
                "Nama: ${transaction.itemName}\n" +
                "Jumlah: ${transaction.itemQty}\n" +
                "Total Bayar: Rp ${transaction.totalPay},-\n\n" +
                "Pastikan alamat Anda sudah benar saat melakukan pemesanan ini"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detail Item")
        builder.setMessage(message)
        builder.setPositiveButton("Oke") { _, _ ->
            progress_bar.visibility = View.VISIBLE
            uploadTransactionData(transaction)
        }
        builder.setNeutralButton("Batal") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInformationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informasi")
        builder.setMessage("Permintaan Berhasil Diproses\nSilahkan menuju ke menu Keranjang untuk melanjutkan pemesanan")
        builder.setPositiveButton("Oke") { _, _ ->
            finish()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun uploadTransactionData(transaction: Transaction) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(TRANSACTION).add(transaction).addOnSuccessListener {
            progress_bar.visibility = View.GONE
            showInformationDialog()
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal memproses transaksi anda", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidData(transaction: Transaction): Boolean {
        if ((transaction.itemQty != 0) && (transaction.totalPay != 0)) {
            return true
        }
        return false
    }

    private fun loadUserInformation(userId: String?) {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(Constant.Collection.USER).document(userId ?: "user").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    user = document.toObject(User::class.java)!!
                } else {
                    Log.d("USER-DATA", "fail to catch user data")
                }
            }.addOnFailureListener {
                Log.d("USER-DATA", "failure catch data from firebase")
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
