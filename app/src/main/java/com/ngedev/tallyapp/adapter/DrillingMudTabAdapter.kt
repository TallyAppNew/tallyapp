package com.ngedev.tallyapp.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ngedev.tallyapp.fragment.DrillingMudFragment
import com.ngedev.tallyapp.fragment.DrillingMudProductFragment
import com.ngedev.tallyapp.fragment.DrillingMudReportFragment


class DrillingMudTabAdapter(fm: FragmentManager, private val action: String) : FragmentPagerAdapter(fm) {

    private val pages = listOf(
        DrillingMudFragment(),
        DrillingMudProductFragment(),
//        DrillingMudReportFragment()
    )

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        val args = Bundle()
        args.putString("", action)

        when(position) {
            0 -> {
                fragment = pages[0]
                fragment.arguments = args
            }
//            1 -> fragment = pages[1]
            else -> fragment = pages[1]
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Drilling Mud"
            else -> "Drilling Mud Product"
//            else -> "Drilling Mud Report"
        }
    }
}