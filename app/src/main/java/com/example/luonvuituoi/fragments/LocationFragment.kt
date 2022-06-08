package com.example.luonvuituoi.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.example.luonvuituoi.helper.DataStore
import com.example.luonvuituoi.item.MallItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class LocationFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val FINE_LOCATION_RO = 101
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var filterList: ArrayList<MallItem> = ArrayList<MallItem>()
    lateinit var mallItemAdapter: MallItemAdapter

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
        val data = DataStore.getDataSet()
        mallItemAdapter = MallItemAdapter(data, this)
        binding.rvMallItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mallItemAdapter
        }
        return binding.root
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
      // val data = ArgsLocationToBookSlot(mallItem.name,mallItem.picture)
        val fragment = Fragment()
        val bundle = Bundle()
        bundle.putString("nameMall", mallItem.name)
        fragment.arguments = bundle
        Log.e("test",bundle.toString())
        findNavController().navigate(R.id.action_locationFragment_to_bookSlotFragment,bundle)


    }

    override fun onDirectionsClicked(mallItem: MallItem) {
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
    private fun filter(s: String) {
        val data = DataStore.getDataSet()
        filterList.clear()
        for (MallItem in data) {
            if (MallItem.name.toLowerCase().contains(s.lowercase(Locale.getDefault()))) {
                filterList.add(MallItem)
                Log.e("123",MallItem.name)
            }
        }
        mallItemAdapter=MallItemAdapter(filterList, this@LocationFragment)
        mallItemAdapter.notifyDataSetChanged()

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)

        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                val data = DataStore.getDataSet()
                if (query.toString().isEmpty())
                {
                    mallItemAdapter=MallItemAdapter(data, this@LocationFragment)
                    mallItemAdapter.notifyDataSetChanged()
                }
                else
                {
                    filterList.clear()
                    for (MallItem in data) {
                        if (MallItem.name.toLowerCase().contains(query!!.lowercase(Locale.getDefault()))) {
                            filterList.add(MallItem)
                            Log.e("123",MallItem.name)
                        }
                    }
                    mallItemAdapter=MallItemAdapter(filterList, this@LocationFragment)
                    mallItemAdapter.notifyDataSetChanged()
                }
                Log.e("123", query.toString())
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