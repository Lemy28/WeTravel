package com.app.wetravel
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import android.util.Log

import android.widget.Button


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

    //val Okhttp =OkHttpTest(13220324,123)


}

    // Other methods ...
}

