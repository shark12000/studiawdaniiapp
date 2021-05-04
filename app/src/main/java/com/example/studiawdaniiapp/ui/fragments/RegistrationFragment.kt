package com.example.studiawdaniiapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentRegistrationBinding
import com.example.studiawdaniiapp.ui.AuthListener
import com.example.studiawdaniiapp.viewmodel.AuthViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegistrationFragment : Fragment(),  View.OnClickListener, AuthListener, KoinComponent {
    var navController: NavController? = null;
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        viewModel.authListener = this
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerBtn.setOnClickListener(this)

        binding.emailText.setText("")
        binding.passwordText.setText("")
        binding.errorId.setText("")
    }

    private fun register() {
        val email = binding.emailText.text.toString().trim()
        val password = binding.passwordText.text.toString().trim()
        Toast.makeText(activity, "register btn pressed", Toast.LENGTH_SHORT).show()
        viewModel.signup(email, password)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.register_btn -> register()
        }
    }

    override fun onStarted() {

    }

    override fun onSuccess(message: String) {
        binding.errorId.text = message
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        binding.errorId.text = message
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}




