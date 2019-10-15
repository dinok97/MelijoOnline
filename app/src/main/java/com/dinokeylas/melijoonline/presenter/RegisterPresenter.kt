package com.dinokeylas.melijoonline.presenter

import com.dinokeylas.melijoonline.contract.RegisterContract

class RegisterPresenter(_view: RegisterContract.View): RegisterContract.Presenter{

    private var view : RegisterContract.View = _view

    override fun validateInput(
        fullName: String,
        email: String,
        address: String,
        phoneNumber: String,
        password: String,
        passwordValidation: String
    ) {
        if(view.validateInput(fullName, email, address, phoneNumber, password, passwordValidation)){
            view.showProgressBar()
        }
    }

    override fun register() {

    }
}