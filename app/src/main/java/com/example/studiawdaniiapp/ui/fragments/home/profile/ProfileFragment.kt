package com.example.studiawdaniiapp.ui.fragments.home.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentProfileBinding
import com.example.studiawdaniiapp.ui.fragments.home.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private var navController: NavController? = null;
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
        if (savedInstanceState == null) {
            navController = Navigation.findNavController(view)
            //Toast.makeText(context, view.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(context, viewModel.toString(), Toast.LENGTH_SHORT).show()
            observeProfileData()
            observeFirebaseData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeProfileData() {
        viewModel.getUserProfileData().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.tvAddress.text = it.data.firstName + " " + it.data.secondName
                    binding.firstName.text = it.data.firstName
                    binding.secondName.text = it.data.secondName
                    binding.phoneText.text = it.data.mobilePhone
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


    private fun observeFirebaseData() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.emailText.text = it.data?.email
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