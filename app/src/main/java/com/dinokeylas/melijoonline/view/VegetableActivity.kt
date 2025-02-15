package com.dinokeylas.melijoonline.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.util.GridItemDecoration
import com.dinokeylas.melijoonline.HomeActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.adapter.ItemAdapter
import com.dinokeylas.melijoonline.contract.VegetableContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.presenter.VegetablePresenter
import kotlinx.android.synthetic.main.activity_vegetable.*
import java.util.*
import kotlin.collections.ArrayList

class VegetableActivity : AppCompatActivity(), VegetableContract.View {

    private lateinit var vegetablePresenter: VegetablePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetable)

        vegetablePresenter = VegetablePresenter(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Daftar Sayur"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onDataLoaded(itemList: ArrayList<Item>) {
        val recyclerView: RecyclerView = findViewById(R.id.rv_vegetable)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(GridItemDecoration(10, 2))

        val adapter = ItemAdapter(this, itemList)
        recyclerView.adapter = adapter
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun onBackPressed(){
        super.onBackPressed()
            startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


//    fun produceList(): List<Vegetable>{
//        val veg1 = Vegetable("vegetable1", 2500, "coba", "Dino", "noImage")
//        val veg2 = Vegetable("vegetable2", 2000, "coba", "Akbar", "noImage")
//        val veg3 = Vegetable("vegetable3", 1500, "coba", "Dino", "noImage")
//        val veg4 = Vegetable("vegetable4", 2200, "coba", "Akbar", "noImage")
//        val list = mutableListOf<Vegetable>(veg1, veg2, veg3, veg4)
//        return list
//    }