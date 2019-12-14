package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.VegetableContract
import com.dinokeylas.melijoonline.model.Vegetable
import com.dinokeylas.melijoonline.util.Constant
import com.google.firebase.firestore.FirebaseFirestore

class VegetablePresenter(_view: VegetableContract.View): VegetableContract.Presenter {

    private val view: VegetableContract.View = _view

    init {
        loadVegetableData()
    }

    override fun loadVegetableData() {
        view.showProgressBar()
        val vegetableList = ArrayList<Vegetable>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(Constant.Collection.VEGETABLE).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val vegetable: Vegetable? = document.toObject(Vegetable::class.java)
                vegetableList.add(vegetable!!)
            }
            view.onDataLoaded(vegetableList)
            view.hideProgressBar()
        }.addOnFailureListener {
            Log.d("LOG", "data gagal diambil")
        }
    }
}