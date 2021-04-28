package com.example.studiawdaniiapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.databinding.FragmentWelcomeBinding
import org.koin.core.KoinComponent


class WelcomeFragment : Fragment(), View.OnClickListener, KoinComponent {

    var navController: NavController? = null;
    private lateinit var binding: FragmentWelcomeBinding;
    private val WELCOME_FRAGMENT_TAG: String = WelcomeFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        when(v!!.id) {
            R.id.login_btn -> navController!!.navigate(R.id.action_welcomeFragment_to_loginFragment2)
            R.id.register_btn -> navController!!.navigate(R.id.action_welcomeFragment_to_registrationFragment2)
            R.id.menu_btn -> navController!!.navigate(R.id.action_welcomeFragment_to_homeFragment)
        }
    }
}