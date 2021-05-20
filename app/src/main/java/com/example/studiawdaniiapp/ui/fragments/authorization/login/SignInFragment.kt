package com.example.studiawdaniiapp.ui.fragments.authorization.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentLoginBinding
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: SignInViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        onSignInButtonPressed()
    }

    private fun onSignInButtonPressed() {
        binding.signInBtn.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val password = binding.passwordText.text.toString().trim()
            signIn(EmailPassword(email = email, password = password))
        }
    }

    private fun signIn(emailPassword: EmailPassword) {
        viewModel.signIn(emailPassword = emailPassword)
        viewModel.signInLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.errorId.text = "Waiting..."
                }
                is Resource.Success -> {
                    observeAdminLiveData()

                }
                is Resource.Failure -> {
                    binding.errorId.text = "Failure"
                }
            }
        })

    }

    private fun observeAdminLiveData() {
        viewModel.isAdmin()
        viewModel.isAdminLiveData.observe(viewLifecycleOwner, {
            // TODO
            if (it) {
                navController.navigate(R.id.action_loginFragment_to_nav_graph_admin)
            } else {
                navController.navigate(R.id.action_loginFragment_to_nav_graph_home)
            }
        })
    }

}