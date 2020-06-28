package com.dinokeylas.melijoonline.presenter

import com.dinokeylas.melijoonline.contract.RegisterContract
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.USER
import com.dinokeylas.melijoonline.util.MD5
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterPresenter(_view: RegisterContract.View) : RegisterContract.Presenter {

    private var view: RegisterContract.View = _view


    override fun register(user: User) {
        val mAuth = FirebaseAuth.getInstance()

        if (isValidInput(user)) {
            view.showProgressBar()
            mAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
                saveData(user)
            }.addOnFailureListener {
                onRegisterFailure()
            }
        }

    }

    override fun isValidInput(user: User): Boolean {
        return view.validateInput(user)
    }

    override fun saveData(user: User) {
        val mUser = FirebaseAuth.getInstance().currentUser
        val fireStore = FirebaseFirestore.getInstance()
        val userID = mUser?.uid

        fireStore.collection(USER).document(userID ?: "default").set(createNewUser(user))
            .addOnSuccessListener {
                onRegisterSuccess()
            }.addOnFailureListener {
            onRegisterFailure()
        }

    }

    override fun onRegisterSuccess() {
        view.showToastMessage("Registrasi Berhasil")
        view.hideProgressBar()
    }

    override fun onRegisterFailure() {
        view.showToastMessage("Registrasi gagal, silahkan coba kembali")
        view.hideProgressBar()
    }


    //create user with encrypted password
    private fun createNewUser(user: User): User {
        return User(
            user.userName,
            user.fullName,
            user.email,
            user.address,
            user.phoneNumber,
            user.profileImageUrl,
            MD5.encript(user.password)
        )
    }
}
