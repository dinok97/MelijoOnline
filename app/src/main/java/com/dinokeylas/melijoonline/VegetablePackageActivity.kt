package com.dinokeylas.melijoonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.adapter.ItemAdapter
import com.dinokeylas.melijoonline.contract.VegetablePackageContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.presenter.VegetablePackagePresenter
import com.dinokeylas.melijoonline.util.GridItemDecoration
import kotlinx.android.synthetic.main.activity_sea_food.*
import kotlin.collections.ArrayList

class VegetablePackageActivity : AppCompatActivity(), VegetablePackageContract.View {

    private lateinit var vegetablePackagePresenter: VegetablePackagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetable_package)

        vegetablePackagePresenter = VegetablePackagePresenter(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Daftar Paket Sayur"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDataLoaded(itemList: ArrayList<Item>) {
        val recyclerView: RecyclerView = findViewById(R.id.rv_item_list)
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
