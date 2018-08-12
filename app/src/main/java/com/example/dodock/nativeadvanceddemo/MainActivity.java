package com.example.dodock.nativeadvanceddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;

public class MainActivity extends AppCompatActivity {
    private AdLoader adLoader;
    private boolean isAdLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAdLoading) {
            loadNativeAds();
        }
    }
// Loading Request................
    private void loadNativeAds() {
        if (getApplicationContext() != null) {
            AdLoader.Builder builder = new AdLoader.Builder(getApplicationContext(), "your_ad_unit_id");
            adLoader = builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                @Override
                public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
                    if (!adLoader.isLoading()) {
                        isAdLoading = false;
                    }
                    FrameLayout frameLayout = findViewById(R.id.fl_placeholder);
                    NativeAppInstallAdView appInstallAdView = (NativeAppInstallAdView)
                            getLayoutInflater().inflate(R.layout.layout_native_large, null);
//                    Populate your add
                    pupulateNativeAd(nativeAppInstallAd, appInstallAdView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(appInstallAdView);

                }
            }).withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    loadNativeAds(); // Recursive request if ads loading failded
                }
            }).build();
        }
    }
// Populate Ads .....
    private void pupulateNativeAd(NativeAppInstallAd nativeAppInstallAd, NativeAppInstallAdView appInstallAdView) {
        MediaView mediaView = appInstallAdView.findViewById(R.id.appinstall_media);
        appInstallAdView.setMediaView(mediaView);
        appInstallAdView.setHeadlineView(appInstallAdView.findViewById(R.id.appinstall_headline));
        appInstallAdView.setCallToActionView(appInstallAdView.findViewById(R.id.appinstall_call_to_action));
    }
}
