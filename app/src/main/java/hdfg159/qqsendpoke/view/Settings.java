package hdfg159.qqsendpoke.view;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import hdfg159.qqsendpoke.R;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private EditTextPreference timeInterval, pokeTimes;
    private SwitchPreference isEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.sendpoke_setting);
        initView();
    }

    private void initView() {
        setupActionBar();
        isEnable = (SwitchPreference) getPreferenceScreen().findPreference("IsEnable");
        pokeTimes = (EditTextPreference) getPreferenceScreen().findPreference("PokeTimes");
        timeInterval = (EditTextPreference) getPreferenceScreen().findPreference("TimeInterval");
        pokeTimes.setSummary(getPokeTimesFromPrefences());
        timeInterval.setSummary(getTimeIntervalFromPrefences());
        toggleEnableModulestate();
    }

    private String getPokeTimesFromPrefences() {
        return getPreferenceManager().getSharedPreferences().getString("PokeTimes", "1");
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        switch (s) {
            case "PokeTimes":
                pokeTimes.setSummary(getPokeTimesFromPrefences());
                break;
            case "TimeInterval":
                timeInterval.setSummary(getTimeIntervalFromPrefences());
                break;
            case "IsEnable":
                toggleEnableModulestate();
                break;
        }
    }

    private void toggleEnableModulestate() {
        if (isEnableModuleFromPrefences()) {
            pokeTimes.setEnabled(true);
            timeInterval.setEnabled(true);
        } else {
            pokeTimes.setEnabled(false);
            timeInterval.setEnabled(false);
        }
    }

    private boolean isEnableModuleFromPrefences() {
        return getPreferenceManager().getSharedPreferences().getBoolean("IsEnable", true);
    }

    private String getTimeIntervalFromPrefences() {
        return getPreferenceManager().getSharedPreferences().getString("TimeInterval", "0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
