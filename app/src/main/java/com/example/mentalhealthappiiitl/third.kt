package com.example.mentalhealthappiiitl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton


class third : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_third, container, false)
        val btn:MaterialButton=v.findViewById(R.id.btn)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_view_pager_to_loginFragment)
        }
        return v
    }

}