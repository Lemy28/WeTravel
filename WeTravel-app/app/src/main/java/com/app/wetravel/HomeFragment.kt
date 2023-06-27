package com.app.wetravel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import com.app.wetravel.models.House
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException


class HomeFragment: Fragment() {


    fun performSearch(keyword: String) {
        val client = OkHttpClient()

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("39.107.60.28")
            .port(8014)
            .addPathSegment("selectAccommodationByKey")
            .addQueryParameter("Key", keyword)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理请求失败情况
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()

                    val houses = parseJson(json)

                    requireActivity().runOnUiThread {
                        val frogoRv = view?.findViewById<com.frogobox.recycler.widget.FrogoRecyclerView>(R.id.house_rv)
                        frogoRv?.adapter = HouseAdapter(houses)
                    }
                } else {
                    // 处理请求失败情况
                    throw IOException("Unexpected code $response")
                }
            }
        })
    }



    class HouseAdapter(private val houses: List<House>) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.frogo_rv_grid_type_2, parent, false)
            return HouseViewHolder(view)
        }

        override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
            val house = houses[position]
            holder.bind(house)
        }

        override fun getItemCount(): Int {
            return houses.size
        }

        inner class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val houseNameTextView: TextView = itemView.findViewById(R.id.tv_house_name)
            private val houseAddressTextView: TextView = itemView.findViewById(R.id.tv_house_address)
            private val housePriceTextView: TextView = itemView.findViewById(R.id.tv_house_price)
            private val houseImageView: ImageView = itemView.findViewById(R.id.iv_house_image)

            fun bind(house: House) {
                houseNameTextView.text = house.roomName
                houseAddressTextView.text = house.location
                housePriceTextView.text = "$${house.price}"

                val prefix = "http://39.107.60.28:8014"

                Picasso.get().load(prefix + house.imageUrl).into(houseImageView)

                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val clickedHouse = houses[position]

                        // 创建一个 Intent，并将数据作为 extra 传递给新的 Activity
                        val intent = Intent(itemView.context, HouseDetailActivity::class.java)
                        intent.putExtra("HOUSE_DATA", Gson().toJson(clickedHouse))

                        // 启动新的 Activity
                        itemView.context.startActivity(intent)
                    }
                }



            }
        }
    }

    fun listOfHouses(callback: (List<House>) -> Unit) {
        // 创建一个OkHttpClient实例
        val client = OkHttpClient()
        // 创建一个请求对象
        val request = Request.Builder()
            .url("http://39.107.60.28:8014/selectAccommodation") // 这里是你的后台数据的URL
            .build()

        // 发送请求并获取响应（异步方式）
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理失败情况

                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()

                    val houses = parseJson(json)

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
    return inflater.inflate(R.layout.home_content, container, false)
}



    private var searchHandler = Handler()
    private var searchRunnable: Runnable? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本变化之前执行的操作（可忽略）
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 当文本发生变化时执行的操作
                val searchText = s.toString()

                // 移除之前的搜索任务（如果存在）
                searchRunnable?.let { searchHandler.removeCallbacks(it) }

                // 创建一个新的搜索任务
                searchRunnable = Runnable {
                    // 在这里处理搜索栏输入的文本，例如执行搜索操作
                    performSearch(searchText)
                }
                val runnable = searchRunnable

                // 延迟执行搜索任务
                if (runnable != null) {
                    searchHandler.postDelayed(runnable, 500L)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本变化之后执行的操作（可忽略）
            }
        })


        listOfHouses { houses ->
            requireActivity().runOnUiThread {
                val frogoRv = view.findViewById<com.frogobox.recycler.widget.FrogoRecyclerView>(R.id.house_rv)
                frogoRv.adapter = HouseAdapter(houses)

            }
        }
    }
}