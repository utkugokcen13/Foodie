package com.app.foodie.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.foodie.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.foodie.databinding.ActivityMapsBinding
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.awaitCancellation
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var database: DatabaseReference
    private lateinit var cartArrayList : ArrayList<Meal>
    private lateinit var businessArrayList : ArrayList<Business>
    var hashMap : HashMap<String, String> = HashMap<String, String> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setTitle("Map")
        }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent !=null)
        {
            val bundles:Bundle = intent.extras!!.get("bundle") as Bundle
            if(bundles!=null)
            {
                cartArrayList = bundles.getSerializable("cartarraylist") as ArrayList<Meal>

            }
        }
        else{
            cartArrayList = ArrayList<Meal>()
        }

        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val businessImage = intent.getStringExtra("businessimage")
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")
        businessArrayList = ArrayList<Business>()






        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, CustomerProfileActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_home ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, CustomerMainActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_map ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_cart ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, CartActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
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
        database = FirebaseDatabase.getInstance().getReference("Business")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (businessSnapShot in snapshot.children){
                        val business = businessSnapShot.getValue(Business::class.java)
                        businessArrayList.add(business!!)
                        val mark = LatLng(business!!.latitude, business!!.longitude)
                        val marker = googleMap.addMarker(MarkerOptions().position(mark).title(business!!.businessName).snippet(business!!.businessType))




                        //database.child(business!!.businessName).child("markerID").setValue(marker.id)
                        //val markerID = mapOf<String,String>("markerID" to marker.id)
                        //database.updateChildren(markerID)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        googleMap.setOnInfoWindowClickListener(this)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(39.88388882024731, 32.75714588040386),10f))
    }


    override fun onInfoWindowClick(p0: Marker?) {
        Toast.makeText(this,p0!!.id, Toast.LENGTH_SHORT).show()
    }




}