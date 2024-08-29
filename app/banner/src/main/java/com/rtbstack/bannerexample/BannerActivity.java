package com.rtbstack.bannerexample;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rtbstack.sdk.BannerAdView;
import com.rtbstack.sdk.SDKSettings;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        SDKSettings.setRequestBaseUrl("SERVER URL"); //Your server url, obtained from the Edit Endpoint window in UI

        FrameLayout adContainer = findViewById(R.id.container);
        BannerAdView adView = new BannerAdView(this);
        adView.setAdSize(320, 250);
        adView.setTagID("TAG ID"); // Your custom tag id, may be any string value
        adContainer.addView(adView);
        adView.loadAd();
    }
}