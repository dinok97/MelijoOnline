package com.dinokeylas.melijoonline.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class Transaction(
    @get:Exclude var transactionId: String = "",
    val transactionCode: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val date: Date = Calendar.getInstance().time,
    val itemName: String = "",
    val itemPrise: Int = 0,
    val imageUrl: String = "",
    val userLocation: String = "",
    val itemQty: Int = 0,
    val totalPay: Int = 0,
    val sellerName: String = "",
    val done: Boolean = false,
    val transactionProgress: String = ""
)

