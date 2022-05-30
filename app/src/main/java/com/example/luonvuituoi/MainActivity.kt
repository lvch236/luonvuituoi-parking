package com.example.luonvuituoi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.luonvuituoi.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityMainBinding
    var uidSignIn:String? =null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val hView = navigationView.getHeaderView(0)

        val navUser = hView.findViewById<TextView>(R.id.tv_user_name)

        val navUserId = hView.findViewById<TextView>(R.id.tv_user_email_id)

        val navProfile = hView.findViewById<CircleImageView>(R.id.ivProfile)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController

        navigationView.itemIconTintList = null

        NavigationUI.setupActionBarWithNavController(this, navController, drawer)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this@MainActivity)

        if (intent != null && intent.extras != null) {
            val userName: String = intent.getStringExtra("UserName").toString()
            navUser.text = userName

            val userEmail: String = intent.getStringExtra("UserEmail").toString()
            navUserId.text = userEmail

            val userPhoto = intent.getStringExtra("UserPhoto")
            if (userPhoto != null) {
                Glide.with(navProfile).load(userPhoto).into(navProfile)
                Log.e("ava",userPhoto)
            }
            else Glide.with(navProfile).load("https://dvdn247.net/wp-content/uploads/2020/07/avatar-mac-dinh-1.png").into(navProfile)
            Log.e("email",userEmail)
//            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "u5"
//            Log.e("conghau",currentUserUid)
//            uidSignIn = intent.getStringExtra("uid")? :""
        }
//        mAuth = FirebaseAuth.getInstance()
//        val currenUser = mAuth.updateCurrentUser()
//        //mAuth.uid
//       // Log.e("147", currenUser!!.uid)
//        if (currenUser == null) Log.e("123","333")
//        if (mAuth == null) Log.e("123","456")
//        Log.e("123", mAuth.uid!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(
                this,
                R.id.fragmentContainerView2
            ), drawer
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> return if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
                true
            } else false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_hotnews -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.hotNewsFragment, true).build()
                Navigation.findNavController(this, R.id.fragmentContainerView2)
                    .navigate(R.id.hotNewsFragment, null, navOptions)

            }

            R.id.nav_home -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.locationFragment, true).build()
                Navigation.findNavController(this, R.id.fragmentContainerView2)
                    .navigate(R.id.locationFragment, null, navOptions)
            }
            R.id.nav_my_bookings -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.myBookingFragment, true).build()
                Navigation.findNavController(this, R.id.fragmentContainerView2)
                    .navigate(R.id.myBookingFragment, null, navOptions)
            }
            R.id.nav_support -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.supportFragment, true).build()
                Navigation.findNavController(this, R.id.fragmentContainerView2)
                    .navigate(R.id.supportFragment, null, navOptions)

            }
            R.id.nav_logout -> {
                mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(this, R.id.fragmentContainerView2)
            .currentDestination?.id
    }


    fun hideToolBar() {

        binding.myToolbar.visibility = View.GONE

    }

    fun showToolBar() {
        binding.myToolbar.visibility = View.VISIBLE

    }

}