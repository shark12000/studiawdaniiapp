package com.example.studiawdaniiapp.ui.fragments.home.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentLobbyBinding
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class LobbyFragment : Fragment() {

    private lateinit var navController: NavController
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
        binding.bottomNavigationMenu.setupWithNavController(navController)
        onSignOutButtonClicked()
        onSubmitButtonPressed()
        observeData()
        observeStatus()
    }

    private fun onSubmitButtonPressed() {
        binding.submitButton.setOnClickListener() {
            navController.navigate(R.id.action_lobbyFragment_to_educationInfoFragment)
        }
    }

    private fun onSignOutButtonClicked() {
        binding.signout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        viewModel.signOut()
        navController.setGraph(R.navigation.nav_graph_authorization)
    }

    private fun observeStatus() {
        viewModel.getStatus()
        viewModel.statusLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.statusText.text = it.data.status.trim()
                    binding.submitButton.visibility = View.GONE

                }
                is Resource.Failure -> {
                    Toast.makeText(context, it.string, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeData() {
        viewModel.getUserProfileData()
        viewModel.userLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.firstNameText.text = it.data.firstName
                }
                is Resource.Failure -> {
                    Toast.makeText(context, it.string, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}