package me.li2.androidmaterialpreference;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

/**
 * Created by weiyi on 2/27/16.
 */
public class PreferencesFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String PREF_KEY_SWITCH = "switch_preference";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SwitchPreference switchPreference = (SwitchPreference) findPreference(PREF_KEY_SWITCH);
        switchPreference.setOnPreferenceClickListener(this);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(PREF_KEY_SWITCH)) {
            boolean isChecked = ((SwitchPreference)preference).isChecked();
            String message = "Switch is " + (isChecked ? "on" : "off");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
