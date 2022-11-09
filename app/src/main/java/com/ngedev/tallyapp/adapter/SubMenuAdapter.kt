package com.ngedev.tallyapp.adapter

import com.ngedev.tallyapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ngedev.tallyapp.model.SubMenuModel

class SubMenuAdapter(private val datas: List<SubMenuModel>, val onClick: (SubMenuModel) -> Unit) : RecyclerView.Adapter<SubMenuAdapter.MyViewHolder>() {

  class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_submenu, parent, false))
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.itemView.findViewById<Button>(R.id.btnAction).apply {
      text = datas[position].title
      setOnClickListener {
        onClick(datas[position])
      }
    }

  }

  override fun getItemCount(): Int = datas.size
}
