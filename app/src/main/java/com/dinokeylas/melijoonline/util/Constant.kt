package com.dinokeylas.melijoonline.util

interface Constant {

    interface Collection {
        companion object{
            val USER = "user"
            val TRANSACTION = "transaction"
            val TRANSACTION_BUNDLE = "transactionBundle"
            val ITEM = "item"
            val DATE_FORMAT = "dd/MM/yyyy"
        }
    }

    interface Category {
        companion object{
            val VEGETABLE = "Vegetable"
            val SEASONING = "Seasoning"
            val VEGETABLE_PACKAGE = "VegetablePackage"
            val SEAFOOD = "Seafood"
            val MEAT = "Meat"
            val FRUIT = "Fruit"
        }
    }

}