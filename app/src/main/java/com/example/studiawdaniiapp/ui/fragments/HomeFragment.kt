package com.example.studiawdaniiapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentHomeBinding
import com.example.studiawdaniiapp.ui.recyclerView.EduListAdapter
import com.example.studiawdaniiapp.ui.viewmodel.EduViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = EduListAdapter()
    private val eduViewModel: EduViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialiseRecycler()
        initialiseAdapter()
        observeData()
    }

    private fun initialiseRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initialiseAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {
        eduViewModel.fetchEducationalInstitutionsList.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    adapter.setEducationalInstitutionsList(it.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        context,
                        "An error has occurred:${it.string}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}