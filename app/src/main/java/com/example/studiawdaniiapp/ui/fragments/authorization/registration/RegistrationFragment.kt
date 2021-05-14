package com.example.studiawdaniiapp.ui.fragments.authorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentRegistrationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    var navController: NavController? = null;
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
        binding.registerBtn.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email = binding.emailText.text.toString().trim()
        val password = binding.passwordText.text.toString().trim()
        val repeatPassword = binding.repeatPasswordText.text.toString().trim()
        Toast.makeText(activity, "register btn pressed", Toast.LENGTH_SHORT).show()
        if (password == repeatPassword) {
            Toast.makeText(activity, "let's go!", Toast.LENGTH_SHORT).show()
            observeData(EmailPassword(email, password))
        } else {
            binding.errorId.text = "password do not match, try again"
        }
    }

    private fun observeData(emailPassword: EmailPassword) {
        viewModel.registration(emailPassword).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.errorId.text = "Waiting..."
                }
                is Resource.Success -> {
                    navController!!.navigate(R.id.action_registrationFragment_to_registrationDataFragment)
                    navController!!.navigate(R.id.action_registrationFragment_to_registrationDataFragment)
                    binding.errorId.text = it.data.toString()
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    binding.errorId.text = it.string
                    Toast.makeText(
                        context,
                        "An error has occurred:${it.string}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun observeNotification() {
        viewModel.sendEmailVerification().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Toast.makeText(context, "Notification was send", Toast.LENGTH_SHORT).show()
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




