package com.dinokeylas.melijoonline.presenter

import android.util.Log
import com.dinokeylas.melijoonline.contract.AccountContract
import com.dinokeylas.melijoonline.util.Constant.Collection
import com.dinokeylas.melijoonline.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountPresenter (_view: AccountContract.View): AccountContract.Presenter{

    private val view: AccountContract.View = _view

    init {
        loadUserData()
    }

    override fun loadUserData() {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid

//        view.showProgressBar()
        firebaseFirestore.collection(Collection.USER).document(userId ?: "user").get()
            .addOnSuccessListener {document ->
                if(document!=null){
                    val user: User? = document.toObject(User::class.java)
                    fillUserDataToLayout(user)
                } else {
                    //show toast message
                    Log.d("USER-DATA", "fail to catch user data")
                }
            }.addOnFailureListener {
                //show toast message
                Log.d("USER-DATA", "failure catch data from firebase")
            }
    }

    override fun fillUserDataToLayout(user: User?) {
        view.fillDataToLayout(user)
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
        view.navigateToLogin()
    }

}