package com.yunthink.supercleanmaster.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.superbooster.cleanup.R;
import com.yunthink.supercleanmaster.base.FragmentContainerActivity;
import com.yunthink.supercleanmaster.ui.AboutActivity;
import com.yunthink.supercleanmaster.ui.PrivacypolicyActivity;
import com.yunthink.supercleanmaster.ui.TermsofuseActivity;
import com.yunthink.supercleanmaster.utils.AppUtil;
import com.yunthink.supercleanmaster.utils.T;
import com.yunthink.supercleanmaster.utils.Utils;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {


    public static void launch(Activity from) {
        FragmentContainerActivity.launch(from, SettingsFragment.class, null);
    }

    private Preference createShortCut;



    private Preference pAbout;// Github
    private Preference pPrivacypolicy;
    private Preference pTermsofuse;

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

        createShortCut = findPreference("createShortCut");
        createShortCut.setOnPreferenceClickListener(this);
        pPrivacypolicy = findPreference("pPrivacypolicy");
        pPrivacypolicy.setOnPreferenceClickListener(this);
        pTermsofuse = findPreference("pTermsofuse");
        pTermsofuse.setOnPreferenceClickListener(this);

        pAbout = findPreference("pAbout");
        pAbout.setOnPreferenceClickListener(this);
        //initData();
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {

        if ("createShortCut".equals(preference.getKey())) {
            createShortCut();
        } else if ("pPrivacypolicy".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), PrivacypolicyActivity.class));
        }
        else if ("pTermsofuse".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), TermsofuseActivity.class));
        }
        else if ("pAbout".equals(preference.getKey())) {
            getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
        }
        return false;
    }

    /**
    private void initData() {
        String appID = "wxa263da737a20300e";
        String appSecret = "381a2fab6466410c674afaa40c77c953";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getActivity(),appID,appSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(),appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }**/


    private void createShortCut() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, SettingsFragment.this.getString(R.string.short_cut_name));
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.short_cut_icon));
        Intent i = new Intent();
        i.setAction("com.yzy.shortcut");
        i.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        getActivity().sendBroadcast(intent);
        T.showLong(getActivity(), SettingsFragment.this.getString(R.string.create_icon));

    }


    public  void startMarket() {
        Uri uri = Uri.parse(String.format("market://details?id=%s", AppUtil.getPackageInfo(getActivity()).packageName));
        if (Utils.isIntentSafe(getActivity(), uri)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
        }
        // 没有安装市场
        else {
            T.showLong(getActivity(),"无法打开应用市场");

        }
    }
}
