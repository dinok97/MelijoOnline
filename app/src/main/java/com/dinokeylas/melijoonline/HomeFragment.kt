package com.dinokeylas.melijoonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.dinokeylas.melijoonline.adapter.SliderImageAdapter
import com.dinokeylas.melijoonline.model.BannerImage
import com.dinokeylas.melijoonline.util.Constant.Collection.Companion.BANNER_IMAGES
import com.dinokeylas.melijoonline.view.SeasoningActivity
import com.dinokeylas.melijoonline.view.VegetableActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.viewpagerindicator.CirclePageIndicator
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var viewPager: ViewPager? = null
    private var currentPage = 0
    private var NUM_PAGES = 0
    private var imageList: ArrayList<BannerImage> = ArrayList()
    private lateinit var ctx: View
    private lateinit var indicator: CirclePageIndicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        ctx = view
        viewPager = view.findViewById(R.id.pager)
        indicator = view.findViewById(R.id.indicator)

        getImageList()

        val cvVegetable: CardView = view.findViewById(R.id.cv_vegetable)
        val cvSeasoning: CardView = view.findViewById(R.id.cv_seasoning)
        val cvVegetablePackage: CardView = view.findViewById(R.id.cv_vegetable_packet)
        val cvMeat: CardView = view.findViewById(R.id.cv_chicken_meet)
        val cvFruit: CardView = view.findViewById(R.id.cv_other_menu)
        val cvSeafood: CardView = view.findViewById(R.id.cv_seafood)

        cvVegetable.setOnClickListener { startActivity(Intent(context, VegetableActivity::class.java)) }
        cvSeasoning.setOnClickListener { startActivity(Intent(context, SeasoningActivity::class.java)) }
        cvVegetablePackage.setOnClickListener { startActivity(Intent(context, VegetablePackageActivity::class.java)) }
        cvSeafood.setOnClickListener { startActivity(Intent(context, SeaFoodActivity::class.java)) }
        cvMeat.setOnClickListener { startActivity(Intent(context, MeatActivity::class.java)) }
        cvFruit.setOnClickListener { startActivity(Intent(context, FruitActivity::class.java)) }

        return view
    }

    private fun getImageList() {
        FirebaseFirestore.getInstance().collection(BANNER_IMAGES).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val image: BannerImage = document.toObject(BannerImage::class.java)
                    imageList.add(image)
                }
                fillBannerToLayout()
            }.addOnFailureListener {
            Log.d("DEBUG-IMAGES", "fail to catch image")
        }
    }

    private fun fillBannerToLayout(){
        viewPager?.adapter = SliderImageAdapter(ctx.context, imageList)
        setIndicator()
    }

    private fun setIndicator() {
        indicator.setViewPager(viewPager)
        val density = resources.displayMetrics.density
        indicator.radius = 5 * density
        NUM_PAGES = imageList.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) { currentPage = 0 }
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
            override fun onPageSelected(position: Int) { currentPage = position }
            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) { }
            override fun onPageScrollStateChanged(pos: Int) { }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
