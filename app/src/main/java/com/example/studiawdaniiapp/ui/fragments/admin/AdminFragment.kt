package com.example.studiawdaniiapp.ui.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentAdminBinding
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentAdminBinding
    private val viewModel: AdminViewModel by viewModel()
    private val adapter: AdminAdapter = AdminAdapter()

    companion object{
        private const val CANCEL = "Cancelled"
        private const val ACCEPT = "Accepted"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupAdapter()
        observeData()
        onButtonClicked()
        onLogOutButtonPressed()
    }

    private fun onLogOutButtonPressed() {
        binding.logout.setOnClickListener() {
            viewModel.signOut()
            navController.setGraph(R.navigation.nav_graph_authorization)
        }
    }

    private fun setupAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun onButtonClicked() {
        adapter.setOnButtonItemClickListener(object :
            OnButtonClicked {
            override fun onCancelButtonPressed(index: Int) {
                val item = adapter.getItem(index)
                viewModel.setStatus(CANCEL, item.userID, item.id)
                adapter.removeItem(index)
            }

            override fun onAcceptButtonPressed(index: Int) {
                val item = adapter.getItem(index)
                viewModel.setStatus(ACCEPT, item.userID, item.id)
                adapter.removeItem(index)
            }
        })
    }

    private fun observeData() {
        viewModel.getAdminData()
        viewModel.dataLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    adapter.setList(it.data)
                }
                is Resource.Failure -> {
                }
            }
        })
    }


}