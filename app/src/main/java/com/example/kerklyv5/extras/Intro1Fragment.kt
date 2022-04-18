package com.example.kerklyv5.extras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.kerklyv5.R


class Intro1Fragment : Fragment() {


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View?

    {


         val view: View = inflater.inflate(R.layout.fragment_intro1, container, false)




        return view;
    }

}