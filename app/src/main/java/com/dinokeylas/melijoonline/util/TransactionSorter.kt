package com.dinokeylas.melijoonline.util

import com.dinokeylas.melijoonline.model.TransactionBundle

class TransactionSorter {
    companion object{
        @JvmStatic
        fun sort(tranList: ArrayList<TransactionBundle>): ArrayList<TransactionBundle>{
            val list = tranList.sortedWith(compareBy{it.date})
            tranList.removeAll(tranList)
            tranList.addAll(list)
            return tranList
        }
    }
}