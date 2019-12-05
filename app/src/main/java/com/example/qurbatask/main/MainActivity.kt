package com.example.qurbatask.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.qurbatask.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,MainFragment(),"main").commit()
    }
}
