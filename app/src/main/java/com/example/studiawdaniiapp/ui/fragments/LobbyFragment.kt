package com.example.studiawdaniiapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentLobbyBinding
import com.example.studiawdaniiapp.ui.viewmodel.LobbyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LobbyFragment : Fragment() {

    var navController: NavController? = null;
    private lateinit var binding: FragmentLobbyBinding
    private val viewModel: LobbyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLobbyBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        observeData()
        observe()
        binding.profileButton.setOnClickListener {
            goToProfileFragment()
        }
    }

    fun goToProfileFragment() {
        navController!!.navigate(R.id.action_lobbyFragment_to_profileFragment)
    }

    fun observeData() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun observe() {
        viewModel.getUserProfileData().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.nameText.text = it.data.firstName
                    Toast.makeText(context, it.data.firstName, Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(context, it.string, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}