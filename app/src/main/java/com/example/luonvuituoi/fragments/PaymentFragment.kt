package com.example.luonvuituoi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.luonvuituoi.R
import com.example.luonvuituoi.databinding.FragmentPaymentBinding
import com.example.luonvuituoi.helper.KEY_USER_GOOGLE_ID
import com.example.luonvuituoi.helper.PreferenceHelper
import com.example.luonvuituoi.item.myBooking
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding

    var Paymentfees:String? =null;
    var nameMall1:String? =null;
    var Box:String? =null;
    var typePayment:String? =null;
    var typeHours:String? =null;
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        Log.e("test",bundle.toString())
        if (bundle != null) {
            nameMall1 = bundle.getString("nameMall", android.R.attr.defaultValue.toString())
            Box = bundle.getString("Box", android.R.attr.defaultValue.toString())
            Log.e("conghau", nameMall1+Box)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_bookSlotFragment)
        }

        binding.apply {
            cv13hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = "100.000 VND"
                Paymentfees= "100.000 VND"
                typeHours = "1 - 3 hours"
                tvHiddenText.visibility = View.GONE
            }

            cv34Hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = "200.000 VND"
                Paymentfees= "200.000 VND"
                typeHours = "3 - 4 hours"
                tvHiddenText.visibility = View.GONE
            }
            cv57Hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = "300.000 VND"
                Paymentfees= "300.000 VND"
                typeHours = "5 - 7 hours"
                tvHiddenText.visibility = View.GONE
            }
            cv7Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                tvCost.text = "400.000 VND"
                Paymentfees= "400.000 VND"
                typeHours = "7+ hours"
                tvHiddenText.visibility = View.VISIBLE
            }
            visa.setOnClickListener {
                visa.setCardBackgroundColor(resources.getColor(R.color.phone_orange))
                momo.setCardBackgroundColor(resources.getColor(R.color.white))
                zalopay.setCardBackgroundColor(resources.getColor(R.color.white))
                typePayment = "Visa"
            }
            momo.setOnClickListener {
                momo.setCardBackgroundColor(resources.getColor(R.color.phone_orange))
                visa.setCardBackgroundColor(resources.getColor(R.color.white))
                zalopay.setCardBackgroundColor(resources.getColor(R.color.white))
                typePayment = "Momo"
            }
            zalopay.setOnClickListener {
                zalopay.setCardBackgroundColor(resources.getColor(R.color.phone_orange))
                visa.setCardBackgroundColor(resources.getColor(R.color.white))
                momo.setCardBackgroundColor(resources.getColor(R.color.white))
                typePayment = "Zalopay"
            }
        }

        binding.ibNext.setOnClickListener {
            val fragment = Fragment()
            val bundle = Bundle()
            bundle.putString("nameMall", nameMall1)
            bundle.putString("Box",Box)
            bundle.putString("typePayment",typePayment)
            bundle.putString("Paymentfees",Paymentfees)
            bundle.putString("Hours",typeHours)
            fragment.arguments = bundle
            Log.e("test",bundle.toString())
            findNavController().navigate(R.id.action_paymentFragment_to_detailFragment,bundle)
            var booking = myBooking()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
//            with(booking)
//            {
//                nameMall = nameMall1
//                boxParking = Box
//                timeBook = currentDate
//                timeLeft = typeHours
//                paymentType = typePayment
//                paymentFee = Paymentfees
//                check = "true"
//
//                var path = "users/"+PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID)!!+"/_myBooking"
//
//                Log.e("123",mGroupId)
//                databaseReference.child(path).push().setValue(booking)
//            }
//            var park = parking()
//            with(park)
//            {
//                available = false
//                time = currentDate +" @ "+ typeHours
//                user_id = PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID)!!
//                var firebaseDatabase = FirebaseDatabase.getInstance()
//                var databaseReference = firebaseDatabase.getReference()
//                var path = "parking/F_1/"+Box
//                databaseReference.child(path).push().setValue(park)
//            }
            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.getReference()
            val id: String = databaseReference.push().getKey()!!
            updateDat1(id, nameMall1.toString(), Box.toString(),currentDate,
                typeHours.toString(), typePayment.toString(), Paymentfees.toString(),"true")
            updateData(Box!!,"false",currentDate +" @ "+ typeHours,PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID)!!)

        }

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

        }
    }

}