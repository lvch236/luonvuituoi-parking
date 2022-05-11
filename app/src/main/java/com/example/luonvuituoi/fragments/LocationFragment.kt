package com.example.luonvuituoi.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.luonvuituoi.OnItemClickListener
import com.example.luonvuituoi.R
import com.example.luonvuituoi.adapter.MallItemAdapter
import com.example.luonvuituoi.databinding.FragmentLocationBinding
import com.example.luonvuituoi.item.MallItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class LocationFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val FINE_LOCATION_RO = 101
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var mallItemAdapter: MallItemAdapter
    private var mallItemList = arrayListOf<MallItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        setHasOptionsMenu(true)
        activity?.actionBar?.hide()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        getLocation()
        buildData()

        mallItemAdapter = MallItemAdapter(mallItemList, this)
        binding.rvMallItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mallItemAdapter
        }



        return binding.root
    }

    private fun buildData() {
        mallItemList.add(MallItem(R.drawable.dongkhoi, "VINCOM Dong Khoi, District 1", "9 km away", 56))
        mallItemList.add(MallItem(R.drawable.scvivo, "SC VIVO, District 7", "15.5 km away", 107))
        mallItemList.add(MallItem(R.drawable.crescent, "CRESCENT MALL, District 7", "9.5 km away", 54))
        mallItemList.add(MallItem(R.drawable.takashima, "TAKASHIMAYA, District 1", "15 km away", 86))
        mallItemList.add(MallItem(R.drawable.megamall, "VINCOM MEGA MALL, District 2", "14 km away", 42))
        mallItemList.add(MallItem(R.drawable.pearl, "PEARL PLAZA, Binh Thanh District", "12 km away", 2))
        mallItemList.add(MallItem(R.drawable.vanhanh, "Van Hanh Mall, District 10", "7 km away", 77))
        mallItemList.add(MallItem(R.drawable.giga, "GIGA MALL, Thu Duc City", "3 km away", 99))
        mallItemList.add(MallItem(R.drawable.bitexco, "BITEXCO, District 1", "10 km away", 188))
    }

    private fun getLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            val location = it.result
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addressList = geocoder.getFromLocation(
                    location.latitude, location.longitude, 1
                )
                try {
                    Toast.makeText(context, addressList[0].locality.toString(), Toast.LENGTH_SHORT)
                        .show()
                } catch (e: Exception) {

                }

            }
        }
    }

    override fun onItemClicked(mallItem: MallItem) {
        //findNavController().navigate(LocationFragmentDirections.actionLocationFragmentToParkingBookingScreenFragment())
    }

    override fun onDirectionsClicked(mallItem: MallItem) {
//        val gmmIntentUri: Uri = Uri.parse("google.streetview:cbll=17.493328112421448, 78.3934185687929")
        val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(mallItem.name))

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (activity?.let { mapIntent.resolveActivity(it.packageManager) } != null) {
            startActivity(mapIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)

        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false;

            }

        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.search_menu, menu)
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_search -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}