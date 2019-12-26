package com.dinokeylas.melijoonline

import android.net.Uri
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
import android.content.pm.PackageManager
import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {

    private val CHOOSE_IMAGE = 101
    private var uriProfileImage: Uri? = null
    private lateinit var profileImageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val mUser = FirebaseAuth.getInstance().currentUser
        var userId = ""
        if (mUser != null) userId = mUser.uid
        getUserData(userId)

        civ_profile_image.setOnClickListener { showImageChooser() }
        btn_update.setOnClickListener { updateProfile(userId) }

    }

    private fun getUserData(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection(USER).document(userId).get().addOnSuccessListener { document ->
            if (document != null) {
                val user = document.toObject(User::class.java)!!
                fillLayout(user)
            } else {
                Log.d("USER-DATA", "fail to catch user data")
            }
        }.addOnFailureListener {
            TODO("if something goes wrong, do something here")
        }
    }

    private fun showImageChooser() {
        if (isPermissionGranted()) {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(
                Intent.createChooser(galleryIntent, "Pilih foto profil Anda"),
                CHOOSE_IMAGE
            )
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission.READ_EXTERNAL_STORAGE), 0)
        }
    }

    /*method to ask storage access permission */
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun fillLayout(user: User?) {
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

    private fun updateProfile(userId: String) {
        progress_bar.visibility = View.VISIBLE
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection(USER).document(userId)
        ref.update("userName", et_user_name.text.toString().trim())
        ref.update("fullName", et_full_name.text.toString().trim())
        ref.update("address", et_address.text.toString().trim())
        ref.update("profileImageUrl", profileImageUrl)
        ref.update("phoneNumber", et_phone_number.text.toString().trim())
            .addOnSuccessListener {
                progress_bar.visibility = View.GONE
                Toast.makeText(this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Pastikan Anda Terkoneksi dengan Internet", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            uriProfileImage = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriProfileImage)
                civ_profile_image.setImageBitmap(bitmap)
                uploadImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        val mStorageRef = FirebaseStorage.getInstance()
            .getReference("profileImage/" + System.currentTimeMillis() + ".jpg")
        progress_bar.visibility = View.VISIBLE

        val uploadTask = mStorageRef.putFile(uriProfileImage!!)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            mStorageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progress_bar.visibility = View.GONE
                val downloadUri = task.result
                profileImageUrl = downloadUri!!.toString()
            } else {
                Toast.makeText(this, "Gagal Update Profile", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
