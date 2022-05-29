package com.example.luonvuituoi.fragments

import android.R.attr.defaultValue
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.luonvuituoi.R
import com.example.luonvuituoi.adapter.BookAdapter
import com.example.luonvuituoi.databinding.FragmentBookSlotBinding
import com.example.luonvuituoi.helper.KEY_USER_GOOGLE_ID
import com.example.luonvuituoi.helper.PreferenceHelper
import com.example.luonvuituoi.item.BookItem
import com.example.luonvuituoi.item.parking
import com.google.firebase.database.*


class BookSlotFragment : Fragment() ,AdapterView.OnItemClickListener{
    private var arrayList:ArrayList<BookItem>? =null
    private var gridView:GridView?=null
    var nameMall:String? =null;
    private lateinit var database : DatabaseReference
    private var bookAdapter:BookAdapter?=null
    lateinit var binding: FragmentBookSlotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        Log.e("test",bundle.toString())
        if (bundle != null) {
            nameMall = bundle.getString("nameMall", defaultValue.toString())
            Log.e("conghau", nameMall.toString())
        }
    }

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
        setDataList()

        bookAdapter = context?.let { BookAdapter(it, arrayList!!) }
        gridView?.adapter = bookAdapter
        gridView!!.onItemClickListener = this

        binding.pay.setOnClickListener {
            Toast.makeText(context,"Please choose parking before payment!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun setDataList()
    {
        var path = "parking/F_1/"
        database = FirebaseDatabase.getInstance().getReference(path)
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList!!.clear()
                var i : Int = 0
                for (postSnapshot in snapshot.children) {
                    i = i+1
                    val post = postSnapshot.getValue(parking::class.java)
                    Log.e("123", post!!.available!!)
                    arrayList!!.add(BookItem(R.drawable.car,"P_10"+i,post.time,post.user_id,post.available))
                }
                bookAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Get Faild!",Toast.LENGTH_SHORT).show()
            }

        })
      //  var arrayList:ArrayList<BookItem> = ArrayList()
//        arrayList!!.add(BookItem(R.drawable.car,"P_101","12","","true"))
//        arrayList!!.add(BookItem(R.drawable.car,"P_102","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_103","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_104","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_105","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_106","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_107","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_108","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_109","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_110","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_111","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_112","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_113","12","","true"))
//        arrayList.add(BookItem(R.drawable.car,"P_114","12","","true"))
//       // return arrayList
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

    //    imageView.setImageResource(R.drawable.vanhanh)
        var items :BookItem = arrayList!!.get(position)
        if (items.available.equals("true"))
        {
           // Log.e("conga",p0!!.count.toString())
            for (index in 0 until p0!!.count){
                p0[index].apply {
                    if (index != position){
                        // background color for not selected items
                        //findViewById<ImageView>(R.id.car)!!.setBackgroundColor(Color.parseColor("#E60026"))
                        var listItem: BookItem = arrayList!!.get(index)
                        findViewById<ImageView>(R.id.car)!!.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        if (listItem.available.equals("true"))
                        {
                           // Log.e("test",listItem.available.toString())
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

            val fragment = Fragment()
            val bundle = Bundle()
            bundle.putString("nameMall", nameMall)
            bundle.putString("Box",items.id)
            fragment.arguments = bundle
            Log.e("test",bundle.toString())
            findNavController().navigate(R.id.action_bookSlotFragment_to_paymentFragment,bundle)
//            Toast.makeText(context,"Payment",Toast.LENGTH_SHORT).show()
        }

    }

}