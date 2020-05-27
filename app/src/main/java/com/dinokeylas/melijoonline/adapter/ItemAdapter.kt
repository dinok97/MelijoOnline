package com.dinokeylas.melijoonline.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.DetailItemActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Item

class ItemAdapter(private val context: Context, private val itemList: ArrayList<Item>) :
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
        holder.tvItemName.text = itemList[position].name
        Glide.with(context).load(itemList[position].imageUrl).into(holder.ivItem)
        holder.cvItem.setOnClickListener(onClickListener(position))
        if (itemList[position].discount != 0) {
            holder.tvDiscount.visibility = View.VISIBLE
            holder.tvItemPriceStreak.visibility = View.VISIBLE
            var actualPrice = itemList[position].price.toDouble()
            actualPrice -= (actualPrice * (itemList[position].discount.toDouble() / 100))

            holder.tvItemPrice.text = String.format("Rp ${actualPrice.toInt()}")
            holder.tvDiscount.text = String.format(" ${itemList[position].discount}%%")
            holder.tvItemPriceStreak.text = String.format("Rp ${itemList[position].price}")
        } else {
            holder.tvDiscount.visibility = View.GONE
            holder.tvItemPriceStreak.visibility = View.GONE
            holder.tvItemPrice.text = String.format("Rp ${itemList[position].price},-")
        }
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            // Toast.makeText(context, vegetableList[position].toString(), Toast.LENGTH_SHORT).show()
            moveToItemDetail(itemList[position])
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvItem: CardView = itemView.findViewById(R.id.cv_item)
        var tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvItemPrice: TextView = itemView.findViewById(R.id.tv_item_price)
        var tvDiscount: TextView = itemView.findViewById(R.id.tv_discount)
        var tvItemPriceStreak: TextView = itemView.findViewById(R.id.tv_item_price_streak)
        var ivItem: ImageView = itemView.findViewById(R.id.iv_item)
    }

    private fun moveToItemDetail(item: Item) {
        val nextScreenIntent = Intent(context, DetailItemActivity::class.java).apply {
            putExtra("itemName", item.name)
            putExtra("itemPrice", item.price)
            putExtra("itemDesc", item.description)
            putExtra("sellerName", item.sellerName)
            putExtra("unitSale", item.unitSale)
            putExtra("discount", item.discount)
            putExtra("imageUrl", item.imageUrl)
        }
        context.startActivity(nextScreenIntent)
    }
}