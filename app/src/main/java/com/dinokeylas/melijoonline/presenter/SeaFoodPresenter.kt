package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.SeaFoodContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.SEAFOOD
import com.google.firebase.firestore.FirebaseFirestore

class SeaFoodPresenter(_view: SeaFoodContract.View) : SeaFoodContract.Presenter {

    private val view: SeaFoodContract.View = _view

    init {
        loadSeaFoodData()
    }

    override fun loadSeaFoodData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(Constant.Collection.ITEM).whereEqualTo("category", SEAFOOD).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val itemSell: Item? = document.toObject(Item::class.java)
                    itemList.add(itemSell!!)
                    Log.d("DATA", itemSell.toString())
                }
                view.onDataLoaded(itemList)
                view.hideProgressBar()
            }.addOnFailureListener {
            Log.d("LOG", "data gagal diambil")
        }
    }
}