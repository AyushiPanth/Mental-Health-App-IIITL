package com.example.mentalhealthappiiitl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mentalhealthappiiitl.databinding.FragmentViewPagerBinding

class view_pager : Fragment() {

     lateinit var binding: FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentViewPagerBinding.inflate(inflater, container, false)


        val fragmentlist= arrayListOf(
            landing_screen_first(),
            landing_screen_second(),
            landing_screen_third()

        )
        val myadapter=viewpageradapter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter=myadapter
        binding.circle.setViewPager(binding.viewPager)


        return binding.root
    }


}