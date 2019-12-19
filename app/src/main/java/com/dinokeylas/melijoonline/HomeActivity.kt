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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val menu: Menu = findViewById<BottomNavigationView>(R.id.bottom_navigation).menu
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // initialize first fragment that appear
        selectedMenu(menu.getItem(0))

        bottomNavigation.setOnNavigationItemSelectedListener {
            item: MenuItem -> selectedMenu(item)
            false
        }

    }

    private fun selectedMenu(menuItem: MenuItem){
        menuItem.isChecked = true
        when(menuItem.itemId){
            R.id.navigation_home -> showFragment(HomeFragment())
            R.id.navigation_trolley -> showFragment(TrolleyFragment())
            R.id.navigation_account -> showFragment(AccountFragment())
        }
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}
