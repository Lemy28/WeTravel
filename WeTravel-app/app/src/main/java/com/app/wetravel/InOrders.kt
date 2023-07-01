package com.app.wetravel

import java.math.BigDecimal
import java.util.Date

class InOrders {
    private var imageUrl: String? = null
    var phoneNumber: String? = null
    var roomName: String? = null
    var location: String? = null
    var time: String? = null
    var day: Int? = null
    var price: BigDecimal? = null

    constructor(
        imageUrl: String?,
        phoneNumber: String?,
        roomName: String?,
        location: String?,
        time: String?,
        day: Int?,
        price: BigDecimal?
    ) {
        this.imageUrl = imageUrl
        this.phoneNumber = phoneNumber
        this.roomName = roomName
        this.location = location
        this.time = time
        this.day = day
        this.price = price
    }

    constructor() {}

    fun getImageUrl(): String {
        return imageUrl!!
    }

    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }
}