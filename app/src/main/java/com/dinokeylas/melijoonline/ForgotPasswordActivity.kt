package com.dinokeylas.melijoonline

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dinokeylas.melijoonline.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btn_send.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val emailAddress = et_email.text.toString()
            progress_bar.visibility = View.VISIBLE
            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progress_bar.visibility = View.GONE
                        showInformationDialog(
                            "Email berhasil dikirim\nCek email Anda untuk melakukan perubahan kata sandi",
                            0
                        )
                    } else {
                        progress_bar.visibility = View.GONE
                        showInformationDialog("Gagal mengirim email perubahan kata sandi", 1)
                    }
                }
        }
    }

    private fun showInformationDialog(message: String, flag: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Perhatian")
        builder.setMessage(message)
        builder.setPositiveButton("Oke") { _, _ -> if (flag == 0) navigateToLogin() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
