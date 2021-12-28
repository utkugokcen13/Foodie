package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.foodie.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.foodie.databinding.ActivityMapsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

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

        // Add a marker in Sydney and move the camera
        val mark1 = LatLng(39.882874, 32.754991)
        mMap.addMarker(MarkerOptions().position(mark1).title("restaurant1"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mark1))

        val mark2 = LatLng(39.909064, 32.770711)
        mMap.addMarker(MarkerOptions().position(mark2).title("restaurant2"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mark2))

        val mark3 = LatLng(39.877649, 32.732449)
        mMap.addMarker(MarkerOptions().position(mark3).title("restaurant3"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mark3))
    }
}