package com.example.luonvuituoi.fragments

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
    }
    private fun setDataList():ArrayList<BookItem>
    {
        var arrayList:ArrayList<BookItem> = ArrayList()
        arrayList.add(BookItem(R.drawable.car,"1",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"2",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"3",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"4",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"5",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"6",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"7",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"8",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"5",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"6",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"7",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"8",12,"",true))
        arrayList.add(BookItem(R.drawable.vanhanh,"9",12,"",true))
        arrayList.add(BookItem(R.drawable.car,"10",12,"",true))
        return arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        var view:View = View.inflate(context, R.layout.item_car,null)
        var items :BookItem = arrayList!!.get(position)
        var car : ImageView = view.findViewById(R.id.car)
       car.setImageResource(R.drawable.vanhanh)
        bookAdapter!!.notifyDataSetChanged();
        Toast.makeText(context,items.id,Toast.LENGTH_SHORT).show()
        Log.e("123","123")
    }

}