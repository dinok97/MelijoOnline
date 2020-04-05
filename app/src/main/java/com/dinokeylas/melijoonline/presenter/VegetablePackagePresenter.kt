package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.VegetablePackageContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.VEGETABLE_PACKAGE
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.ITEM
import com.google.firebase.firestore.FirebaseFirestore

class VegetablePackagePresenter(_view: VegetablePackageContract.View): VegetablePackageContract.Presenter {

    private val view: VegetablePackageContract.View = _view

    init {
        loadVegetablePackageData()
    }

    override fun loadVegetablePackageData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        FirebaseFirestore.getInstance().collection(ITEM).whereEqualTo("isAvailable", true)
            .whereEqualTo("category", VEGETABLE_PACKAGE).get()
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