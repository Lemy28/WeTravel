package com.app.wetravel


import android.content.Intent
import android.os.Bundle



import android.util.Log



import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.wetravel.models.House
import com.app.wetravel.models.HouseConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    private val client = OkHttpClient()
    private val userId = "22"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        // 获取传递的数据
        val houseDataString = intent.getStringExtra("HOUSE_DATA")


        // 如果你将房屋数据转换为 Gson 格式的字符串传递过来，则可以使用 Gson 来解析数据
        val gson = Gson()
        val clickedHouse = gson.fromJson(houseDataString, House::class.java)
        fetchData(userId,clickedHouse.roomId)


        // 初始化视图组件

        val rentCountTextView = findViewById<TextView>(R.id.rentCountTextView)
        val priceTextView = findViewById<TextView>(R.id.priceTextview) as? TextView
        val titleTextView = findViewById<TextView>(R.id.titleTextview) as? TextView
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView) as? TextView
        val imageView = findViewById<ImageView>(R.id.imageView)
        val mapbutton = findViewById<ImageView>(R.id.locateButton)
        val bottomTextView = findViewById<TextView>(R.id.bottomTextView)
        val collationbutton = findViewById<ImageView>(R.id.collectionButton)
        val bigTextView = findViewById<TextView>(R.id.bigTextView)
        val washTextView = findViewById<TextView>(R.id.washTextView)
        val bedTextView = findViewById<TextView>(R.id.bedTextView)
        val furniturnTextView = findViewById<TextView>(R.id.furniturnTextView)

        val payButton = findViewById<Button>(R.id.payButton)


        if (rentCountTextView != null) {
            rentCountTextView.text = rentCount.toString()
        }
        if (priceTextView != null) {
            priceTextView.text = clickedHouse.price.toString()+"/天"
        }
        if (titleTextView != null) {
            titleTextView.text = clickedHouse.roomName
        }
        if (descriptionTextView != null) {
            descriptionTextView.text = clickedHouse.location
        }

        if(bottomTextView != null){
            getDetail(clickedHouse.roomId) { houseConfig ->
                if (houseConfig != null) {
                    runOnUiThread {
                        bottomTextView.text = houseConfig.toString()
                        bigTextView.text= houseConfig.toStringbig()
                        washTextView.text= houseConfig.toStringwashroom()
                        bedTextView.text= houseConfig.toStringbedroom()
                        furniturnTextView.text= houseConfig.toStringfurniture()

                    }
                } else {
                    bottomTextView.text = "Unknow"
                }
            }

        }
        Picasso.get()
            .load(Configs.prefix + clickedHouse.imageUrl)
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


        // 收藏
        collationbutton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                // 在后台线程中执行网络请求
                val roomId = clickedHouse.roomId
                val url = "http://39.107.60.28:8014/addCollection?userId=$userId&roomId=$roomId"

                try {
                    val request = Request.Builder()
                        .url(url)
                        .build()

                    val response = client.newCall(request).execute()

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // 收藏成功
                            // 在这里可以更新 UI 或执行其他逻辑操作
                            collationbutton.setImageResource(R.drawable.collectionbutton)
                            Toast.makeText(this@HouseDetailActivity, "收藏成功", Toast.LENGTH_SHORT).show()
                        } else {
                            // 收藏失败
                            // 在这里可以处理失败情况，例如显示错误信息等
                            collationbutton.setImageResource(R.drawable.collectionbutton2)
                            Toast.makeText(this@HouseDetailActivity, "已经取消收藏", Toast.LENGTH_SHORT).show()
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
            // 在这里处理租订按钮的逻辑，例如跳转到支付页面，传递总价格等
            // 使用 totalPrice 来获取计算后的房租金额
                val roomId = clickedHouse.roomId

                // 执行网络请求或调用后台API提交数据
                submitBillToBackend(userId, roomId, rentCount.toString(),clickedHouse.price.toString())



        }


        //地图
        mapbutton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("HOUSE_DATA",intent.getStringExtra("HOUSE_DATA"))
            intent.putExtra("key", clickedHouse.roomName.toString())
            startActivity(intent)
        }

    }
    private fun getDetail(roomId: String, callback: (HouseConfig?) -> Unit) {
        val urlBuilder = (Configs.prefix + "selectConfiguration").toHttpUrlOrNull()?.newBuilder()
        urlBuilder?.addQueryParameter("roomId", roomId)

        val url = urlBuilder?.build()

        Log.d("urlDebug",url.toString())

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
                        Toast.makeText(this@HouseDetailActivity, "请求房间信息失败", Toast.LENGTH_SHORT).show()
                    }
                    // 回调函数中返回 null
                    callback(null)
                }

                override fun onResponse(call: Call, response: Response) {
                    // 请求成功，根据响应结果显示相应提示
                    val responseBody = response.body?.string()

                    if (response.isSuccessful && responseBody != null) {
                        // 请求成功并且后台返回了正确的响应
                        val gson = Gson()
                        val houseConfig = gson.fromJson(responseBody, HouseConfig::class.java)

                        callback(houseConfig)
                    } else {
                        // 请求成功但后台返回了错误的响应
                        runOnUiThread {
                            Toast.makeText(this@HouseDetailActivity, "网络故障", Toast.LENGTH_SHORT).show()
                        }
                        // 回调函数中返回 null
                        callback(null)
                    }
                }
            })
        } else {
            // URL 构建错误，回调函数中返回 null
            callback(null)
        }
    }

    private fun fetchData(userid: String,roomId: String) {
        val client = OkHttpClient()
        val collationbutton = findViewById<ImageView>(R.id.collectionButton)
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("39.107.60.28")
            .port(8014)
            .addPathSegment("checkCollection")
            .addQueryParameter("userId", userid)
            .addQueryParameter("roomId", roomId)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                // 处理请求失败情况
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    collationbutton.setImageResource(R.drawable.collectionbutton)
                } else {
                    collationbutton.setImageResource(R.drawable.collectionbutton2)
                }
            }
        })
    }

    fun parseJson(json: String?): List<House> {
        // 创建一个Gson实例
        val gson = Gson()

        // 创建一个泛型类型
        val type = object : TypeToken<List<House>>() {}.type

        // 直接将JSON字符串转换为List<House>类型
        return gson.fromJson(json, type)
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
                                Toast.makeText(this@HouseDetailActivity, "已经被预定了", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }


    }

