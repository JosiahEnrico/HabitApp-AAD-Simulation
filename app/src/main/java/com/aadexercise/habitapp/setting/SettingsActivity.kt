package com.aadexercise.habitapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.aadexercise.habitapp.utils.DarkMode
import com.aadexercise.habitapp.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            setThemePref()
        }

        private fun setThemePref() {
            val pref = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            pref?.setOnPreferenceChangeListener { preference, newValue ->
                when (newValue){
                    "auto"  -> updateTheme(DarkMode.FOLLOW_SYSTEM.value)
                    "on"    -> updateTheme(DarkMode.ON.value)
                    "off"   -> updateTheme(DarkMode.OFF.value)
                }
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}