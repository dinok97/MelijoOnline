package com.dinokeylas.melijoonline.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class Transaction (
    @get:Exclude val transactionId: String,
    val transactionCode: String,
    val userId: String,
    val userEmail: String,
    val date: Date,
    val itemName: String,
    val itemPrise: Int,
    val imageUrl: String,
    val userLocation: String,
    val itemQty: Int,
    val totalPay: Int,
    val sellerName: String,
    val done: Boolean,
    val transactionProgress: String
)

