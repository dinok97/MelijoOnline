package com.dinokeylas.melijoonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinokeylas.melijoonline.adapter.VegetableAdapter
import com.dinokeylas.melijoonline.model.Vegetable
import java.util.*

class VegetableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetable)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.title = "Daftar Sayur"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView: RecyclerView = findViewById(R.id.rv_vegetable)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
//        recyclerView.addItemDecoration(GridItemDecoration(10,2))

        val list: List<Vegetable> = produceList()
        val adapter: VegetableAdapter = VegetableAdapter(this, list)
        recyclerView.adapter = adapter
    }

    fun produceList(): List<Vegetable>{
        val veg1 = Vegetable("vegetable1", 2500, "coba", "Dino", "noImage")
        val veg2 = Vegetable("vegetable2", 2000, "coba", "Akbar", "noImage")
        val veg3 = Vegetable("vegetable3", 1500, "coba", "Dino", "noImage")
        val veg4 = Vegetable("vegetable4", 2200, "coba", "Akbar", "noImage")
        val list = mutableListOf<Vegetable>(veg1, veg2, veg3, veg4)
        return list
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
