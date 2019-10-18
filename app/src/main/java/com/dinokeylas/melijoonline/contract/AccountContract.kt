package com.dinokeylas.melijoonline.contract

import com.dinokeylas.melijoonline.model.User

interface AccountContract {

    interface View {
        fun fillDataToLayout(user: User?)
        fun navigateToAccountDetail()
        fun navigateToLogin()
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun loadUserData()
        fun fillUserDataToLayout(user: User?)
        fun logout()
    }

}