package com.ngedev.tallyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdView
//import com.google.android.gms.ads.MobileAds
//import com.ngedev.core.HelperAds
import com.ngedev.tallyapp.R
import com.ngedev.tallyapp.adapter.DrillingMudProductAdapter
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStream

class DrillingMudProductFragment : Fragment() {

//    private var mAdView: AdView? = null

    private lateinit var rv_data: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drilling_mud_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        HelperAds.showAds(context, activity, R.string.menu_ads)
//
//        context?.let {
//            MobileAds.initialize(
//                it
//            ) { }
//        }
//
        rv_data = activity?.findViewById(R.id.rv_data_drilling_mud_product)!!

//        mAdView = activity?.findViewById(R.id.adView)
//        val adRequest = AdRequest.Builder().build()
//        mAdView?.loadAd(adRequest)

        val arrayData = loadJson()

        val adapter = arrayData?.let { DrillingMudProductAdapter(it) }
        val layoutManager = LinearLayoutManager(context)
        rv_data.layoutManager = layoutManager
        rv_data.adapter = adapter
    }

    private fun loadJson(): JSONArray? {
        try {
            val ism: InputStream = resources.assets.open("json/dmp.json")
            val size = ism.available()
            val buffer = ByteArray(size)
            ism.read(buffer)
            ism.close()
            val strJson = String(buffer)
            return JSONArray(strJson)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}