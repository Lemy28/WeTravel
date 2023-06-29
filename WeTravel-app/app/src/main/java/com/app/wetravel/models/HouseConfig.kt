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
        return "'$detail' \n " +
                "厨房：'$kitchen'\n 浴室：'$bathroom'\n 卧室='$bedroom'\n 家具='$parlour')"
    }
}
