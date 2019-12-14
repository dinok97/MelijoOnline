package com.dinokeylas.melijoonline.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.DetailVegetableActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Vegetable

class VegetableAdapter(private val context: Context, private val vegetableList: ArrayList<Vegetable>):
    RecyclerView.Adapter<VegetableAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_vegetable_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = vegetableList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tvVegetableName.text = vegetableList[position].name
        holder.tvVegetablePrice.text = vegetableList[position].price.toString()
        Glide.with(context).load(vegetableList[position].imageUrl).into(holder.ivVegetable)
        holder.cvVegetable.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cvVegetable: CardView = itemView.findViewById(R.id.cv_vegetable_item)
        var tvVegetableName: TextView = itemView.findViewById(R.id.tv_vegetable_name)
        var tvVegetablePrice: TextView = itemView.findViewById(R.id.tv_vegetable_price)
        var ivVegetable: ImageView = itemView.findViewById(R.id.iv_vegetable_item)
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener{
        return View.OnClickListener {
//            Toast.makeText(context, vegetableList[position].toString(), Toast.LENGTH_SHORT).show()
            moveToDetailVegetable(vegetableList[position])
        }
    }

    private fun moveToDetailVegetable(vegetable: Vegetable){
        val nextScreenIntent = Intent(context, DetailVegetableActivity::class.java).apply {
                putExtra("vegetableName", vegetable.name)
                putExtra("vegetablePrice", vegetable.price)
                putExtra("vegetableDesc", vegetable.description)
            }
        context.startActivity(nextScreenIntent)
    }

}