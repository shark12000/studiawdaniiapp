package com.example.studiawdaniiapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.studiawdaniiapp.R
import com.example.studiawdaniiapp.viewmodel.SignInViewModel
import com.firebase.ui.auth.AuthUI
import java.util.Arrays

class SignInActivity : AppCompatActivity() {
    companion object {
        private val RC_SIGN_IN: Int = 42
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        checkIfSignedIn()
        setContentView(R.layout.signin_activity)
    }

    private fun checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, Observer { user ->
            if (user != null)
                goToMainActivity()
        })
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun signIn(v: View) {
        val providers: List<AuthUI.IdpConfig> = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent: Intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.common_google_signin_btn_icon_light)
            .build()

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            handleSignInRequest(resultCode)
        }
    }

    private fun handleSignInRequest(resultCode: Int) {
        if (resultCode == RESULT_OK)
            goToMainActivity()
        else
            Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show()
    }
}