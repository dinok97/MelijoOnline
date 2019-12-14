package com.dinokeylas.melijoonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail_vegetable.*
import kotlinx.android.synthetic.main.layout_vegetable_item.*
import kotlinx.android.synthetic.main.layout_vegetable_item.tv_vegetable_name
import kotlinx.android.synthetic.main.layout_vegetable_item.tv_vegetable_price

class DetailVegetableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_vegetable)

        var quantity = 0
        var totalPay = 0

        val vegetableName = intent.getStringExtra("vegetableName")
        val vegetablePrice = intent.getIntExtra("vegetablePrice", 0)
        val vegetableStringPrice = "Rp $totalPay,-"
        val vegetableDesc = intent.getStringExtra("vegetableDesc")

        tv_vegetable_name.text = vegetableName
        tv_vegetable_price.text = vegetablePrice.toString()
        tv_vegetable_desc.text = vegetableDesc
        tv_quantity.text = quantity.toString()
        tv_total_pay.text = vegetableStringPrice

        btn_plus.setOnClickListener {
            totalPay = (++quantity)*vegetablePrice
            tv_total_pay.text = totalPay.toString()
            tv_quantity.text = quantity.toString()
        }

        btn_minus.setOnClickListener {
            if ((quantity-1)>=0){
                totalPay = (--quantity)*vegetablePrice
                tv_total_pay.text = totalPay.toString()
                tv_quantity.text = quantity.toString()
            } else {
                Toast.makeText(this, "Maaf, jumlah tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        btn_add_to_bucket.setOnClickListener {

        }
    }
}
