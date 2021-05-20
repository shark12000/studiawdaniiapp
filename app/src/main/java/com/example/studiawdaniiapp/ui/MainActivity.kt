package com.example.studiawdaniiapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.studiawdaniiapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)

        viewModel.getCurrentUser().observe(this, {
            if(it == true) {
                navHostFragment.findNavController().setGraph(R.navigation.nav_graph_home)
            } else
                navHostFragment.findNavController().setGraph(R.navigation.nav_graph_authorization)
        })
    }

}