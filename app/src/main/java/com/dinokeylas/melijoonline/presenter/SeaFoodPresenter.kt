package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.SeaFoodContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.SEAFOOD
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.ITEM
import com.dinokeylas.melijoonline.util.Constant.Field.Companion.IS_AVAILABLE
import com.google.firebase.firestore.FirebaseFirestore

class SeaFoodPresenter(_view: SeaFoodContract.View) : SeaFoodContract.Presenter {

    private val view: SeaFoodContract.View = _view

    init {
        loadSeaFoodData()
    }

    override fun loadSeaFoodData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        FirebaseFirestore.getInstance().collection(ITEM).whereEqualTo(IS_AVAILABLE, true)
            .whereEqualTo("category", SEAFOOD).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val itemSell: Item? = document.toObject(Item::class.java)
                    itemList.add(itemSell!!)
                    Log.d("DATA", itemSell.toString())
                }
                view.onDataLoaded(itemList)
                view.hideProgressBar()
            }
            .addOnFailureListener { Log.d("LOG", "data gagal diambil") }
    }
}