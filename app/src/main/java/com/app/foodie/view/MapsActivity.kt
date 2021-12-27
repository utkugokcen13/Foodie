package com.app.foodie.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.app.foodie.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.foodie.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile ->{
                    val intent = Intent(this, CustomerProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_home ->{
                    val intent = Intent(this, CustomerMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_map ->{
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in Ankara and move the camera
        val ankara = LatLng(39.883061, 32.756493)
        mMap.addMarker(MarkerOptions().position(ankara).title("Marker in Ankara"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ankara))
        mMap.setOnMarkerClickListener {
            val view = layoutInflater.inflate(R.layout.business_popup, null)
            val popupWindow = PopupWindow(view)

            popupWindow.contentView = view
            popupWindow.showAsDropDown(bottom_navigation)


            true
        }



    }
}