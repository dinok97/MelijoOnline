package com.dinokeylas.melijoonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.adapter.ItemAdapter
import com.dinokeylas.melijoonline.contract.FruitContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.presenter.FruitPresenter
import com.dinokeylas.melijoonline.util.GridItemDecoration
import kotlinx.android.synthetic.main.activity_sea_food.*
import java.util.*
import kotlin.collections.ArrayList

class FruitActivity : AppCompatActivity(), FruitContract.View {

    private lateinit var fruitPresenter: FruitPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)

        fruitPresenter = FruitPresenter(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Daftar Buah"
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
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
