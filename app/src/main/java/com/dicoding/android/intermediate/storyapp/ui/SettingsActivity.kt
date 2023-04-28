package com.dicoding.android.intermediate.storyapp.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.android.intermediate.storyapp.R
import com.dicoding.android.intermediate.storyapp.databinding.ActivitySettingsBinding
import com.dicoding.android.intermediate.storyapp.repo.UserPreferences
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModel
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModelFactory

class SettingsActivity : AppCompatActivity() {
    private lateinit var settingsBinding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Setelan"
        supportActionBar?.elevation = 0f
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)

        val itemCardLanguage = settingsBinding.cardLang
        itemCardLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        val authViewModel : AuthViewModel by viewModels { AuthViewModelFactory.getInstance(this) }
        val userPreferences = UserPreferences.getInstance(dataStore)
        authViewModel.setPreferences(userPreferences)

        val itemCardLogout = settingsBinding.cardLogout
        itemCardLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setNegativeButton("Tidak",
                    { dialogInterface, id ->
                        dialogInterface.cancel()
                    }
                )
                .setPositiveButton("Ya",
                    { dialogInterface, id ->
                        authViewModel.clearData()
                        val loginIntent = Intent(this, LoginActivity::class.java)
                        startActivity(loginIntent)
                        finish()
                    }
                )
                .create()
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}