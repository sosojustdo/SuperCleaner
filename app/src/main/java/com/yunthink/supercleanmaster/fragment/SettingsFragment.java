package com.yunthink.supercleanmaster.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.superbooster.cleaning.R;
import com.yunthink.supercleanmaster.ads.BannerAdvertisementActivity;
import com.yunthink.supercleanmaster.ads.InterstitialAdvertisementActivity;
import com.yunthink.supercleanmaster.base.FragmentContainerActivity;
import com.yunthink.supercleanmaster.ui.AboutActivity;
import com.yunthink.supercleanmaster.ui.PrivacypolicyActivity;
import com.yunthink.supercleanmaster.ui.TermsofuseActivity;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {


    public static void launch(Activity from) {
        FragmentContainerActivity.launch(from, SettingsFragment.class, null);
    }

    private Preference pAbout;// Github
    private Preference pPrivacypolicy;
    private Preference pTermsofuse;
    private Preference pbanner;
    private Preference pinterstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addPreferencesFromResource(R.xml.ui_settings);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        getActivity().getActionBar().setTitle(R.string.title_settings);

        pPrivacypolicy = findPreference("pPrivacypolicy");
        pPrivacypolicy.setOnPreferenceClickListener(this);

        pTermsofuse = findPreference("pTermsofuse");
        pTermsofuse.setOnPreferenceClickListener(this);

        pAbout = findPreference("pAbout");
        pAbout.setOnPreferenceClickListener(this);

        pbanner = findPreference("pbanner");
        pbanner.setOnPreferenceClickListener(this);

        pinterstitial = findPreference("pinterstitial");
        pinterstitial.setOnPreferenceClickListener(this);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if ("pPrivacypolicy".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), PrivacypolicyActivity.class));
        }
        else if ("pTermsofuse".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), TermsofuseActivity.class));
        }
        else if ("pAbout".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
        }
        else if ("pbanner".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), BannerAdvertisementActivity.class));
        }
        else if ("pinterstitial".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), InterstitialAdvertisementActivity.class));
        }
        return false;
    }



}
