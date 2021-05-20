package com.example.studiawdaniiapp.ui.fragments.authorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentRegistrationBinding
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        onRegistrationButtonPressed()
    }

    private fun onRegistrationButtonPressed() {
        binding.registerBtn.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val password = binding.passwordText.text.toString().trim()
            val repeatPassword = binding.repeatPasswordText.text.toString().trim()
            observeData(EmailPassword(email, password), repeatPassword)
        }
    }

    private fun observeData(emailPassword: EmailPassword, repeatPassword: String) {
        viewModel.registration(emailPassword, repeatPassword)
        viewModel.registrationLiveData
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                        binding.errorId.text = "Waiting..."
                    }
                    is Resource.Success -> {
                        navController.navigate(R.id.action_registrationFragment_to_registrationDataFragment)
                    }
                    is Resource.Failure -> {
                        binding.errorId.text = it.string
                    }
                }
            })
    }
}




