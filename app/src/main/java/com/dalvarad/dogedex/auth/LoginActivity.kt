package com.dalvarad.dogedex.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.dalvarad.dogedex.MainActivity
import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.api.ApiResponseStatus
import com.dalvarad.dogedex.databinding.ActivityLoginBinding
import com.dalvarad.dogedex.models.User

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions, SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel = binding.loadingWheel

         viewModel.status.observe(this) {status ->
             when (status) {
                 is ApiResponseStatus.Error -> {
                     //ocultar progresbar
                     loadingWheel.visibility = View.GONE
                     //Toast.makeText(this, status.message, Toast.LENGTH_LONG).show()
                     showErrorDialog(status.message)
                 }
                 is ApiResponseStatus.Loading -> {
                     //Mostrar progressbar
                     loadingWheel.visibility = View.VISIBLE
                 }
                 is ApiResponseStatus.Succes -> {
                     //ocultar progresbar
                     loadingWheel.visibility = View.GONE
                 }

             }

         }

        viewModel.user.observe(this){
                user ->
            if (user != null ){
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidated(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.signUp(email,password,passwordConfirmation)
    }


    private fun showErrorDialog(messageId:Int){
        AlertDialog.Builder(this)
            .setTitle(R.string.there_was_an_error)
            .setMessage(messageId)
            .setPositiveButton(R.string.ok){
                _,_ ->
            }.create()
            .show()

    }
}