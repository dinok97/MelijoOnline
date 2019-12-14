package com.dinokeylas.melijoonline.contract

import com.dinokeylas.melijoonline.model.Vegetable

interface VegetableContract {
    interface View{
        fun onDataLoaded(vegetableList: ArrayList<Vegetable>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter{
        fun loadVegetableData()
    }
}