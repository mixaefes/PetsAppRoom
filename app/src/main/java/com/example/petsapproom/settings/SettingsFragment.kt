package com.example.petsapproom.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import com.example.petsapproom.R

class SettingsFragment:PreferenceFragmentCompat() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("SettingsFragment","i'm at settings fragment")
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }
}