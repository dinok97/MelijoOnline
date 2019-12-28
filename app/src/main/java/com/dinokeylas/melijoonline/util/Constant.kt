package com.dinokeylas.melijoonline.util

interface Constant {

    interface Collection {
        companion object{
            val USER = "user"
            val TRANSACTION = "transaction"
            val ITEM = "item"
        }
    }

    interface Category {
        companion object{
            val VEGETABLE = "Vegetable"
            val SEASONING = "Seasoning"
            val VEGETABLE_PACKAGE = "VegetablePackage"
            val SEAFOOD = "Seafood"
            val MEET = "Meet"
            val FRUIT = "Fruit"
        }
    }

}