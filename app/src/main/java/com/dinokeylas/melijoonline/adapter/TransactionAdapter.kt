package com.dinokeylas.melijoonline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Transaction

class TransactionAdapter(
    private val context: Context,
    private val transactionList: ArrayList<Transaction>
) :
    RecyclerView.Adapter<TransactionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_transaction_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tvDate.text = transactionList[position].date.toString().trim()
        holder.tvTotal.text = transactionList[position].totalPay.toString().trim()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tv_transaction_date)
        val tvTotal: TextView = itemView.findViewById(R.id.tv_transaction_nominal)
        val cvTransaction: CardView = itemView.findViewById(R.id.cv_transaction_item)
    }
}