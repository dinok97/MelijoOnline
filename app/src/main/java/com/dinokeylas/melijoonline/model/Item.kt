package com.dinokeylas.melijoonline.model

import com.google.firebase.firestore.Exclude

data class Item (
    @get:Exclude var itemId: String = "",
    val name: String = "",
    val category: String = "",
    val price: Int = 0,
    val description: String = "",
    val sellerName: String = "",
    val imageUrl: String = ""
)