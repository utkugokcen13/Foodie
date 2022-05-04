package com.app.foodie.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.databinding.ActivityCustomerMainBinding
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.recycler_row.*


class CustomerMainActivity : AppCompatActivity(), MainRecyclerAdapter.OnItemClickListener {
    //private lateinit var binding : ActivityCustomerMainBinding

    private lateinit var mainAdapter : MainRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var businessArrayList : ArrayList<Business>
    private lateinit var cartArrayList : ArrayList<Meal>

    var currentLocation : Location? = null
    val REQUEST_CODE : Int = 101

    val callback = object : LocationCallback(){
        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }

        override fun onLocationResult(result: LocationResult) {
            val lastLocation = result.lastLocation

            Log.d("TAG", lastLocation.latitude.toString() + " " + lastLocation.longitude.toString())

            super.onLocationResult(result)
        }
    }


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    //private lateinit var recyclerView: RecyclerView
    companion object{
        private const val REQUEST_PERMISSION_REQUEST_CODE = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_main)
        var actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setTitle("Foodie")
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        businessArrayList = ArrayList<Business>()
        getBusinessData()
        mainAdapter = MainRecyclerAdapter(businessArrayList,this,this)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)



        val mSearchView = findViewById<SearchView>(R.id.searchView)
        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val businessImage = intent.getStringExtra("businessimage")
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")
        val previousActivity = intent.getIntExtra("checkprevious",0)


        mSearchView.setQueryHint("Search a Business");

        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val email = intent.getStringExtra("email")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        if(previousActivity != 1){
            if(intent !=null)
            {
                val bundles:Bundle = intent.extras!!.get("bundle") as Bundle
                if(bundles!=null)
                {
                    cartArrayList = bundles.getSerializable("cartarraylist") as ArrayList<Meal>

                }
            }
        }else{
            cartArrayList = ArrayList<Meal>()
        }

        // BOTTOM NAV BAR

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

        onGPS()
        findViewById<ImageView>(R.id.filter).setOnClickListener {
            requestLocation()

        }


    }

    private fun fetchLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 200)
                return
            }else{
                requestLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val requestLocation = LocationRequest()
        requestLocation.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        requestLocation.interval = 0
        requestLocation.fastestInterval = 0
        requestLocation.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(
            requestLocation,callback, Looper.myLooper()!!
        )
    }

    fun onGPS(){
        if(!isLocationEnabled()){
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }else{
            fetchLocation()
        }
    }

    private fun isLocationEnabled():Boolean{
        val locationManager  = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    /*private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_REQUEST_CODE)
    }*/

    /*private fun checkPermissions() : Boolean {
        //check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),1)
                return true
        }
            //getCurrentLocation()
        return false

    }*/

    /*private fun getCurrentLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task ->
                    val location: Location? = task.result
                    if(location ==null){
                        Toast.makeText(this,"Null received",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Get success",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Turn on location",Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            requestPermission()
        }


    }*/

/*override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if(requestCode == REQUEST_PERMISSION_REQUEST_CODE){
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,"Granted",Toast.LENGTH_SHORT).show()
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext,"Denied",Toast.LENGTH_SHORT).show()
        }
    }
}*/


    override fun onItemClick(position: Int) {
        val clickedItem = businessArrayList[position]
        //Toast.makeText(this, clickedItem.businessID.toString(), Toast.LENGTH_LONG).show()
        mainAdapter.notifyItemChanged(position)
        var bundle = Bundle()
        bundle!!.putSerializable("cartarraylist",cartArrayList)
        val intent = Intent(this, DetailedBusinessActivity::class.java)
        intent.putExtra("businessname", clickedItem.businessName)
        intent.putExtra("businessaddress", clickedItem.businessAddress)
        intent.putExtra("businessimage", clickedItem.ImageUrl)
        intent.putExtra("bundle",bundle)
        //intent.putExtra("pickuptimerange", clickedItem.)
        startActivity(intent)
        finish()
    }


    private fun getBusinessData(){
        database = FirebaseDatabase.getInstance().getReference("Business")
        database.addValueEventListener(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                for (businessSnapShot in snapshot.children){
                    val business = businessSnapShot.getValue(Business::class.java)
                    businessArrayList.add(business!!)
                }
                recyclerView1.adapter = mainAdapter
            }
        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
}

}