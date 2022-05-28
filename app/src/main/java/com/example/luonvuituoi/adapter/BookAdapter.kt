package com.example.luonvuituoi.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.luonvuituoi.R
import com.example.luonvuituoi.fragments.BookSlotFragment
import com.example.luonvuituoi.item.BookItem
import java.text.FieldPosition

class BookAdapter(var context: Context, var arrayList: ArrayList<BookItem>): BaseAdapter() {


    override fun getItem(position: Int): Any {
       return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
       return arrayList.size
    }
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
       var view:View = View.inflate(context, R.layout.item_car,null)
        var car :ImageView = view.findViewById(R.id.car)
        var listItem: BookItem = arrayList.get(position)
        if (listItem.available == false) {
            with(car) {
                listItem.car?.let { setImageResource(it) }
            }
        }
       // car.setImageResource(R.drawable.car)
        return view
    }
}

