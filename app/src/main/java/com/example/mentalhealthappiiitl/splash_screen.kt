package com.example.mentalhealthappiiitl

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mentalhealthappiiitl.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth


class splash_screen : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        Handler().postDelayed(
            {
                if (user != null) {
                    findNavController().navigate(R.id.action_splash_screen_to_profileFragment)
                } else {
                    findNavController().navigate(R.id.action_splash_screen_to_view_pager)
                }
            }, 3000
        )


        return binding.root
    }


}