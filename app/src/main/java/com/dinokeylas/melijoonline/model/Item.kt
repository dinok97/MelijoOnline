package com.dinokeylas.melijoonline.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class Item (
    @get:Exclude var itemId: String = "",
    val name: String = "",
    val category: String = "",
    val price: Int = 0,
    val unitSale: String = "",
    val description: String = "",
    val createAt: Date = Calendar.getInstance().time,
    val updateAt: Date = Calendar.getInstance().time,
    val isAvailable: Boolean = false,
    val discount: Int = 0,
    val sellerName: String = "",
    val imageUrl: String = ""
)