package com.example.luonvuituoi.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luonvuituoi.R
import com.example.luonvuituoi.item.myBooking

class MyBookingAdapter(var arrayList: ArrayList<myBooking>):RecyclerView.Adapter<MyBookingAdapter.MyBookingVH>()
{
   // private var arrayList:ArrayList<myBooking>? =null
   private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(adapterView: View, position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyBookingAdapter.MyBookingVH {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking,parent,false)
        return MyBookingVH(view, mListener)
    }

    override fun onBindViewHolder(holder: MyBookingAdapter.MyBookingVH, position: Int) {
        var items :myBooking = arrayList!!.get(position)
        holder.itemTextView1.text = items.nameMall
        holder.itemTextView2.text = items.boxParking +" "+ items.timeBook
        holder.itemTextView3.text = items.timeLeft
        if (items.check.equals("false"))
            holder.lnbooking.setBackgroundColor(Color.parseColor("#9CAACA"))
        else holder.lnbooking.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    override fun getItemCount(): Int {
      return arrayList!!.size
    }
    inner class MyBookingVH(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        var itemTextView1:TextView
        var itemTextView2:TextView
        var itemTextView3:TextView
        var lnbooking : LinearLayout
        init {
            lnbooking = itemView.findViewById(R.id.ln_itembooking)
            itemTextView1 = itemView.findViewById(R.id.tv_nameMall)
            itemTextView2 = itemView.findViewById(R.id.tv_timeBooking)
            itemTextView3 = itemView.findViewById(R.id.tv_fee)
            itemView.setOnClickListener {
                listener.onItemClick(itemView,adapterPosition)
            }
        }
    }


}