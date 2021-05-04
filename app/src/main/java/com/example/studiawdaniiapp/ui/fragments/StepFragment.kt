package com.example.studiawdaniiapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.studiawdaniiapp.databinding.FragmentStepBinding

class StepFragment : Fragment() {

    private lateinit var binding: FragmentStepBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepBinding.inflate(inflater, container, false)
        return binding.root;
    }
}