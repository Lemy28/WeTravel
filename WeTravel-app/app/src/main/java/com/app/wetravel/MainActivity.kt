package com.app.wetravel
import android.os.Bundle
<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
=======
import android.widget.Button

>>>>>>> 8afc757561075c7c433a747e61f4e830fe957820
import com.app.wetravel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
<<<<<<< HEAD
=======
    //val Okhttp =OkHttpTest(13220324,123)


>>>>>>> 8afc757561075c7c433a747e61f4e830fe957820
}