package com.dinokeylas.melijoonline.contract

import com.dinokeylas.melijoonline.model.Seasoning

interface SeasoningContract {
    interface View{
        fun onDataLoaded(seasoningList: ArrayList<Seasoning>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter{
        fun loadSeasoningData()
    }
}