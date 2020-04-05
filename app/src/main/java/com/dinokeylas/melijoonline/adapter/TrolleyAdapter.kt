package com.dinokeylas.melijoonline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Transaction

class TrolleyAdapter(
    private val context: Context,
    private val transactionList: ArrayList<Transaction>,
    private val itemClickListener: (Transaction) -> Unit
) :
    RecyclerView.Adapter<TrolleyAdapter.TrolleyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrolleyViewHolder {
        return TrolleyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_transaction_detail, parent, false
            )
        )
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TrolleyViewHolder, position: Int) {
        holder.bind(transactionList[position], itemClickListener, context)
    }

    class TrolleyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvItemQty: TextView = itemView.findViewById(R.id.tv_item_qty)
        var tvItemPrise: TextView = itemView.findViewById(R.id.tv_item_prise)
        var ivItemImage: ImageView = itemView.findViewById(R.id.iv_item_image)
        var btnDeleteItem: ImageView = itemView.findViewById(R.id.btn_delete)

        fun bind(
            tranItem: Transaction,
            itemClickListener: (Transaction) -> Unit,
            context: Context
        ) {
            val itemPrice = tranItem.itemPrise
            val itemQty = tranItem.itemQty
            val totalItemPrice = itemPrice * itemQty
            val itemQtyxPrice = String.format("$itemQty x $itemPrice")

            tvItemName.text = tranItem.itemName
            tvItemQty.text = itemQtyxPrice
            tvItemPrise.text = String.format("Rp $totalItemPrice,-")
            Glide.with(context).load(tranItem.imageUrl).into(ivItemImage)
            btnDeleteItem.setOnClickListener {
                itemClickListener(tranItem)
            }
        }
    }
}