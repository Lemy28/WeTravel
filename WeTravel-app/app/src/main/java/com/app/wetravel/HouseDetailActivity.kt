package com.app.wetravel


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.wetravel.models.House
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class HouseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        // 获取传递的数据
        val houseDataString = intent.getStringExtra("HOUSE_DATA")

        // 如果你将房屋数据转换为 Gson 格式的字符串传递过来，则可以使用 Gson 来解析数据
        val gson = Gson()
        val clickedHouse = gson.fromJson(houseDataString, House::class.java)



        // 初始化视图组件
        val titleTextView = findViewById<TextView>(R.id.titleTextView) as? TextView
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView) as? TextView
        val imageView = findViewById<ImageView>(R.id.imageView)
        val mapbutton = findViewById<Button>(R.id.locateButton)

        if (titleTextView != null) {
            titleTextView.text = clickedHouse.roomName.toString()//名字
        }
        if (descriptionTextView != null) {
            descriptionTextView.text = clickedHouse.location.toString()
        }
        val prefix = "http://39.107.60.28:8014"
        Picasso.get()
            .load(prefix + clickedHouse.imageUrl)
            .into(imageView)


        mapbutton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("key", clickedHouse.roomName.toString())
            startActivity(intent)
        }



    }
}
