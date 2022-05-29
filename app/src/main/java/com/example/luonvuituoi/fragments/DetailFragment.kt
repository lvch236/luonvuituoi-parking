package com.example.luonvuituoi.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.luonvuituoi.R
import com.example.luonvuituoi.databinding.FragmentDetailBinding
import com.example.luonvuituoi.helper.KEY_USER_GOOGLE_ID
import com.example.luonvuituoi.helper.PreferenceHelper
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    var Paymentfees:String? =null;
    var nameMall:String? =null;
    var Box:String? =null;
    var typePayment:String? =null;
    var typeHours:String? =null;
    //private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "u5"
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


//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            for (profile in user.providerData) {
//                // Id of the provider (ex: google.com)
//                val providerId = profile.providerId
//
//                // UID specific to the provider
//                val uid = profile.uid
//
//                // Name, email address, and profile photo Url
//                val name = profile.displayName
//                val email = profile.email
//                val photoUrl: Uri? = profile.photoUrl
//                Log.e("conghau", email.toString())
//            }
//        }
//        else Log.e("cc","null")
//        //private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "u5"
//        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
//       // Toast.makeText(context, "" + currentFirebaseUser!!.uid, Toast.LENGTH_SHORT).show()
//        Log.e("conghau",currentuser)
//        val user = Firebase.auth.currentUser
//        user?.let {
//            for (profile in it.providerData) {
//                // Id of the provider (ex: google.com)
//                val providerId = profile.providerId
//
//                // UID specific to the provider
//                val uid = profile.uid
//
//                // Name, email address, and profile photo Url
//                val name = profile.displayName
//                val email = profile.email
//                val photoUrl = profile.photoUrl
//            }
//        }
        Log.e("conghau", PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID)!!)
        binding.tvMall.text = nameMall
        binding.tvBox.text = "Box Parking: "+Box
        binding.tvTimeLeft.text = "Time left: "+typeHours
//        if(typeHours.equals("1")) binding.tvTimeLeft.text = "Time left: "+"1 - 3 hours"
//        if(typeHours.equals("2")) binding.tvTimeLeft.text = "Time left: "+"3 - 4 hours"
//        if(typeHours.equals("3")) binding.tvTimeLeft.text = "Time left: "+"5 - 7 hours"
//        if(typeHours.equals("4")) binding.tvTimeLeft.text = "Time left: "+"7+ hours"
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        binding.tvTimeBook.text = "Time Book: "+currentDate
        binding.tvPaymentFee.text = "Payment Fee: "+Paymentfees
        binding.tvPaymentType.text = "Payment Type: "+typePayment
        }
}