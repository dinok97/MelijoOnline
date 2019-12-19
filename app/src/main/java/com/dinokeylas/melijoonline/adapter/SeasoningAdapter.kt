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
import com.dinokeylas.melijoonline.DetailSeasoningActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Seasoning

class SeasoningAdapter(private val context: Context, private val seasoningList: ArrayList<Seasoning>):
    RecyclerView.Adapter<SeasoningAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_seasoning_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = seasoningList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val seasoningPrise = seasoningList[position].price
        holder.tvSeasoningName.text = seasoningList[position].name
        holder.tvSeasoningPrise.text = String.format("Rp $seasoningPrise,-")
        Glide.with(context).load(seasoningList[position].imageUrl).into(holder.ivSeasoning)
        holder.cvSeasoning.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cvSeasoning: CardView = itemView.findViewById(R.id.cv_seasoning_item)
        var tvSeasoningName: TextView = itemView.findViewById(R.id.tv_seasoning_name)
        var tvSeasoningPrise: TextView = itemView.findViewById(R.id.tv_seasoning_price)
        var ivSeasoning: ImageView = itemView.findViewById(R.id.iv_seasoning_item)
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener{
        return View.OnClickListener {
            //  Toast.makeText(context, vegetableList[position].toString(), Toast.LENGTH_SHORT).show()
            moveToDetailVegetable(seasoningList[position])
        }
    }

    private fun moveToDetailVegetable(seasoning: Seasoning){
        val nextScreenIntent = Intent(context, DetailSeasoningActivity::class.java).apply {
            putExtra("seasoningName", seasoning.name)
            putExtra("seasoningPrice", seasoning.price)
            putExtra("seasoningDesc", seasoning.description)
            putExtra("sellerName", seasoning.sellerName)
            putExtra("imageUrl", seasoning.imageUrl)
        }
        context.startActivity(nextScreenIntent)
    }
}