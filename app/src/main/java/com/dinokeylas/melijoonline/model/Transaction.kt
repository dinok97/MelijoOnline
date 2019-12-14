package com.dinokeylas.melijoonline.model

data class Transaction (
    val transactionId: String,
    val userId: String,
    val userEmail: String,
    val itemName: String,
    val userLocation: String,
    val itemQty: Int,
    val totalPay: Int,
    val sellerName: String,
    val done: Boolean,
    val transactionProgress: String
)

