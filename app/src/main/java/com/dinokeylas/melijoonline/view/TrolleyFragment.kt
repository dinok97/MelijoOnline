package com.dinokeylas.melijoonline.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.HomeActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.adapter.TrolleyAdapter
import com.dinokeylas.melijoonline.model.Transaction
import com.dinokeylas.melijoonline.util.Constant
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.TRANSACTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import kotlinx.android.synthetic.main.fragment_trolley.*

class TrolleyFragment : Fragment() {

    private val TRANSACTION_PROGRESS = "transactionProgress"
    private val USER_EMAIL = "userEmail"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrolleyAdapter
    private lateinit var transactionList: ArrayList<Transaction>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_trolley, container, false)

        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid
        loadUserData(userId)

        recyclerView = view.findViewById(R.id.rv_transaction_item)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val btnMakeRequest = view.findViewById<Button>(R.id.btn_make_request)
        btnMakeRequest.setOnClickListener {
            showInformationDialog()
        }

        return view
    }

    private fun loadUserData(userId: String?) {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(Constant.Collection.USER).document(userId ?: "user").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user: User = document.toObject(User::class.java)!!
                    showUserData(user)
                    getTransactionData(user)
                } else {
                    Log.d("USER-DATA", "fail to catch user data")
                }
            }.addOnFailureListener {
                Log.d("USER-DATA", "failure catch data from firebase")
            }
    }

    private fun showUserData(user: User) {
        tv_user_name.text = user.userName
        tv_phone_number.text = user.phoneNumber
        tv_address.text = user.address
    }

    private fun getTransactionData(user: User) {
        transactionList = ArrayList()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(TRANSACTION).whereEqualTo(USER_EMAIL, user.email)
            .whereEqualTo(TRANSACTION_PROGRESS, "inTrolley").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val transaction: Transaction = document.toObject(Transaction::class.java)
                    transaction.transactionId = document.id
                    transactionList.add(transaction)
//                Log.d("DATA", transaction.toString())
                }
                if (transactionList.size > 0) showTranData(transactionList)
                else showNoTransactionText()
//            adapter.notifyDataSetChanged()
            }.addOnFailureListener {
                Log.d("LOG", "data gagal diambil")
            }
    }

    private fun showTranData(transactionList: ArrayList<Transaction>) {
        adapter = TrolleyAdapter(context!!, transactionList)
        recyclerView.adapter = adapter
        progress_bar.visibility = View.GONE

        var total = 0
        for (tran in transactionList) {
            total += (tran.itemQty * tran.itemPrise)
        }
        tv_total_pay.text = String.format("Rp $total,-")
        tv_delivery_fee.text = String.format("Rp 3000,-")
    }

    private fun showNoTransactionText() {
        progress_bar.visibility = View.GONE
        tv_no_transaction.visibility = View.VISIBLE
    }

    private fun updateTransactionData() {
        progress_bar.visibility = View.VISIBLE
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val batch = db.batch()
        for (tran in transactionList) {
            val ref = db.collection(TRANSACTION).document(tran.transactionId)
            updateTransactionDocument(ref, batch)
        }
        batch.commit().addOnCompleteListener {
            progress_bar.visibility = View.GONE
            showInformationDialog("Yey, pesanan kamu sedang diproses :)\ntunggu pesan WA dari kami ya")
        }
    }

    private fun updateTransactionDocument(ref: DocumentReference, batch: WriteBatch) {
        batch.update(ref, TRANSACTION_PROGRESS, "ordered")
    }

    private fun showInformationDialog() {
        val message = "Pastikan pesanan kamu sudah benar ya saat melanjutkan proses ini :)\n\n" +
                "Pastikan alamat kamu benar (lihat di profil), sehingga kami dapat mengantarkan pesanan dengan tepat."
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Perhatian")
        builder.setMessage(message)
        builder.setPositiveButton("Lanjutkan") { _, _ -> updateTransactionData() }
        builder.setNeutralButton("Batal") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInformationDialog(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Perhatian")
        builder.setMessage(message)
        builder.setPositiveButton("Oke") { _, _ ->
            startActivity(Intent(context, HomeActivity::class.java))
        }
        builder.setNeutralButton("Batal") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return TrolleyFragment()
        }
    }
}
