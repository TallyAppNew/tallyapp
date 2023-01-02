package com.ngedev.tallyapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdView
//import com.google.android.gms.ads.MobileAds
//import com.ngedev.core.HelperAds
import com.ngedev.tallyapp.R
import com.ngedev.tallyapp.adapter.SubMenuAdapter
import com.ngedev.tallyapp.model.SubMenuModel
import com.ngedev.tallyapp.webview.WebViewActivity

class DrillingMudFragment : Fragment() {

//    private var mAdView: AdView? = null

    private var listMenu = listOf<SubMenuModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drilling_mud, container, false)
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
//        mAdView = activity?.findViewById(R.id.av_drilling_mud)
//        val adRequest = AdRequest.Builder().build()
//        mAdView?.loadAd(adRequest)

        listMenu = generateDrillingMudList()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        activity?.findViewById<RecyclerView>(R.id.rv_drilling_mud)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SubMenuAdapter(listMenu) {
                openWebActivity(it.htmlTitle)
            }
        }
    }

    private fun generateDrillingMudList() = listOf(
        SubMenuModel("drilling_mud_type.html", "Drilling Mud Type"),
        SubMenuModel("the_main_functions_of_a_drilling_mud.html", "The Main Function Of A Drilling Mud"),
        SubMenuModel("composition_of_drilling_mud.html", "Composition Of Drilling Mud"),
        SubMenuModel("drilling_mud_classification.html", "Drilling Mud Classification")
    )

    private fun openWebActivity(url: String) {
        val i = Intent(context, WebViewActivity::class.java)
        i.putExtra("url", url)
        i.putExtra("dir", "drilling_mud")
        startActivity(i)
    }
}