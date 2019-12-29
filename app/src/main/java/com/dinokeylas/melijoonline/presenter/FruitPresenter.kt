package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.FruitContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.FRUIT
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.ITEM
import com.google.firebase.firestore.FirebaseFirestore

class FruitPresenter(_view: FruitContract.View): FruitContract.Presenter {

    private val view: FruitContract.View = _view

    init {
        loadFruitData()
    }

    override fun loadFruitData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(ITEM).whereEqualTo("category", FRUIT).get()
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