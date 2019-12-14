package com.dinokeylas.melijoonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.adapter.VegetableAdapter
import com.dinokeylas.melijoonline.contract.VegetableContract
import com.dinokeylas.melijoonline.model.Vegetable
import com.dinokeylas.melijoonline.presenter.VegetablePresenter
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.VEGETABLE
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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

    override fun onDataLoaded(vegetableList: ArrayList<Vegetable>) {
        val recyclerView: RecyclerView = findViewById(R.id.rv_vegetable)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(GridItemDecoration(10,2))

        val adapter = VegetableAdapter(this, vegetableList)
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