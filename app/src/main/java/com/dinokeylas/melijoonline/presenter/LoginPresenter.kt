package com.dinokeylas.melijoonline.presenter

import com.dinokeylas.melijoonline.contract.LoginContract
import com.dinokeylas.melijoonline.util.MD5
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(_view: LoginContract.View): LoginContract.Presenter{

    private val view: LoginContract.View = _view

    override fun login(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()

        if (isValidInput(email, password)){
            view.showProgressBar()
            mAuth.signInWithEmailAndPassword(email, MD5.encript(password))
                .addOnSuccessListener {
                    onLoginSuccess()
                }
                .addOnFailureListener {
                    onLoginFailure()
                }
        }
    }

    override fun isValidInput(email: String, password: String): Boolean {
        return view.validateInput(email, password)
    }

    override fun onLoginSuccess() {
        view.hideProgressBar()
        view.showToastMessage("Anda Berhasil Login")
        view.navigateToHome()
    }

    override fun onLoginFailure() {
        view.hideProgressBar()
        view.showToastMessage("Pastikan pasangan email dan password yang anda masukkan adalah benar")
    }

}