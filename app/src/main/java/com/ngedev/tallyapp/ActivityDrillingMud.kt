package com.ngedev.tallyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ngedev.tallyapp.adapter.DrillingMudTabAdapter
import java.util.*

class ActivityDrillingMud : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drilling_mud)

        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        title = "Drilling Mud"

        viewPager = findViewById(R.id.pager)
        tab = findViewById(R.id.tab_layout)

        val action = intent.action ?: ""

        viewPager.adapter = DrillingMudTabAdapter(supportFragmentManager, action)
        tab.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}