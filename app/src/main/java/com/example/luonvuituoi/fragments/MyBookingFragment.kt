package com.example.luonvuituoi.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
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

        (adapter as MyBookingAdapter).setOnItemClickListener(object :MyBookingAdapter.onItemClickListener{
            override fun onItemClick(adapterView: View, position: Int) {
                var items :myBooking = arrayList!!.get(position)
                if (items.check.equals("false"))
                {
                    Toast.makeText(context,"Invalid",Toast.LENGTH_SHORT).show()
                } else
                {
                    var dialog = AlertDialog.Builder(context)
                        .setTitle("Left parking")
                        .setMessage("You have left the parking lot?")
                        .setPositiveButton("Yes"){_,_ ->
                          //  adapterView.findViewById<LinearLayout>(R.id.ln_itembooking).setBackgroundColor(Color.parseColor("#9CAACA"))
                            updateData(items.boxParking!!,"true","","")
                            updateDat1(items.id.toString(),
                                items.nameMall.toString(),
                                items.boxParking!!,
                                items.timeBook.toString(),
                                items.timeLeft.toString(),
                                items.paymentType.toString(), items.paymentFee.toString(),"false")
                        }
                        .setNegativeButton("No"){_,_ ->
                            Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show()
                        }.create()
                    dialog.show()
                    //Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                }

                Log.e("123", position.toString())
            }


        })
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
    private fun updateDat1(id: String,nameMall:String,boxParking:String,timeBook:String,timeLeft:String,paymentType:String,paymentFee:String,check:String) {
        var path = "users/"+PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID)!!+"/_myBooking/"
        database = FirebaseDatabase.getInstance().getReference(path)
        val mbooking = mapOf<String,String>(
            "nameMall" to nameMall,
            "boxParking" to boxParking,
            "timeBook" to timeBook,
            "timeLeft" to timeLeft,
            "paymentType" to paymentType,
            "paymentFee" to paymentFee,
            "check" to check,
            "id" to id,
        )

        database.child(id).updateChildren(mbooking).addOnSuccessListener {

            Toast.makeText(context,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(context,"Failed to Update",Toast.LENGTH_SHORT).show()

        }
    }
    private fun updateData(boxparking: String,available:String,time:String,user_id:String) {
        var path = "parking/F_1/"
        database = FirebaseDatabase.getInstance().getReference(path)
        val parking = mapOf<String,String>(
            "available" to available,
            "time" to time,
            "user_id" to user_id
        )

        database.child(boxparking).updateChildren(parking).addOnSuccessListener {

            Toast.makeText(context,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(context,"Failed to Update",Toast.LENGTH_SHORT).show()

        }}


}