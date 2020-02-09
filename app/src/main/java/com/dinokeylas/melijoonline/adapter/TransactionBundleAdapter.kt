package com.dinokeylas.melijoonline.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.TransactionBundle
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.DATE_FORMAT
import java.text.SimpleDateFormat

class TransactionBundleAdapter(private val context: Context, private val tranBundleList: ArrayList<TransactionBundle>):
    RecyclerView.Adapter<TransactionBundleAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_transaction_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = tranBundleList.size

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT)
        val date = simpleDateFormat.format(tranBundleList[position].date)
        val totalPay = tranBundleList[position].totalPay
        val itemNameList = tranBundleList[position].itemNameList
        var itemNames = ""
        for(iName in itemNameList){ itemNames += iName }

        holder.tvItemName.text = itemNames
        holder.tvDate.text = date
        holder.tvTotalPrice.text = String.format("Rp $totalPay")
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cvTransaction: CardView = itemView.findViewById(R.id.cv_transaction_item)
        val tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDate: TextView = itemView.findViewById(R.id.tv_transaction_date)
        val tvTotalPrice: TextView = itemView.findViewById(R.id.tv_transaction_nominal)
    }
}