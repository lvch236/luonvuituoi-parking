package com.example.luonvuituoi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.luonvuituoi.R
import com.example.luonvuituoi.item.HotNewItem


class HotNewAdapter : ListAdapter<HotNewItem, HotNewAdapter.HotNewVH>(MovieDiffUtilCallback()) {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<HotNewItem>() {
        override fun areItemsTheSame(oldItem: HotNewItem, newItem: HotNewItem): Boolean {
            return oldItem.source.id == newItem.source.id
        }

        override fun areContentsTheSame(oldItem: HotNewItem, newItem: HotNewItem): Boolean {
            return oldItem == newItem
        }
    }

    class HotNewVH (itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bindData(item: HotNewItem) {
            var textView = itemView.findViewById<TextView>(R.id.textView)
            var textView2 = itemView.findViewById<TextView>(R.id.textView2)
            var textView3 = itemView.findViewById<TextView>(R.id.textView3)
            var imageView = itemView.findViewById<ImageView>(R.id.imageView)
            textView.text = item.title.trim()
            textView2.text = item.description.trim()
            textView3.text = item.publishedAt.substring(0,10).trim()
            Glide.with(itemView.context).load(item.urlToImage.trim())
                .into(imageView)
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotNewVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hot_new_item_layout, parent, false)
        return HotNewVH(itemView, mListener)
    }

    override fun onBindViewHolder(holder: HotNewVH, position: Int) {
        val hotNew = getItem(position)
        holder.bindData(hotNew)
    }
}