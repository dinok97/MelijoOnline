package com.dinokeylas.melijoonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val mUser = FirebaseAuth.getInstance().currentUser
        var userId = ""
        if (mUser != null) userId = mUser.uid

        val db = FirebaseFirestore.getInstance()
        db.collection(USER).document(userId).get().addOnSuccessListener { document ->
            if(document!=null){
                val user = document.toObject(User::class.java)!!
                fillLayout(user)
            } else {
                Log.d("USER-DATA", "fail to catch user data")
            }
        }.addOnFailureListener {
            TODO("if something goes wrong, do something here")
        }

        btn_update.setOnClickListener {
//            updateProfile(userId)
        }

    }

    private fun fillLayout(user: User?){
        et_user_name.setText(user?.userName)
        et_full_name.setText(user?.fullName)
        tv_email_address.text = user?.email
        et_phone_number.setText(user?.phoneNumber)
        et_address.setText(user?.address)
        if (user?.profileImageUrl != null) {
            Glide.with(this)
                .load(user.profileImageUrl)
                .into(civ_profile_image)
        }
    }

    private fun updateProfile(userId: String){
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection(USER).document(userId)
        ref.update("userName", et_user_name.text)
        ref.update("fullName", et_full_name.text)
        ref.update("address", et_address.text)
        ref.update("phoneNumber", et_phone_number.text).addOnSuccessListener {
            Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}
