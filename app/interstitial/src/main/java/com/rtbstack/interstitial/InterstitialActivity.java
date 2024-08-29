package com.rtbstack.interstitial;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rtbstack.sdk.AdListener;
import com.rtbstack.sdk.AdView;
import com.rtbstack.sdk.InterstitialAdView;
import com.rtbstack.sdk.ResultCode;
import com.rtbstack.sdk.SDKSettings;

public class InterstitialActivity extends AppCompatActivity {
    private InterstitialAdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interstitial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SDKSettings.setRequestBaseUrl("SERVER URL"); //Your server url, obtained from the Edit Endpoint window in UI

        adView = new InterstitialAdView(this);
        adView.setTagID("TAG ID"); // Your custom tag id, may be any string value
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(AdView adView) {
                InterstitialAdView interstitialAdView = (InterstitialAdView) adView;
                if(interstitialAdView.isReady()) {
                    interstitialAdView.show();
                }
            }

            @Override
            public void onAdRequestFailed(AdView adView, ResultCode resultCode) {
            }

            @Override
            public void onAdExpanded(AdView adView) {
            }

            @Override
            public void onAdCollapsed(AdView adView) {
            }

            @Override
            public void onAdClicked(AdView adView) {
            }

            @Override
            public void onAdClicked(AdView adView, String s) {
            }
        });

        adView.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adView != null) {
            adView.activityOnDestroy();
        }
    }
}