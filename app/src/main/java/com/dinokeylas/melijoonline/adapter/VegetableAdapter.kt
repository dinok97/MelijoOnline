package com.dinokeylas.melijoonline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.model.Vegetable

class VegetableAdapter(private val context: Context, private val vegetableList: List<Vegetable>):
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
        holder.tvVegetableName.text = vegetableList[position].vegetableName
        holder.tvVegetablePrice.text = vegetableList[position].price.toString()
//        holder.tvVegetableName.text = vegetableList[position].vegetableName
        holder.cvVegetable.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cvVegetable: CardView = itemView.findViewById(R.id.cv_vegetable_item)
        var tvVegetableName: TextView = itemView.findViewById(R.id.tv_vegetable_name)
        var tvVegetablePrice: TextView = itemView.findViewById(R.id.tv_vegetable_price)
//        var ivVegetable: ImageView = itemView.findViewById(R.id.iv_vegetable_item)
    }

    private fun onClickListener(position: Int): View.OnClickListener{
        return View.OnClickListener {
            Toast.makeText(context, vegetableList[position].toString(), Toast.LENGTH_SHORT).show()
        }
    }

}