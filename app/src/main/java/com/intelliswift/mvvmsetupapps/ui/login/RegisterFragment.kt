package com.intelliswift.mvvmsetupapps.ui.login

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.intelliswift.mvvmsetupapps.R
import com.intelliswift.mvvmsetupapps.databinding.FragmentRegisterBinding
import com.intelliswift.mvvmsetupapps.models.UserRequest
import com.intelliswift.mvvmsetupapps.utils.Helper
import com.intelliswift.mvvmsetupapps.utils.Helper.Companion.hideKeyboard
import com.intelliswift.mvvmsetupapps.utils.NetworkResult
import com.intelliswift.mvvmsetupapps.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by activityViewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        if (tokenManager.getToken() != null) {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            hideKeyboard(it)
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequest = getUserRequest()
                authViewModel.registerUser(userRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }
        bindObservers()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.txtEmail.text.toString()
        val userName = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()
        return authViewModel.validateCredentials(emailAddress, userName, password, false)
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(
                txtUsername.text.toString(),
                false,
                "d9335e897c1999d9",
                "Android",
                txtEmail.text.toString(),
                txtPassword.text.toString(),
            )
        }
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data?.result.toString() == "failed")
                        Helper.showToast(activity!!,it.data?.message.toString())
                    else
                        Helper.showToast(activity!!,"Signup Successful!")
                }

                is NetworkResult.Error -> {
                    showValidationErrors(it.message.toString())
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}