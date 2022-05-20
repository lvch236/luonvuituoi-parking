package com.example.luonvuituoi.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.luonvuituoi.R

class HotFeedAdapter(var dataSet: List<String>, var key: String) : ListAdapter<String, HotFeedAdapter.HotFeedViewHolder>(HotFeedDiffUtil()) {
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class HotFeedDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    class HotFeedViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bindData(word: String, keyword: String) {
            Log.e("12345", "$word $keyword")
            if (word == keyword) {
                itemView.findViewById<TextView>(R.id.keyword).setBackgroundResource(R.drawable.book_bg)
                itemView.findViewById<TextView>(R.id.keyword).setTextColor(Color.WHITE)
            }
            itemView.findViewById<TextView>(R.id.keyword).text = word
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotFeedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hot_feed_item_layout, parent, false)
        return HotFeedViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: HotFeedViewHolder, position: Int) {
        val hotFeed = dataSet[position]
        val keyword = key
        holder.bindData(hotFeed, keyword)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}