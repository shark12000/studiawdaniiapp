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
import com.example.studiawdaniiapp.databinding.FragmentLoginBinding
import com.example.studiawdaniiapp.ui.AuthListener
import com.example.studiawdaniiapp.viewmodel.AuthViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginFragment : Fragment(), View.OnClickListener, KoinComponent, AuthListener {
    var navController: NavController? = null;
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel.authListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.registerBtn.setOnClickListener(this)

        binding.errorId.text = ""
        binding.passwordText.setText("")
        binding.emailText.setText("")

    }

    private fun login() {
        val email = binding.emailText.text.toString().trim()
        val password = binding.passwordText.text.toString().trim()

        Toast.makeText(activity, "Hahah", Toast.LENGTH_SHORT).show()

        if(email != "" && password != "") {
            val result = viewModel.login(email, password)
        } else {
            Toast.makeText(activity, "AZAZAZZAZ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.register_btn -> login()
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