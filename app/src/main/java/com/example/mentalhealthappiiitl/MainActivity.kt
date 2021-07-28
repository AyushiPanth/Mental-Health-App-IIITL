package com.example.mentalhealthappiiitl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mentalhealthappiiitl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   // private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

       // binding = DataBindingUtil.setContentView(this,R.layout.activity_main)


    }
}
