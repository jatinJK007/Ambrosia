package com.example.ambrosia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ambrosia.Fragments.HomeFragment
import com.example.ambrosia.Fragments.ProfileFragment
import com.example.ambrosia.Fragments.SaveFragment
import com.example.ambrosia.Fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val bottomView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        replaceWithFragment(HomeFragment())
        bottomView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceWithFragment(HomeFragment())
                R.id.save -> replaceWithFragment(SaveFragment())
                R.id.profile -> replaceWithFragment(ProfileFragment())
                R.id.search -> replaceWithFragment(SearchFragment())
                else -> {
                }
            }
            true
        }
    }

    private fun replaceWithFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}