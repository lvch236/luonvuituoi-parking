package com.example.luonvuituoi.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.example.luonvuituoi.R
import com.example.luonvuituoi.adapter.BookAdapter
import com.example.luonvuituoi.databinding.FragmentBookSlotBinding
import com.example.luonvuituoi.item.BookItem
import java.text.FieldPosition


class BookSlotFragment : Fragment() ,AdapterView.OnItemClickListener{
    private var arrayList:ArrayList<BookItem>? =null
    private var gridView:GridView?=null
    private var bookAdapter:BookAdapter?=null
    lateinit var binding: FragmentBookSlotBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookSlotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridView = binding.gridView
        arrayList = ArrayList()
        arrayList = setDataList()

        bookAdapter = context?.let { BookAdapter(it, arrayList!!) }
        gridView?.adapter = bookAdapter
        gridView!!.onItemClickListener = this
        binding.pay.setOnClickListener {
            Toast.makeText(context,"Please choose parking before payment!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun setDataList():ArrayList<BookItem>
    {
        var arrayList:ArrayList<BookItem> = ArrayList()
        arrayList.add(BookItem(R.drawable.car,"P101",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"P102",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"P103",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P104",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"P105",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P106",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"P107",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"P108",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P109",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P110",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P111",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"P112",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P113",12,"",false))
        arrayList.add(BookItem(R.drawable.car,"P114",12,"",false))
        return arrayList
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

    //    imageView.setImageResource(R.drawable.vanhanh)
        var items :BookItem = arrayList!!.get(position)
        if (items.available==true)
        {
            for (index in 0 until p0!!.count){
                p0[index].apply {
                    if (index != position){
                        // background color for not selected items
                        //findViewById<ImageView>(R.id.car)!!.setBackgroundColor(Color.parseColor("#E60026"))
                        var listItem: BookItem = arrayList!!.get(index)
                        findViewById<ImageView>(R.id.car)!!.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        if (listItem.available==true)
                        {
                            Log.e("test",listItem.available.toString())
                            findViewById<ImageView>(R.id.car)!!.setImageResource(R.drawable.bg_item)
                        }
                    }else{
                        // background color for selected item
                        findViewById<ImageView>(R.id.car)!!.setBackgroundColor(Color.parseColor("#FFDB00"))
                        findViewById<ImageView>(R.id.car)!!.setImageResource(R.drawable.car)
                    }
                }
            }
//            p1?.findViewById<ImageView>(R.id.car)!!.setBackgroundColor(Color.parseColor("#FFDB00"))
//            p1?.findViewById<ImageView>(R.id.car)!!.setImageResource(R.drawable.car)
           // car.setBackgroundColor(Color.parseColor("#E60026"))
            Toast.makeText(context,items.id,Toast.LENGTH_SHORT).show()
         //   Log.e("123","123")
        }
        else
        {
            Toast.makeText(context,"InValid",Toast.LENGTH_SHORT).show()
        }
        binding.pay.setOnClickListener {
            findNavController().navigate(R.id.action_bookSlotFragment_to_paymentFragment)
//            Toast.makeText(context,"Payment",Toast.LENGTH_SHORT).show()
        }

    }

}