package com.ngedev.tallyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngedev.tallyapp.R
import org.json.JSONArray
import org.json.JSONException

class DrillingMudProductAdapter(private var mArrayData: JSONArray) : RecyclerView.Adapter<DrillingMudProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.row_list_conversion, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvOne = holder.itemView.findViewById<TextView>(R.id.tv_one)
        val tvTwo = holder.itemView.findViewById<TextView>(R.id.tv_two)
        val tvThree = holder.itemView.findViewById<TextView>(R.id.tv_three)

        try {
            val data = mArrayData.getJSONArray(position)
            val multiply = data.getString(0)
            val by = data.getString(1)
            val obtain = data.getString(2)
            tvOne.text = multiply
            tvTwo.text = by
            tvThree.text = obtain
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int = mArrayData.length()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}