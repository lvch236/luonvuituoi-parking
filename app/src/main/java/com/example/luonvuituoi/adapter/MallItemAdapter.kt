package com.example.luonvuituoi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luonvuituoi.OnItemClickListener
import com.example.luonvuituoi.R
import com.example.luonvuituoi.item.MallItem
import com.example.luonvuituoi.viewsHolders.MallItemViewHolder

class MallItemAdapter(private val list: List<MallItem>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MallItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MallItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mall_item_layout, parent, false)
        return MallItemViewHolder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: MallItemViewHolder, position: Int) {
        val mallItem = list[position]
        holder.setData(mallItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}