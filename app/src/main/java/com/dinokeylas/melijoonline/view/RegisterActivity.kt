package com.dinokeylas.melijoonline.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.contract.RegisterContract
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_email
import kotlinx.android.synthetic.main.activity_register.et_password
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var registerPresenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerPresenter = RegisterPresenter(this)

        btn_register.setOnClickListener{
            registerPresenter.validateInput(
                et_full_name.text.toString(),
                et_email.text.toString(),
                et_address.text.toString(),
                et_phone_number.text.toString(),
                et_password.text.toString(),
                et_password_validation.text.toString()
            )
        }

    }

    override fun validateInput(
        fullName: String,
        email: String,
        address: String,
        phoneNumber: String,
        password: String,
        passwordValidation: String
    ): Boolean {

            //full name should not empty
            if (fullName.isEmpty()){
                et_full_name.error = "Nama Tidak Boleh Kosong"
                et_full_name.requestFocus()
                return false
            }

            //email should not empty
            if (email.isEmpty()){
                et_email.error = "Email Tidak Boleh Kosong"
                et_email.requestFocus()
                return false
            }

            //email should follow pattern
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_email.error = "Format email salah"
                et_email.requestFocus()
                return false
            }

            //address should not empty
            if (address.isEmpty()){
                et_address.error = "Alamat Tidak Boleh Kosong"
                et_address.requestFocus()
                return false
            }

            //phone number should not empty
            if (phoneNumber.isEmpty()){
                et_phone_number.error = "Nomor Telepon Tidak Boleh Kosong"
                et_phone_number.requestFocus()
                return false
            }

            //password should not empty
            if (password.isEmpty()){
                et_password.error = "Kata Sandi Tidak Boleh Kosong"
                et_password.requestFocus()
                return false
            }

            //password contains minimum 6 character
            if (password.length<6){
                et_password.error = "Kata Sandi minimal terdiri dari 6 karakter"
                et_password.requestFocus()
                return false
            }

            //password should not null
            if (passwordValidation.isEmpty()){
                et_password_validation.error = "Kata Sandi Tidak Boleh Kosong"
                et_password_validation.requestFocus()
                return false
            }

            // password and password validation must be same
            if (password != passwordValidation){
                et_password.error = "Kata Sandi Harus Sama"
                et_password.requestFocus()
                return false
            }

        return true
    }

    override fun onRegisterSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun navigateToHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
