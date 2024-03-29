package com.dalvarad.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.databinding.FragmentSignUpBinding
import com.dalvarad.dogedex.isValidEmail


class SignUpFragment : Fragment() {

    interface SignUpFragmentActions {
        fun onSignUpFieldsValidated(
            email: String,
            password: String,
            passwordConfirmation: String
        )
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSingUpButton()
        return binding.root
    }

    private fun setupSingUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {

        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""

        val email = binding.emailEdit.text.toString()
        if (!isValidEmail(email)) {
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if (password.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_incorrecto)
            return
        }
        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if (password.isEmpty()) {
            binding.confirmPasswordInput.error = getString(R.string.password_incorrecto)
            return
        }

        if (password != passwordConfirmation) {
            binding.confirmPasswordInput.error = getString(R.string.password_no_coinciden)
            return
        }

        signUpFragmentActions.onSignUpFieldsValidated(email,password, passwordConfirmation)
    }



}