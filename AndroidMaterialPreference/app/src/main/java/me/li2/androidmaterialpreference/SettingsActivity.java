package me.li2.androidmaterialpreference;


import android.app.Fragment;

public class SettingsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PreferencesFragment();
    }
}
