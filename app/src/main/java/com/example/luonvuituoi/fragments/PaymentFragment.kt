package com.example.luonvuituoi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.luonvuituoi.R
import com.example.luonvuituoi.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding

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
            Log.e("conghau", nameMall+Box)
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
                typeHours = "1"
                tvHiddenText.visibility = View.GONE
            }

            cv34Hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = "200.000 VND"
                Paymentfees= "200.000 VND"
                typeHours = "2"
                tvHiddenText.visibility = View.GONE
            }
            cv57Hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = "300.000 VND"
                Paymentfees= "300.000 VND"
                typeHours = "3"
                tvHiddenText.visibility = View.GONE
            }
            cv7Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                tvCost.text = "400.000 VND"
                Paymentfees= "400.000 VND"
                typeHours = "4"
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
            bundle.putString("nameMall", nameMall)
            bundle.putString("Box",Box)
            bundle.putString("typePayment",typePayment)
            bundle.putString("Paymentfees",Paymentfees)
            bundle.putString("Hours",typeHours)
            fragment.arguments = bundle
            Log.e("test",bundle.toString())
            findNavController().navigate(R.id.action_paymentFragment_to_detailFragment,bundle)
        }

    }

}