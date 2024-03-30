package com.example.submissionawalfundamental.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.submissionawalfundamental.databinding.ActivityThemeSettingBinding
import com.example.submissionawalfundamental.helper.SettingPreferences
import com.example.submissionawalfundamental.helper.SettingViewModelFactory
import com.example.submissionawalfundamental.helper.dataStore
import com.example.submissionawalfundamental.viewModel.ThemeViewModel

class ThemeSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Settings"

        val button = binding.themeToggle

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                button.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                button.isChecked = false
            }
        }

        button.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}