package com.dicoding.android.intermediate.storyapp.ui.customview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dicoding.android.intermediate.storyapp.R
import com.dicoding.android.intermediate.storyapp.databinding.FragmentRegisterResultBinding
import com.dicoding.android.intermediate.storyapp.ui.LoginActivity

class RegisterResultFragment(private val result: Boolean, private val status: String) : DialogFragment() {

    private lateinit var binding: FragmentRegisterResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterResultBinding.inflate(inflater, container, false)
        if (result == true) {
            binding.titleResult.text = getString(R.string.success_register_title)
            binding.descriptionResult.text = getString(R.string.success_register_description)
            binding.lottieAnimationView.setAnimationFromUrl(getString(R.string.success_register_animation))
            binding.btnRegisterResult.setText(getString(R.string.success_register_button))
            binding.btnRegisterResult.setBackgroundColor(resources.getColor(R.color.blue))
            binding.btnRegisterResult.setOnClickListener {
                val loginIntent = Intent(requireActivity(), LoginActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(loginIntent)
            }
        } else {
            binding.titleResult.text = getString(R.string.failure_register_title)
            if (status == "Email is already taken") {
                binding.descriptionResult.text = "User sudah terdaftar di sistem"
            } else {
                binding.descriptionResult.text = getString(R.string.success_register_description)
            }
            binding.lottieAnimationView.setAnimationFromUrl(getString(R.string.failure_register_animation))
            binding.btnRegisterResult.setText(getString(R.string.failure_register_button))
            binding.btnRegisterResult.setBackgroundColor(resources.getColor(R.color.red))
            binding.btnRegisterResult.setOnClickListener {
                dialog?.cancel()
            }
        }
        return binding.root
    }

    companion object {
        val EXTRA_REGISTER_RESULT = "extra_register_result"
        val EXTRA_REGISTER_DESC = "extra_register_desc"
    }

}