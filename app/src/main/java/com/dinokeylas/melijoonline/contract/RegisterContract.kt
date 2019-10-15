package com.dinokeylas.melijoonline.contract

interface RegisterContract {

    interface View{
        fun validateInput(fullName: String, email: String, address: String, phoneNumber: String, password: String, passwordValidation: String): Boolean
        fun onRegisterSuccess()
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToHome()
    }

    interface Presenter {
        fun validateInput(fullName: String, email: String, address: String, phoneNumber: String, password: String, passwordValidation: String)
        fun register()
    }

}