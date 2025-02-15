package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.MeatContract
import com.dinokeylas.melijoonline.model.Item
import com.dinokeylas.melijoonline.util.Constant.Category.Companion.MEAT
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.ITEM
import com.dinokeylas.melijoonline.util.Constant.Field.Companion.IS_AVAILABLE
import com.google.firebase.firestore.FirebaseFirestore

class MeatPresenter(_view: MeatContract.View): MeatContract.Presenter {

    private val view: MeatContract.View = _view

    init {
        loadMeatData()
    }

    override fun loadMeatData() {
        view.showProgressBar()
        val itemList = ArrayList<Item>()
        FirebaseFirestore.getInstance().collection(ITEM).whereEqualTo("category",MEAT)
            .whereEqualTo(IS_AVAILABLE, true).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val itemSell: Item? = document.toObject(Item::class.java)
                    itemList.add(itemSell!!)
                    Log.d("DATA", itemSell.toString())
                }
                view.onDataLoaded(itemList)
                view.hideProgressBar()
            }.addOnFailureListener { Log.d("LOG", "data gagal diambil") }
    }
}