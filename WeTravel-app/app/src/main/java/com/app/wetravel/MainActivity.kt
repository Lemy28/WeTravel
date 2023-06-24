package com.app.wetravel

import OkHttpTest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.app.wetravel.databinding.ActivityMainBinding
import com.app.wetravel.databinding.BottomFragmentBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    //val Okhttp =OkHttpTest(13220324,123)


}