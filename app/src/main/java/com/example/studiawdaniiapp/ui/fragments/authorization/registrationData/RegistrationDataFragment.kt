package com.example.studiawdaniiapp.ui.fragments.authorization.registrationData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentRegistrationDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegistrationDataFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentRegistrationDataBinding
    private val viewModel: RegistrationDataViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationDataBinding.inflate(inflater, container, false)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        observeUser()
        onRegistrationButtonPressed()
    }

    private fun onRegistrationButtonPressed() {
        binding.continueButton.setOnClickListener {
            provideProfileData()
        }
    }

    private fun provideProfileData() {
        val firstName = binding.editTextFirstName.text.toString().trim()
        val lastName = binding.editTextLastName.text.toString().trim()
        val phoneNumber = binding.editTextPhone2.text.toString().trim()
        updateUser(
            firstName = firstName,
            lastName = lastName,
            mobilePhone = phoneNumber
        )
    }



    private fun updateUser(firstName: String, lastName: String, mobilePhone: String) {
        viewModel.updateProfileData(firstName = firstName, lastName =  lastName, mobilePhone = mobilePhone)
        navController.navigate(R.id.action_registrationDataFragment_to_nav_graph_home)
    }

    private fun observeUser() {
        viewModel.getUser()
        viewModel.profileLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                }
                is Resource.Failure -> {
                    binding.errorText.text = it.string
                    Toast.makeText(context, it.string, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}