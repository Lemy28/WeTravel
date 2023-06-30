package com.app.wetravel.models

import com.google.gson.Gson

class HouseConfig(
    val configurationId: Int,
    val roomId: Int?,
    val detail: String,
    val kitchen: String,
    val bathroom: String,
    val bedroom: String,
    val parlour: String
) {
    companion object {
        fun fromJson(json: String): HouseConfig {
            return Gson().fromJson(json, HouseConfig::class.java)
        }
    }
    override fun toString(): String {
        return "$detail"

    }
    fun toStringbig():String{
        return " 房间大小:$kitchen"
    }
    fun toStringwashroom():String{
        return "浴室:$bathroom"
    }
    fun toStringbedroom():String{
        return "卧室:$bedroom"
    }
    fun toStringfurniture():String{
        return "家具:$parlour"
    }
}
