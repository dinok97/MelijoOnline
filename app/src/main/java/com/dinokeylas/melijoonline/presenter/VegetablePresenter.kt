package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.VegetableContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.VEGETABLE
import com.google.firebase.firestore.FirebaseFirestore

class VegetablePresenter(_view: VegetableContract.View): VegetableContract.Presenter {

    private val view: VegetableContract.View = _view

    init {
        loadVegetableData()
    }

    override fun loadVegetableData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(Constant.Collection.ITEM).whereEqualTo("category", VEGETABLE).get()
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