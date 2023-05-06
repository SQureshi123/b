package edu.psu.szq5043.budgetapp475;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.SharedPreferences;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements
            SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            // Set the summary for the currency preference
            ListPreference currencyPreference = findPreference(getString(R.string.pref_currency_key));
            currencyPreference.setSummary(currencyPreference.getEntry());
        }

        @Override
        public void onResume() {
            super.onResume();

            // Register the listener to receive preference change events
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();

            // Unregister the listener to avoid memory leaks
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            // Update the summary when the currency preference changes
            if (key.equals(getString(R.string.pref_currency_key))) {
                ListPreference currencyPreference = findPreference(key);
                currencyPreference.setSummary(currencyPreference.getEntry());
            }
        }
    }
}
