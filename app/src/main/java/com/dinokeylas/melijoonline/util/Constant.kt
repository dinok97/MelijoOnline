package com.dinokeylas.melijoonline.util

interface Constant {

    interface Collection {
        companion object{
            val USER = "user"
            val TRANSACTION = "transaction"
            val TRANSACTION_BUNDLE = "transactionBundle"
            val ITEM = "item"
            val BANNER_IMAGES = "bannerImages"
            val DATE_FORMAT = "dd/MM/yyyy"
        }
    }

    interface Field {
        companion object{
            val IS_AVAILABLE = "available"
            val DATE = "date"
            val USER_ID = "userId"
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

    interface TransactionBundleProgress {
        companion object{
            val UN_PROCESSED = "un_processed"
            val IN_PROCESSED = "in_processed"
            val DONE = "done"
        }
    }

    interface TransactionProgress {
        companion object{
            val IN_TROLLEY = "in_trolley"
            val ORDERED = "ordered"
            val DONE = "done"
        }
    }

}