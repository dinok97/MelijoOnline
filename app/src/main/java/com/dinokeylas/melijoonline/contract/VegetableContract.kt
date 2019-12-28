package com.dinokeylas.melijoonline.contract

import com.dinokeylas.melijoonline.model.Item

interface VegetableContract {
    interface View{
        fun onDataLoaded(itemList: ArrayList<Item>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter{
        fun loadVegetableData()
    }
}