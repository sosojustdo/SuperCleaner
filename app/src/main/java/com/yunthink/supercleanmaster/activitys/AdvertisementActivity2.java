package com.yunthink.supercleanmaster.activitys;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.superbooster.cleaning.R;
import com.yunthink.supercleanmaster.base.BaseSwipeBackActivity;
import com.yunthink.supercleanmaster.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daipeng on 2019/2/18.
 * 一键加速界面
 */

public class AdvertisementActivity2 extends BaseSwipeBackActivity {

    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;

    ActionBar ab;
    ImageView bck_Btn;
    TextView tv_title;
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ab=getActionBar();
        ab.hide();
        tv_title = (TextView) findViewById(R.id.title_gg);
        bck_Btn = (ImageView) findViewById(R.id.bck_gg);
        tv_content = (TextView) findViewById(R.id.tv_content);
        initTitle();
        bck_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdvertisementActivity2.this,MainActivity.class);
                startActivity(intent);
                AdvertisementActivity2.this.finish();

            }
        });

    }

    private void initTitle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        tv_title.setText(title);
        tv_content.setText(content);
        showNativeAd();
    }

    private void showNativeAd() {
        nativeAd = new NativeAd(this, "360089281055474_362944477436621");
        nativeAd.setAdListener(new NativeAdListener() {

            @Override
            public void onError(Ad ad, AdError error) {
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // On logging impression callback
            }

            @Override
            public void onMediaDownloaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAd(nativeAd);
            }
        });

        // Request an ad
        nativeAd.loadAd();
        // Please call this method at appropriate timing in your project
        showNativeAdWithDelay();
    }

    private void showNativeAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         */
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Check if nativeAd has been loaded successfully
                if(nativeAd == null || !nativeAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if(nativeAd.isAdInvalidated()) {
                    return;
                }
                inflateAd(nativeAd); // Inflate Native Ad into Container same as previous code example
            }
        }, 2000); // Show the ad after 2 seconds
    }

    private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(AdvertisementActivity2.this);
        adView = (LinearLayout) inflater.inflate(R.layout.native_ll, nativeAdContainer, false);
        nativeAdContainer.addView(adView);

        // Create native UI using the ad metadata.
        ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

}
