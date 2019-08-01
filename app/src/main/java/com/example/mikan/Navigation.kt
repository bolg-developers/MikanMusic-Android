package com.example.mikan

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.mikan.NavigationFragment.DashboardFragment
import com.example.mikan.NavigationFragment.HomeFragment
import com.example.mikan.NavigationFragment.NotificationsFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity() {

    // navigationのクリック時の処理
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, HomeFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, DashboardFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, NotificationsFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //初期表示
        supportFragmentManager.beginTransaction()
           .replace(R.id.frameLayout, HomeFragment())
           .commit()
    }
}
