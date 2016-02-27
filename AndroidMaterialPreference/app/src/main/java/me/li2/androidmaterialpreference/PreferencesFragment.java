package me.li2.androidmaterialpreference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by weiyi on 2/27/16.
 */
public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
