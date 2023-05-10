package com.dicoding.android.intermediate.storyapp.ui.customview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dicoding.android.intermediate.storyapp.R
import com.dicoding.android.intermediate.storyapp.databinding.FragmentRegisterResultBinding
import com.dicoding.android.intermediate.storyapp.ui.LoginActivity

class RegisterResultFragment(private val result: Boolean = false, private val status: String) : DialogFragment() {

    private lateinit var binding: FragmentRegisterResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterResultBinding.inflate(inflater, container, false)
        if (result == true) {
            Log.e(this::class.java.simpleName, "onViewCreated: Sukses")
            binding.titleResult.text = getString(R.string.failure_register_title)
            if (status == "Email is already taken") {
                binding.descriptionResult.text = getString(R.string.failure_register_description_email_taken)
            } else {
                binding.descriptionResult.text = getString(R.string.failure_register_description)
            }
            binding.lottieAnimationView.setAnimationFromUrl(getString(R.string.failure_register_animation))
            binding.btnRegisterResult.setText(getString(R.string.failure_register_button))
            binding.btnRegisterResult.setBackgroundColor(resources.getColor(R.color.red))
            binding.btnRegisterResult.setOnClickListener {
                dialog?.cancel()
            }
        } else {
            Log.e(this::class.java.simpleName, "onViewCreated: Gagal")
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
        }
        return binding.root
    }


}