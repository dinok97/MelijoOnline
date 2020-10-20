package com.dinokeylas.melijoonline.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.util.GridItemDecoration
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.adapter.ItemAdapter
import com.dinokeylas.melijoonline.contract.VegetableContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.presenter.VegetablePresenter
import kotlinx.android.synthetic.main.activity_vegetable.*
import kotlin.collections.ArrayList

class VegetableActivity : AppCompatActivity(), VegetableContract.View {

    private lateinit var vegetablePresenter: VegetablePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetable)

        vegetablePresenter = VegetablePresenter(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Daftar Sayur"
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
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}