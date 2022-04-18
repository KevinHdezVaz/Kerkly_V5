package com.example.kerklyv5.extras

import com.example.kerklyv5.vista.MainActivity


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.kerklyv5.R
import kotlinx.android.synthetic.main.activity_intro_slider.*

class IntroSliderActivity : AppCompatActivity() {


    private val fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState)


        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_intro_slider)
         val adapter = IntroSliderAdapter(this)
        vpIntroSlider.adapter = adapter

        fragmentList.addAll(listOf(
            Intro1Fragment(), Intro2Fragment(), Intro3Fragment()
        ))
        adapter.setFragmentList(fragmentList)

        indicatorLayout.setIndicatorCount(adapter.itemCount)
        indicatorLayout.selectCurrentPosition(0)

        registerListeners()
    }

    private fun registerListeners() {
        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)

                if (position < fragmentList.lastIndex) {
                    tvSkip.visibility = View.VISIBLE
                    tvNext.text = "SIGUIENTE"
                } else {
                    tvSkip.visibility = View.GONE
                    tvNext.text = "COMENZAR"
                }
            }
        })

        tvSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        tvNext.setOnClickListener {
            val position = vpIntroSlider.currentItem

            if (position < fragmentList.lastIndex) {
                vpIntroSlider.currentItem = position + 1
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}