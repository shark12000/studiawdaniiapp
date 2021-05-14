package com.example.studiawdaniiapp.ui.fragments.authorization.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null;
    private lateinit var binding: FragmentWelcomeBinding;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.loginBtn.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)
        binding.menuBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_btn -> navController!!.navigate(R.id.action_welcomeFragment_to_loginFragment)
            R.id.register_btn -> navController!!.navigate(R.id.action_welcomeFragment_to_registrationFragment)
        }
    }
}