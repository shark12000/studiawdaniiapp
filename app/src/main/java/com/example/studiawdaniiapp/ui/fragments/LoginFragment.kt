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
import com.example.studiawdaniiapp.data.models.EmailPassword
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.databinding.FragmentLoginBinding
import com.example.studiawdaniiapp.ui.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var navController: NavController? = null;
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
        binding.registerBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.emailText.text.toString().trim()
        val password = binding.passwordText.text.toString().trim()
        observeData(EmailPassword(email, password))
    }

    private fun observeData(emailPassword: EmailPassword) {
        viewModel.signIn(emailPassword).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.errorId.text = "Waiting..."
                }
                is Resource.Success -> {
                    navController!!.navigate(R.id.action_loginFragment_to_lobbyFragment)
                    binding.errorId.text = "Login passed successfully "
                }
                is Resource.Failure -> {
                    binding.errorId.text = "Failure"
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