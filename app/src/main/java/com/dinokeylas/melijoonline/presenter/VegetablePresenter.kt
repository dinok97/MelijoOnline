package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.VegetableContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.VEGETABLE
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.ITEM
import com.dinokeylas.melijoonline.util.Constant.Field.Companion.IS_AVAILABLE
import com.google.firebase.firestore.FirebaseFirestore

class VegetablePresenter(_view: VegetableContract.View): VegetableContract.Presenter {

    private val view: VegetableContract.View = _view

    init {
        loadVegetableData()
    }

    override fun loadVegetableData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        FirebaseFirestore.getInstance().collection(ITEM).whereEqualTo(IS_AVAILABLE, true)
            .whereEqualTo("category", VEGETABLE).get()
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