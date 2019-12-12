package com.example.qurbatask.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.qurbatask.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fullModeKey: String = "fullMode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val enableFullMode = intent.getBooleanExtra(fullModeKey, false)
        val startDestinationArgs = Bundle().apply {
            putBoolean(fullModeKey, enableFullMode)
        }
        findNavController(R.id.nav_host_fragment).setGraph(
            R.navigation.main_nav,
            startDestinationArgs
        )
    }
}
