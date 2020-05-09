package com.dinokeylas.melijoonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.dinokeylas.melijoonline.view.AccountFragment
import com.dinokeylas.melijoonline.view.TrolleyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private val fragmentHome = HomeFragment.newInstance()
    private val fragmentTrolley = TrolleyFragment.newInstance()
    private val fragmentTransaction = TransactionFragment.newInstance()
    private val fragmentAccount = AccountFragment.newInstance()
    private var active: Fragment = fragmentTrolley

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val menu: Menu = findViewById<BottomNavigationView>(R.id.bottom_navigation).menu
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

//         initialize first fragment that appear
        selectedMenu(menu.getItem(0))

        bottomNavigation.setOnNavigationItemSelectedListener {
            item: MenuItem -> selectedMenu(item)
            false
        }

        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentAccount).hide(fragmentAccount).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentTransaction).hide(fragmentTransaction).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentTrolley).hide(fragmentTrolley).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_container, fragmentHome).commit()

    }

    private fun selectedMenu(menuItem: MenuItem){
        menuItem.isChecked = true
        when(menuItem.itemId){
            R.id.navigation_home -> {
                showFragment(fragmentHome)
                active = fragmentHome
            }
            R.id.navigation_trolley -> {
                showFragment(fragmentTrolley)
                active = fragmentTrolley
            }
            R.id.navigation_transaction -> {
                showFragment(fragmentTransaction)
                active = fragmentTransaction
            }
            R.id.navigation_account -> {
                showFragment(fragmentAccount)
                active = fragmentAccount
            }
        }
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
    }
}
