package com.app.wetravel
import android.os.Bundle


import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.Fragment

import androidx.appcompat.app.AppCompatActivity

import com.app.wetravel.databinding.ActivityMainBinding









import com.app.wetravel.databinding.ActivityMainBinding

import com.app.wetravel.databinding.BottomFragmentBinding
import com.ferfalk.simplesearchview.SimpleSearchView



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadHomeFragment()




        // Other code ...

    }


    private fun loadHomeFragment() {
        val homeFragment = HomeFragment()
        replaceFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.content.id, fragment)
            .commit()
    }

}

