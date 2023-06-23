package com.app.wetravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.app.wetravel.databinding.ActivityMainBinding
import com.app.wetravel.databinding.BottomFragmentBinding
import com.ferfalk.simplesearchview.SimpleSearchView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Other code ...
    }

    // Other methods ...
}
