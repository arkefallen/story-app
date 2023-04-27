package com.dicoding.android.intermediate.storyapp.ui.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dicoding.android.intermediate.storyapp.R
import com.dicoding.android.intermediate.storyapp.databinding.FragmentLoginResultBinding

class LoginResultFragment(private val message: String) : DialogFragment() {

    private lateinit var binding: FragmentLoginResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginResultBinding.inflate(inflater, container, false)
        binding.titleResult.text = getString(R.string.failure_login_title)
        binding.descriptionResult.text = "Error : ${message}"
        binding.lottieAnimationView.setAnimationFromUrl(getString(R.string.failure_register_animation))
        binding.btnLoginResult.setText(getString(R.string.failure_register_button))
        binding.btnLoginResult.setBackgroundColor(resources.getColor(R.color.red))
        binding.btnLoginResult.setOnClickListener {
            dialog?.cancel()
        }
        return binding.root
    }

}