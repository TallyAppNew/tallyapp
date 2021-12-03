/*
 * Copyright (c) Tellabs 2021.
 * created by Yusriyadi
 *
 */
package com.ngedev.tallyapp.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.izikode.izilib.veinview.VeinView
import com.ngedev.tallyapp.R


class WebViewActivity : AppCompatActivity() {
  private val veinView: VeinView by lazy { findViewById<VeinView>(R.id.veinview) }
  lateinit var mAdView : AdView
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_web_view)

    MobileAds.initialize(this) { }
    mAdView = findViewById<AdView>(R.id.adView)
    val adRequest = AdRequest.Builder().build()
    mAdView.loadAd(adRequest)

    val url = intent?.getStringExtra("url")?:"basic_formula/AnnularVelocity.html"
    val folder = intent?.getStringExtra("dir")

    veinView.setInitialScale(1)

    veinView.settings.apply {
      loadWithOverviewMode = true
      useWideViewPort = true
      builtInZoomControls = true
    }
  veinView.loadUrl("file:///android_asset/$folder/$url")

    //HelperAds.showAds(applicationContext, this, R.string.menu_ads)
  }
}