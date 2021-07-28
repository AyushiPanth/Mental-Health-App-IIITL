package com.example.mentalhealthappiiitl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class view_pager : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_view_pager, container, false)
        val viewpager:ViewPager2 =v.findViewById(R.id.view_pager)
        val indicator:CircleIndicator3=v.findViewById(R.id.circle)

        val fragmentlist= arrayListOf(
            first(),
            second(),
            third()

        )
        val myadapter=viewpageradapter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        viewpager.adapter=myadapter
        indicator.setViewPager(viewpager)


        return v
    }


}