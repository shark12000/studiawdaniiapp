package com.example.studiawdaniiapp.ui.fragments.home.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.databinding.FragmentProfileBinding
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        observeData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.getUserProfileData()
        viewModel.userLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.tvAddress.text = it.data.firstName + " " + it.data.secondName
                    binding.firstName.text = it.data.firstName
                    binding.secondName.text = it.data.secondName
                    binding.phoneText.text = it.data.mobilePhone
                    binding.emailText.text = it.data.email
                }
                is Resource.Failure -> {
                }
            }
        })
    }
}