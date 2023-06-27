package com.app.wetravel

import android.os.AsyncTask
import android.util.Log
import okhttp3.*


class OkHttpLogin(private val username: String, private val password: String) : AsyncTask<Void, Void, String>() {
    companion object {

        private const val TAG = "Net"
        private const val SERVER_URL = "http://192.168.40.28:8014/userLogin" // 服务器URL
    }
    interface OkHttpCallback {
        fun onSuccess(result: String)
        fun onFailure(error: String)
    }
    private var callback: OkHttpCallback? = null

    fun setCallback(callback: OkHttpCallback) {
        this.callback = callback
    }
    override fun doInBackground(vararg voids: Void): String? {
        val client = OkHttpClient()
        // 创建表单请求体
        val requestBody: RequestBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()
        // 创建请求对象
        val request: Request = Request.Builder()
            .url(SERVER_URL)
            .post(requestBody)
            .build()

        try {
            // 发送请求
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body?.string()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error executing server request: " + e.message)
        }

        return null
    }

    override fun onPostExecute(result: String?) {
        if (result != null) {
            // 在这里处理从服务器返回的字符串
            Log.d(TAG, "Server response: $result")
            if (result == "success") {
                // 登录成功
            } else {
                // 登录失败
            }
        } else {
            // 处理请求失败的情况
            Log.e(TAG, "Server request failed")
        }
    }
}

