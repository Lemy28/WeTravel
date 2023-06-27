package com.app.wetravel


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.wetravel.models.House
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException


class HouseDetailActivity : AppCompatActivity() {

    private var rentCount = 1 // 初始租赁天数为1





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        // 获取传递的数据
        val houseDataString = intent.getStringExtra("HOUSE_DATA")

        // 如果你将房屋数据转换为 Gson 格式的字符串传递过来，则可以使用 Gson 来解析数据
        val gson = Gson()
        val clickedHouse = gson.fromJson(houseDataString, House::class.java)



        // 初始化视图组件

        val rentCountTextView = findViewById<TextView>(R.id.rentCountTextView)
        val priceTextView = findViewById<TextView>(R.id.priceTextview) as? TextView
        val titleTextView = findViewById<TextView>(R.id.titleTextview) as? TextView
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView) as? TextView
        val imageView = findViewById<ImageView>(R.id.imageView)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val payButton = findViewById<Button>(R.id.payButton)

        if (rentCountTextView != null) {
            rentCountTextView.text = rentCount.toString()
        }
        if (priceTextView != null) {
            priceTextView.text = clickedHouse.price.toString()+"/天"
        }
        if (titleTextView != null) {
            titleTextView.text = clickedHouse.roomName.toString()
        }
        if (descriptionTextView != null) {
            descriptionTextView.text = clickedHouse.location.toString()
        }
        val prefix = "http://39.107.60.28:8014"
        Picasso.get()
            .load(prefix + clickedHouse.imageUrl)
            .into(imageView)

        findViewById<Button>(R.id.increaseButton).setOnClickListener {
            rentCount++
            rentCountTextView.text = rentCount.toString()
        }

        // 减号按钮点击事件
        findViewById<Button>(R.id.decreaseButton).setOnClickListener {
            if (rentCount > 1) {
                rentCount--
                rentCountTextView.text = rentCount.toString()
            }
        }

        // 添加按钮点击监听器
        saveButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                // 在后台线程中执行网络请求
                val userId = "22"
                val roomId = clickedHouse.roomId
                val url = "http://39.107.60.28:8014/addCollection?userId=$userId&roomId=$roomId"

                try {
                    val request = Request.Builder()
                        .url(url)
                        .build()

                    val client = OkHttpClient()
                    val response = client.newCall(request).execute()

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // 收藏成功
                            // 在这里可以更新 UI 或执行其他逻辑操作
                            Toast.makeText(this@HouseDetailActivity, "收藏成功", Toast.LENGTH_SHORT).show()
                        } else {
                            // 收藏失败
                            // 在这里可以处理失败情况，例如显示错误信息等
                            Toast.makeText(this@HouseDetailActivity, "已经收藏过了", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    // 异常处理
                    // 在这里可以处理网络请求异常，例如显示错误信息等
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@HouseDetailActivity, "网络请求异常", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                }
            }
        }

        payButton.setOnClickListener {
            val totalPrice = clickedHouse.price * rentCount
            // 在这里处理租订按钮的逻辑，例如跳转到支付页面，传递总价格等
            // 使用 totalPrice 来获取计算后的房租金额
                // 在这里处理租订按钮的逻辑，例如跳转到支付页面，传递总价格等
                // 使用 totalPrice 来获取计算后的房租金额


                val userId = "22"
                val roomId = clickedHouse.roomId

                // 执行网络请求或调用后台API提交数据
                submitBillToBackend(userId, roomId, rentCount.toString(),clickedHouse.price.toString())



        }

    }

    private fun submitBillToBackend(userId: String, roomId: String,rentCount: String,housePrice:String) {
            // 构建请求的 URL
            val urlBuilder = (Configs.prefix + "addOrder").toHttpUrlOrNull()?.newBuilder()
            urlBuilder?.addQueryParameter("userId", userId)
            urlBuilder?.addQueryParameter("roomId", roomId)
            urlBuilder?.addQueryParameter("day",rentCount )
            val totalPrice = rentCount.toFloat() * housePrice.toFloat()
            urlBuilder?.addQueryParameter("totalPrice",totalPrice.toString())
            val url = urlBuilder?.build()

            Log.d("urlDebug",url.toString())

            // 创建 OkHttp 客户端
            val client = OkHttpClient()

            // 创建请求对象
            val request = url?.let {
                Request.Builder()
                    .url(it)
                    .build()
            }

            // 发起异步请求
            if (request != null) {
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        // 请求失败，显示错误提示
                        runOnUiThread {
                            Toast.makeText(this@HouseDetailActivity, "请求失败，请重试", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        // 请求成功，根据响应结果显示相应提示
                        val responseBody = response.body?.string()

                        if (response.isSuccessful && responseBody != null) {
                            // 请求成功并且后台返回了正确的响应
                            runOnUiThread {
                                Toast.makeText(this@HouseDetailActivity, "支付成功，总价格为: $totalPrice", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 请求成功但后台返回了错误的响应
                            runOnUiThread {
                                Toast.makeText(this@HouseDetailActivity, "数据提交失败，请重试", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }

    }

