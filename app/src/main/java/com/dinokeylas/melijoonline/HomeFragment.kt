package com.dinokeylas.melijoonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.dinokeylas.melijoonline.adapter.SliderImageAdapter
import com.dinokeylas.melijoonline.model.SliderModel
import com.dinokeylas.melijoonline.view.SeasoningActivity
import com.dinokeylas.melijoonline.view.VegetableActivity
import com.viewpagerindicator.CirclePageIndicator
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var viewPager: ViewPager? = null
    private var currentPage = 0
    private var NUM_PAGES = 0

    private lateinit var indicator: CirclePageIndicator
    private var imageModelArrayList: ArrayList<SliderModel>? = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        //initialize view
        viewPager = view.findViewById(R.id.pager)
        indicator = view.findViewById(R.id.indicator)

        imageModelArrayList = populateList()

        //set the adapter
        viewPager!!.adapter = SliderImageAdapter(
            view.context,
            imageModelArrayList!!
        )

        //set the indicator
        setIndicator()

        val cvVegetable: CardView = view.findViewById(R.id.cv_vegetable)
        val cvSeasoning: CardView = view.findViewById(R.id.cv_seasoning)
        val cvVegetablePack: CardView = view.findViewById(R.id.cv_vegetable_packet)
        val cvMeet: CardView = view.findViewById(R.id.cv_chicken_meet)
        val cvOtherMenu: CardView = view.findViewById(R.id.cv_other_menu)
        val cvSeafood: CardView = view.findViewById(R.id.cv_seafood)
        cvVegetable.setOnClickListener { startActivity(Intent(context, VegetableActivity::class.java)) }
        cvSeasoning.setOnClickListener { startActivity(Intent(context, SeasoningActivity::class.java)) }
        cvVegetablePack.setOnClickListener { Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show() }
        cvMeet.setOnClickListener { Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show() }
        cvOtherMenu.setOnClickListener { Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show() }
        cvSeafood.setOnClickListener { Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show() }

        return view
    }

    private fun populateList(): ArrayList<SliderModel> {
        val imageList = intArrayOf(R.drawable.banner_one, R.drawable.banner_two, R.drawable.banner_three)
        val list = ArrayList<SliderModel>()

        for (i in 0..2) {
            val imageModel = SliderModel()
            imageModel.setImage(imageList[i])
            list.add(imageModel)
        }

        return list
    }

    private fun setIndicator() {
        indicator.setViewPager(viewPager)
        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.setRadius(5 * density)

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            viewPager!!.setCurrentItem(currentPage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment{
            return HomeFragment()
        }
    }
}
