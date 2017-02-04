package hdfg159.qqsendpoke.view;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import hdfg159.qqsendpoke.R;
import hdfg159.qqsendpoke.utilities.XSharedPreferencesUtil;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private EditTextPreference timeInterval, pokeTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.sendpoke_setting);
        initView();
    }

    private void initView() {
        setupActionBar();
        pokeTimes = (EditTextPreference) getPreferenceScreen().findPreference(XSharedPreferencesUtil.KEY_POKE_TIMES);
        timeInterval = (EditTextPreference) getPreferenceScreen().findPreference(XSharedPreferencesUtil.KEY_TIME_INTERVAL);
        pokeTimes.setSummary(getPokeTimesFromPrefences());
        timeInterval.setSummary(getTimeIntervalFromPrefences());
        toggleEnableModulestate();
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
            case XSharedPreferencesUtil.KEY_POKE_TIMES:
                pokeTimes.setSummary(getPokeTimesFromPrefences());
                break;
            case XSharedPreferencesUtil.KEY_TIME_INTERVAL:
                timeInterval.setSummary(getTimeIntervalFromPrefences());
                break;
            case XSharedPreferencesUtil.KEY_IS_ENABLE:
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
        return getPreferenceManager().getSharedPreferences().getBoolean(XSharedPreferencesUtil.KEY_IS_ENABLE, true);
    }

    private String getTimeIntervalFromPrefences() {
        return getPreferenceManager().getSharedPreferences().getString(XSharedPreferencesUtil.KEY_TIME_INTERVAL, "0");
    }

    private String getPokeTimesFromPrefences() {
        return getPreferenceManager().getSharedPreferences().getString(XSharedPreferencesUtil.KEY_POKE_TIMES, "1");
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
