package com.example.luonvuituoi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.luonvuituoi.R
import com.example.luonvuituoi.databinding.FragmentDetailBinding
import com.example.luonvuituoi.databinding.FragmentPaymentBinding
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    var Paymentfees:String? =null;
    var nameMall:String? =null;
    var Box:String? =null;
    var typePayment:String? =null;
    var typeHours:String? =null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        Log.e("test",bundle.toString())
        if (bundle != null) {
            nameMall = bundle.getString("nameMall", android.R.attr.defaultValue.toString())
            Box = bundle.getString("Box", android.R.attr.defaultValue.toString())
            Paymentfees = bundle.getString("Paymentfees", android.R.attr.defaultValue.toString())
            typePayment = bundle.getString("typePayment", android.R.attr.defaultValue.toString())
            typeHours = bundle.getString("Hours", android.R.attr.defaultValue.toString())
          //  Log.e("conghau", nameMall+Box)
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        binding.btnHome.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_locationFragment)
        }
        binding.tvMall.text = nameMall
        binding.tvBox.text = "Box Parking: "+Box
        if(typeHours.equals("1")) binding.tvTimeLeft.text = "Time left: "+"1 - 3 hours"
        if(typeHours.equals("2")) binding.tvTimeLeft.text = "Time left: "+"3 - 4 hours"
        if(typeHours.equals("3")) binding.tvTimeLeft.text = "Time left: "+"5 - 7 hours"
        if(typeHours.equals("4")) binding.tvTimeLeft.text = "Time left: "+"7+ hours"
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        binding.tvTimeBook.text = "Time Book: "+currentDate
        binding.tvPaymentFee.text = "Payment Fee: "+Paymentfees
        binding.tvPaymentType.text = "Payment Type: "+typePayment
        }
}