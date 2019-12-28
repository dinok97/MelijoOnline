package com.dinokeylas.melijoonline.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.DetailItemActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Item

class ItemAdapter(private val context: Context, private val itemList: ArrayList<Item>):
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_sell, parent, false
            )
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemPrice = itemList[position].price
        holder.tvItemName.text = itemList[position].name
        holder.tvItemPrice.text = String.format("Rp $itemPrice,-")
        Glide.with(context).load(itemList[position].imageUrl).into(holder.ivItem)
        holder.cvItem.setOnClickListener(onClickListener(position))
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener{
        return View.OnClickListener {
            // Toast.makeText(context, vegetableList[position].toString(), Toast.LENGTH_SHORT).show()
            moveToItemDetail(itemList[position])
        }
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cvItem: CardView = itemView.findViewById(R.id.cv_item)
        var tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvItemPrice: TextView = itemView.findViewById(R.id.tv_item_price)
        var ivItem: ImageView = itemView.findViewById(R.id.iv_item)
    }

    private fun moveToItemDetail(item: Item){
        val nextScreenIntent = Intent(context, DetailItemActivity::class.java).apply {
            putExtra("itemName", item.name)
            putExtra("itemPrice", item.price)
            putExtra("itemDesc", item.description)
            putExtra("sellerName", item.sellerName)
            putExtra("imageUrl", item.imageUrl)
        }
        context.startActivity(nextScreenIntent)
    }
}