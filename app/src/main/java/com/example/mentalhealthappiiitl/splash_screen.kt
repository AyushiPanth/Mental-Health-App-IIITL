package com.example.mentalhealthappiiitl

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController


class splash_screen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_splash_screen, container, false)
        Handler().postDelayed(
            {
                findNavController().navigate(R.id.action_splash_screen_to_view_pager)
            },3000
        )


        return v
    }


}