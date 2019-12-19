package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.SeasoningContract
import com.dinokeylas.melijoonline.model.Seasoning
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.SEASONING
import com.google.firebase.firestore.FirebaseFirestore

class SeasoningPresenter(_view: SeasoningContract.View): SeasoningContract.Presenter {

    private val view: SeasoningContract.View = _view

    init {
        loadSeasoningData()
    }

    override fun loadSeasoningData() {
        view.showProgressBar()
        val seasoningList = ArrayList<Seasoning>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(SEASONING).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val seasoning: Seasoning? = document.toObject(Seasoning::class.java)
                seasoningList.add(seasoning!!)
                Log.d("DATA", seasoning.toString())
            }
            view.onDataLoaded(seasoningList)
            view.hideProgressBar()
        }.addOnFailureListener {
            Log.d("LOG", "data gagal diambil")
        }
    }

}