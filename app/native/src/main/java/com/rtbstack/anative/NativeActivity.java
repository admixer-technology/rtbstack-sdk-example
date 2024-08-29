package com.rtbstack.anative;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rtbstack.sdk.NativeAdAsset;
import com.rtbstack.sdk.NativeAdEventListener;
import com.rtbstack.sdk.NativeAdRequest;
import com.rtbstack.sdk.NativeAdRequestListener;
import com.rtbstack.sdk.NativeAdResponse;
import com.rtbstack.sdk.NativeAdView;
import com.rtbstack.sdk.NativeIconView;
import com.rtbstack.sdk.ResultCode;
import com.rtbstack.sdk.SDKSettings;
import com.rtbstack.sdk.utils.Clog;

import java.util.EnumSet;

public class NativeActivity extends AppCompatActivity {
    NativeAdView nativeAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_native);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nativeAdView = findViewById(R.id.native_ad_layout);

        SDKSettings.setRequestBaseUrl("SERVER URL"); //Your server url, obtained from the Edit Endpoint window in UI

        NativeAdRequest adRequest = new NativeAdRequest(this, "TAG ID");  // Your custom tag id, may be any string value
        EnumSet<NativeAdAsset> assets = EnumSet.of(
                NativeAdAsset.IMAGE_ICON,
                NativeAdAsset.TITLE,
                NativeAdAsset.DESCRIPTION
        );
        adRequest.setRequiredAssets(assets);

        EnumSet<NativeAdAsset> optAssets = EnumSet.of(
                NativeAdAsset.CTA,
                NativeAdAsset.SPONSORED
        );
        adRequest.setOptionalAssets(optAssets);

        adRequest.setListener(new NativeAdRequestListener() {
            @Override
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                TextView title = findViewById(R.id.native_title);
                TextView description = findViewById(R.id.native_description);
                NativeIconView icon = findViewById(R.id.native_icon);
                Button buttonCta = findViewById(R.id.native_button_cta);
                TextView sponsored = findViewById(R.id.native_sponsored);

                title.setText(nativeAdResponse.getTitle());
                description.setText(nativeAdResponse.getDescription());

                //Optional parameters
                if (nativeAdResponse.getCallToAction() != null) {
                    buttonCta.setVisibility(View.VISIBLE);
                    buttonCta.setText(nativeAdResponse.getCallToAction());
                } else {
                    buttonCta.setVisibility(View.GONE);
                }

                if (nativeAdResponse.getSponsoredBy() != null) {
                    sponsored.setVisibility(View.VISIBLE);
                    sponsored.setText(nativeAdResponse.getSponsoredBy());
                } else {
                    sponsored.setVisibility(View.GONE);
                }


                nativeAdView.setTitleView(title);
                nativeAdView.setDescriptionView(description);
                nativeAdView.setIconView(icon);
                nativeAdView.setCallToActionView(buttonCta);

                nativeAdResponse.registerViews(nativeAdView, new NativeAdEventListener() {
                    @Override
                    public void onAdWasClicked() {

                    }

                    @Override
                    public void onAdWillLeaveApplication() {

                    }

                    @Override
                    public void onAdWasClicked(String clickUrl, String clickFallbackUrl) {

                    }
                });
            }

            @Override
            public void onAdFailed(ResultCode errorcode) {

            }
        });

        adRequest.loadAd();
    }
}