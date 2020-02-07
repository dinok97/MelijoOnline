package com.dinokeylas.melijoonline

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.adapter.TransactionBundleAdapter
import com.dinokeylas.melijoonline.model.TransactionBundle
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.TRANSACTION_BUNDLE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_transaction.*

class TransactionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val SEDANG_DIPROSES = "Sedang Diproses"
    private val RIWAYAT = "Riwayat"

    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoTransaction: TextView
    private lateinit var recyclerView: RecyclerView
    private var transactionBundleList = ArrayList<TransactionBundle>()
    private var transactionDeliveryList = ArrayList<TransactionBundle>()
    private var transactionHistoryList = ArrayList<TransactionBundle>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid
        getTransactionBundleData(userId)

        recyclerView = view.findViewById(R.id.rv_transaction_bundle)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.progress_bar)
        tvNoTransaction = view.findViewById(R.id.tv_no_transaction)
        tvNoTransaction.visibility = View.GONE

        val spinner = view.findViewById<Spinner>(R.id.spinner_transaction_progress)
        val arrayAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.transaction_progress,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

        return view
    }

    private fun getTransactionBundleData(userId: String?) {
        val db = FirebaseFirestore.getInstance()
        db.collection(TRANSACTION_BUNDLE).whereEqualTo("userId", userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tranBundle: TransactionBundle = document.toObject(TransactionBundle::class.java)
                    tranBundle.tranBundleId = document.id
                    transactionBundleList.add(tranBundle)
                    Log.d("DATA", tranBundle.toString())
                }
//                if (transactionList.size > 0) showTranData(transactionList)
//                else showNoTransactionText()
                progressBar.visibility = View.GONE
                divideList(transactionBundleList)
            }.addOnFailureListener {
                Log.d("DATA", "data gagal diambil")
            }
    }

    private fun divideList(transactionBundleList: ArrayList<TransactionBundle>) {
        for (tran in transactionBundleList) {
            if (tran.transactionBundleProgress == "ready to deliver") {
                transactionDeliveryList.add(tran)
            } else {
                transactionHistoryList.add(tran)
            }
        }

        setAdapter(transactionDeliveryList)
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
        val item = adapterView?.getItemAtPosition(position).toString()

        if (item == SEDANG_DIPROSES) {
//            if(transactionDeliveryList.isEmpty()) tvNoTransaction.visibility = View.VISIBLE
            setAdapter(transactionDeliveryList)
        }
        if (item == RIWAYAT) {
//            if(transactionHistoryList.isEmpty()) tvNoTransaction.visibility = View.VISIBLE
            setAdapter(transactionHistoryList)
        }
    }

    private fun setAdapter(tranBundleList: ArrayList<TransactionBundle>) {
        if(tranBundleList.isEmpty()) tvNoTransaction.visibility = View.VISIBLE
        else tvNoTransaction.visibility = View.GONE
        val transactionBundleAdapter = TransactionBundleAdapter(context!!, tranBundleList)
        recyclerView.adapter = transactionBundleAdapter
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}
