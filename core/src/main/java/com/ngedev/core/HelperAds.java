package com.ngedev.core;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class HelperAds {
    public static void showAds(Context context, Activity activity, int adUnit){
        DialogLoading dialog = new DialogLoading(activity);
//        AdRequest adRequest = new AdRequest.Builder().build();

        dialog.showDialog();
//        InterstitialAd.load(context, context.getResources().getString(adUnit), adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        Log.d("onAdLoaded", "onAdLoaded");
//                        interstitialAd.show(activity);
//                        dialog.cancelDialog();
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        Log.d("onAdFailedToLoad", loadAdError.getMessage());
//                        dialog.cancelDialog();
//                    }
//                });
    }
}
