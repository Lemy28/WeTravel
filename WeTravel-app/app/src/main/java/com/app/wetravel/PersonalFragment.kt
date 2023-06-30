package com.app.wetravel

import InformationFragment
import UserManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.wetravel.databinding.PersonalFragmentBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException


class PersonalFragment:Fragment() {
    private var _binding: PersonalFragmentBinding?=null
    private val binding get()=_binding!!
    companion object {
        const val TAG = "PersonalFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= PersonalFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.textView12.setOnClickListener(){
            replaceFragment(LoginFragment())
        }
        binding.imageButton2.setOnClickListener(){
            replaceFragment(SettingFrgment())
        }
        binding.imageButton5.setOnClickListener(){
            replaceFragment(InformationFragment())
        }
        //测试个人订单界面
        binding.imageButton4.setOnClickListener(){
            replaceFragment(OrderFragment())
        }
        if(UserManager.isLoggedIn){
            val phonenumber=UserManager.getUser()?.phoneNumber
            binding.textView13.text="13874900339@qq.com"
            if(phonenumber!=null) {
                fetchDataFromServer(phonenumber)
            }
        }


        return view
    }
    fun fetchDataFromServer(phoneNumber:String) {
        val client = OkHttpClient()
        val url= HttpUrl.Builder()
            .scheme("http")
            .host("39.107.60.28")
            .port(8014)
            .addPathSegment("getUserByPhoneNumber")
            .addQueryParameter("phoneNumber",phoneNumber)
            .build()
        val request = Request.Builder()
            .url(url)  // 替换为你的服务器 URL
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // 请求失败的处理
                activity?.runOnUiThread {
                    // 在主线程中更新 UI
                    // 这里可以显示错误信息或执行其他操作
                    println("请求失败: ${e.message}")
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // 处理响应数据
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d(PersonalFragment.TAG, "Server response:success")

                    activity?.runOnUiThread {
                        val type = object : TypeToken<User>() {}.type
                        val user = Gson().fromJson<User>(responseData, type)
                        UserManager.setUser(user)
                        binding.textView12.visibility=View.GONE
                        binding.textView21.text=user.username
                        binding.textView21.visibility=View.VISIBLE

                        Log.d(PersonalFragment.TAG, responseData.toString())
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
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=activity?.supportFragmentManager!!
        val transaction=fragmentManager.beginTransaction()
        transaction.replace(R.id.contentfragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}