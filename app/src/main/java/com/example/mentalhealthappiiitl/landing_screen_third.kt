package com.example.mentalhealthappiiitl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.mentalhealthappiiitl.databinding.FragmentLoginBinding
import com.example.mentalhealthappiiitl.databinding.FragmentLandingScreenThirdBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth


class landing_screen_third : Fragment() {
    lateinit var binding: FragmentLandingScreenThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLandingScreenThirdBinding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {

            findNavController().navigate(R.id.action_view_pager_to_loginFragment)

        }

        return binding.root
    }

}