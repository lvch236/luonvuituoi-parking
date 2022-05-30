package com.example.luonvuituoi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luonvuituoi.R
import com.example.luonvuituoi.adapter.BookAdapter
import com.example.luonvuituoi.adapter.MyBookingAdapter
import com.example.luonvuituoi.databinding.FragmentBookSlotBinding
import com.example.luonvuituoi.databinding.FragmentMyBookingBinding
import com.example.luonvuituoi.helper.KEY_USER_GOOGLE_ID
import com.example.luonvuituoi.helper.PreferenceHelper
import com.example.luonvuituoi.item.BookItem
import com.example.luonvuituoi.item.myBooking
import com.example.luonvuituoi.item.parking
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MyBookingFragment : Fragment() {
    private lateinit var database : DatabaseReference
    private var arrayList:ArrayList<myBooking>? =null
    lateinit var binding: FragmentMyBookingBinding
    private var adapter: RecyclerView.Adapter<MyBookingAdapter.MyBookingVH>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayList = ArrayList()
        setDataList()
        adapter = MyBookingAdapter(arrayList!!)
        val lm = LinearLayoutManager(context)
        binding.rvMybooking.layoutManager = lm
        binding.rvMybooking.adapter = adapter
    }

    private fun setDataList(){
        var path = "users/"+ PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID)!!+"/_myBooking"
        database = FirebaseDatabase.getInstance().getReference(path)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList!!.clear()
                var i : Int = 0
                for (postSnapshot in snapshot.children) {
                    i = i+1
                    val post = postSnapshot.getValue(myBooking::class.java)
                    Log.e("123", post!!.boxParking!!)
                    arrayList!!.add(post)
                }
                Collections.reverse(arrayList);
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Get Faild!",Toast.LENGTH_SHORT).show()
            }

        })
//        arrayList.add(myBooking("d","d","d","d","d","d"))
//        arrayList.add(myBooking("d","d","d","d","d","d"))
//        arrayList.add(myBooking("d","d","d","d","d","d"))
//        arrayList.add(myBooking("d","d","d","d","d","d"))
    }


}