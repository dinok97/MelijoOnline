package com.dinokeylas.melijoonline.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dinokeylas.melijoonline.R
import com.dinokeylas.melijoonline.contract.AccountContract
import com.dinokeylas.melijoonline.model.User
import com.dinokeylas.melijoonline.presenter.AccountPresenter
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment(), AccountContract.View {

    private lateinit var accountPresenter: AccountPresenter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)

        accountPresenter = AccountPresenter(this)

        view.btn_logout.setOnClickListener {
            accountPresenter.logout()
        }

        return view
    }

    override fun fillDataToLayout(user: User?) {
        tv_user_name.text = user?.userName
        tv_email.text = user?.email
        tv_phone_number.text = user?.phoneNumber
        tv_location.text = user?.address
        if (user?.profileImageUrl != "default profile image url"){
            Glide.with(this).load(user?.profileImageUrl).into(civ_profile_image)
        }
    }

    override fun navigateToAccountDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToLogin() {
        startActivity(Intent(context, LoginActivity::class.java))
    }

    override fun showProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return AccountFragment()
        }
    }
}
