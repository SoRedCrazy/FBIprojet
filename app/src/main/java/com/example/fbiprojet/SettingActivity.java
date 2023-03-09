package com.example.fbiprojet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPreferences;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.idFrameLayout, new SettingsFragment())
                .commit();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this::onSharedPreferenceChanged);

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if(key.endsWith("darkmode")) {
                    boolean darkmode = sharedPreferences.getBoolean("darkmode", false);
                    if (darkmode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
                if(key.endsWith("langue")) {
                    String langue = sharedPreferences.getString("langue", "en-US");
                    if(langue.equals("fr-FR")){
                        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("fr-FR");
                        AppCompatDelegate.setApplicationLocales(appLocale);
                    }
                    if(langue.equals("en-US")){
                        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-EN");
                        AppCompatDelegate.setApplicationLocales(appLocale);
                    }
                }

            }

    }