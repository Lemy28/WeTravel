package com.app.wetravel


import OrderListAdapter
import UserManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.wetravel.databinding.OrderBinding
import com.app.wetravel.databinding.OrderlistrecycleviewBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class OrderFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var order: InOrders
    private lateinit var adapter: OrderListAdapter
    private var _binding: OrderBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userId = UserManager.getUser()?.userId
        if (userId != null && UserManager.isLoggedIn) {
            fetchOrderListDataFromServer(userId)
        }
        _binding = OrderBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.recyclerView
        adapter = OrderListAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }

    fun fetchOrderListDataFromServer(userId: String) {
        val client = OkHttpClient()
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("39.107.60.28")
            .port(8014)
            .addPathSegment("getInOrders")
            .addQueryParameter("userId", userId)
            .build()
        val request = Request.Builder()
            .url(url)
            .build()

        // 执行请求
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 请求失败的处理
                activity?.runOnUiThread {
                    // 在主线程中更新 UI
                    // 这里可以显示错误信息或执行其他操作
                    println("请求失败: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // 处理响应数据
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d(PersonalFragment.TAG, responseData.toString())

                    activity?.runOnUiThread {
                        val orderList = Gson().fromJson<List<InOrders>>(
                            responseData,
                            object : TypeToken<List<InOrders>>() {}.type
                        )
                        adapter.setOrderList(orderList)
                    }
                } else {
                    activity?.runOnUiThread {
                        // 在主线程中更新 UI
                        // 这里可以显示错误信息或执行其他操作
                        println("请求失败: ${response.code}")
                    }
                }
            }
        })
    }
}
