package com.dinokeylas.melijoonline.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.HomeActivity
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.adapter.SeasoningAdapter
import com.dinokeylas.melijoonline.contract.SeasoningContract
import com.dinokeylas.melijoonline.model.Seasoning
import com.dinokeylas.melijoonline.presenter.SeasoningPresenter
import com.dinokeylas.melijoonline.util.GridItemDecoration
import kotlinx.android.synthetic.main.activity_vegetable.progress_bar
import java.util.*
import kotlin.collections.ArrayList

class SeasoningActivity : AppCompatActivity(), SeasoningContract.View {

    private lateinit var seasoningPresenter: SeasoningPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seasoning)

        seasoningPresenter = SeasoningPresenter(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Daftar Rempah"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDataLoaded(seasoningList: ArrayList<Seasoning>) {
        val recyclerView: RecyclerView = findViewById(R.id.rv_seasoning)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(GridItemDecoration(10, 2))

        val adapter = SeasoningAdapter(this, seasoningList)
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
