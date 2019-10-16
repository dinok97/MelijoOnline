package com.dinokeylas.melijoonline.contract

import com.dinokeylas.melijoonline.model.User

interface RegisterContract {

    interface View{
        fun validateInput(user: User): Boolean
        fun showToastMessage(message: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToHome()
        fun navigateToLogin()
    }

    interface Presenter {
        fun isValidInput(user: User): Boolean
        fun register(user: User)
        fun saveData(user: User)
        fun onRegisterSuccess()
        fun onRegisterFailure()
    }

}