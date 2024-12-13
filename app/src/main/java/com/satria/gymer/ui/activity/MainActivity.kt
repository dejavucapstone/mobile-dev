package com.satria.gymer.ui.activity

import com.satria.gymer.ui.fragment.search.SearchFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satria.gymer.R
import com.satria.gymer.ui.fragment.home.HomeFragment
import com.satria.gymer.ui.fragment.plan.PlanFragment
//import com.satria.gymer.ui.fragment.plan.PlanFragment
import com.satria.gymer.ui.fragment.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up BottomNavigationView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // Load the default fragment (HomeFragment)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Handle navigation item selection
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.navigation_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.navigation_plan -> {
                    loadFragment(PlanFragment())
                    true
                }
                R.id.navigation_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
