package pl.kasprzykmaciej.szpieg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

//    EditTextPreference mprefNumber;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.preferences);


        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();
        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            String value = sharedPreferences.getString(p.getKey(), "");
            if (p instanceof EditTextPreference) {
                // For EditTextPreferences, set the summary to the value's simple string representation.
                p.setSummary(value);
            }



        }

        //add a listener to number preference to check if a correct number was entered
        Preference preference = findPreference(getString(R.string.pref_number_key));
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (preference instanceof EditTextPreference) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                preference.setSummary(value);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // using the onPreferenceChange listener for checking whether the number setting was set to a valid value.

        Toast error = Toast.makeText(getContext(), "Please select a number between 3 and 15", Toast.LENGTH_SHORT);

        // Double check that the preference is the number preference
        String numberKey = getString(R.string.pref_number_key);
        if (preference.getKey().equals(numberKey)) {
            String stringSize = (String) newValue;
            try {
                int number = Integer.parseInt(stringSize);
                // If the number is outside of the acceptable range, show an error.
                if (number > 15 || number < 3) {
                    error.show();
                    return false;
                }
            } catch (NumberFormatException nfe) {
                // If whatever the user entered can't be parsed to a number, show an error
                error.show();
                return false;
            }
        }
        return true;
    }
}
