package com.example.studiawdaniiapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studiawdaniiapp.databinding.FragmentHomeBinding
import com.example.studiawdaniiapp.ui.recyclerView.EduListAdapter
import com.example.studiawdaniiapp.viewmodel.EduViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeFragment : Fragment(), View.OnClickListener, KoinComponent {

    private lateinit var binding: FragmentHomeBinding
    private val eduListAdapter: EduListAdapter by inject()
    private val eduViewModel: EduViewModel by viewModel()
    private lateinit var listView: RecyclerView
    private var layoutManager: LinearLayoutManager = LinearLayoutManager(context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialiseAdapter()
    }



    override fun onClick(v: View?) {
        when(v!!.id) {

        }
    }

    private fun initialiseAdapter() {
        binding.recyclerView.layoutManager = layoutManager
        observeData()
    }

    fun observeData() {
        eduViewModel.getEduListData().observe(viewLifecycleOwner, Observer {
            binding.recyclerView.adapter = EduListAdapter(it)
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
        })
    }


}