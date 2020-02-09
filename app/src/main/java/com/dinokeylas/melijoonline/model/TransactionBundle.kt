package com.dinokeylas.melijoonline.model

import com.google.firebase.firestore.Exclude
import java.util.*
import kotlin.collections.ArrayList

data class TransactionBundle (
    @get:Exclude var tranBundleId: String = "",
    var tranBundleCode: String = "",
    var tranIdList: ArrayList<String> = ArrayList(),
    var userId: String = "",
    var userEmail: String = "",
    var date: Date = Calendar.getInstance().time,
    var itemNameList: ArrayList<String> = ArrayList(),
    var totalPay: Int = 0,
    var done: Boolean = false,
    var transactionBundleProgress: String = ""

)