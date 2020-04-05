package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.SeasoningContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.SEASONING
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.ITEM
import com.google.firebase.firestore.FirebaseFirestore

class SeasoningPresenter(_view: SeasoningContract.View) : SeasoningContract.Presenter {

    private val view: SeasoningContract.View = _view

    init {
        loadSeasoningData()
    }

    override fun loadSeasoningData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        FirebaseFirestore.getInstance().collection(ITEM).whereEqualTo("isAvailable", true)
            .whereEqualTo("category", SEASONING).get()
            .addOnSuccessListener { documents ->
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