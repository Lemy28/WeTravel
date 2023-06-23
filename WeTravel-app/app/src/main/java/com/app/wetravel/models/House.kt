package com.app.wetravel.models

import java.io.Serializable

data class House(
    val id: String, // 民宿的唯一标识符
    val name: String, // 民宿的名称
    val address: String, // 民宿的地址
    val price: Double, // 民宿的价格
    val image: String // 民宿的图片链接
) : Serializable
