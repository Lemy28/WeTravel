package com.app.wetravel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.wetravel.models.House
import com.frogobox.recycler.core.FrogoRecyclerNotifyListener
import com.frogobox.recycler.core.IFrogoViewAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class HomeFragment: Fragment() {

    fun listOfHouses(callback: (List<House>) -> Unit) {
        // 创建一个OkHttpClient实例
        val client = OkHttpClient()
        Log.d("JsonParse","ready to send request")
        // 创建一个请求对象
        val request = Request.Builder()
            .url("http://39.107.60.28:8014/selectAccommodation") // 这里是你的后台数据的URL
            .build()

        // 发送请求并获取响应（异步方式）
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理失败情况
                Log.d("JsonParse","failed to get response")

                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()

                    val houses = parseJson(json)
                    Log.d("JsonParse",houses.toString())

                    callback(houses)
                } else {

                    throw IOException("Unexpected code $response")
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)// tell the Fragment that it has menu items

        return inflater.inflate(R.layout.home_content,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listOfHouses { houses ->
            requireActivity().runOnUiThread {
                val frogoRv = view.findViewById<com.frogobox.recycler.widget.FrogoRecyclerView>(R.id.house_rv)
                // 设置数据源、布局、回调等参数
                frogoRv.injector<House>()
                    .addData(houses) // 这里使用回调函数传入的houses列表
                    .addCustomView(R.layout.frogo_rv_grid_type_2) // 这里是一个自定义的布局文件，用于显示每个House对象的信息，你需要自己创建或修改
                    .addEmptyView(null)
                    .addCallback(object : IFrogoViewAdapter<House> {
                        override fun setupInitComponent(view: View, data: House, position: Int, notifyListener: FrogoRecyclerNotifyListener<House>) {
                            // 在这里绑定数据和视图
                            Log.d("House", "The name of the house is ${data.roomName}")

                            view.findViewById<TextView>(R.id.tv_house_name).text = data.roomName // 设置民宿名称
                            view.findViewById<TextView>(R.id.tv_house_address).text = data.location // 设置民宿地址
                            view.findViewById<TextView>(R.id.tv_house_price).text = "$${data.price}" // 设置民宿价格
                            Picasso.get().load(data.imageUrl).into(view.findViewById<ImageView>(R.id.iv_house_image))// 加载民宿图片
                        }
                        override fun onItemClicked(view: View, data: House, position: Int, notifyListener: FrogoRecyclerNotifyListener<House>) {
                            // 在这里处理点击事件，或者留空
                        }
                        override fun onItemLongClicked(view: View, data: House, position: Int, notifyListener: FrogoRecyclerNotifyListener<House>) {
                            // 在这里处理长按事件，或者留空
                        }

                    })
            }
        }




    }
}