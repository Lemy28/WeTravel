package com.app.wetravel
import android.os.Bundle


import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.Fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.wetravel.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadHomeFragment()

    }


    private fun loadHomeFragment() {
        val homeFragment = HomeFragment()
        replaceFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.contentfragment.id, fragment)
            .commit()
    }

}

