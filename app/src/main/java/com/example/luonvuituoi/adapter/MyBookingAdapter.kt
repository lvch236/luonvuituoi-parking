package com.example.luonvuituoi.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.luonvuituoi.R
import com.example.luonvuituoi.item.BookItem
import com.example.luonvuituoi.item.HotNewItem
import com.example.luonvuituoi.item.myBooking
import com.example.luonvuituoi.item.myBookingItem

class MyBookingAdapter(var arrayList: ArrayList<myBooking>):RecyclerView.Adapter<MyBookingAdapter.MyBookingVH>()
{
   // private var arrayList:ArrayList<myBooking>? =null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyBookingAdapter.MyBookingVH {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking,parent,false)
        return MyBookingVH(view)
    }

    override fun onBindViewHolder(holder: MyBookingAdapter.MyBookingVH, position: Int) {
        var items :myBooking = arrayList!!.get(position)
        holder.itemTextView1.text = items.nameMall
        holder.itemTextView2.text = items.timeBook
        holder.itemTextView3.text = items.timeLeft
    }

    override fun getItemCount(): Int {
      return arrayList!!.size
    }
    inner class MyBookingVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTextView1:TextView
        var itemTextView2:TextView
        var itemTextView3:TextView
        init {
            itemTextView1 = itemView.findViewById(R.id.tv_nameMall)
            itemTextView2 = itemView.findViewById(R.id.tv_timeBooking)
            itemTextView3 = itemView.findViewById(R.id.tv_fee)
        }
    }


}