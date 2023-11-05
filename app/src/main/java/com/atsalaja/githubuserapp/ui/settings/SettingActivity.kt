package com.atsalaja.githubuserapp.ui.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.atsalaja.githubuserapp.R
import com.atsalaja.githubuserapp.viewmodel.MainViewModel
import com.atsalaja.githubuserapp.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val toggleSwitchTheme = findViewById<SwitchMaterial>(R.id.toggle_switch_theme)
        val mainViewModel by viewModels<MainViewModel> {
            ViewModelFactory.getInstance(this.application, pref)
        }
        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                toggleSwitchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                toggleSwitchTheme.isChecked = false
            }
        }
        toggleSwitchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}