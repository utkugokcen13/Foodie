package com.app.foodie.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.recycler_row.*
import java.util.*
import kotlin.collections.ArrayList


class CustomerMainActivity : AppCompatActivity(), MainRecyclerAdapter.OnItemClickListener {
    //private lateinit var binding : ActivityCustomerMainBinding

    private lateinit var mainAdapter : MainRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var businessArrayList : ArrayList<Business>
    private lateinit var tempArrayList : ArrayList<Business>
    private lateinit var cartArrayList : ArrayList<Meal>
    private lateinit var name : String
    private lateinit var surname : String
    private lateinit var email : String
    private lateinit var phoneNumber : String
    private lateinit var currentUser : FirebaseUser
    private lateinit var currentUserUid : String
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010



    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_main)


        var actionBar = supportActionBar
        currentUser = FirebaseAuth.getInstance().currentUser!!

        if(actionBar != null){
            actionBar.setTitle("Foodie")
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        RequestPermission()

        getLastLocation()




        businessArrayList = ArrayList<Business>()
        tempArrayList = ArrayList<Business>()
        getBusinessData()
        mainAdapter = MainRecyclerAdapter(tempArrayList,this,this)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)



        val mSearchView = findViewById<SearchView>(R.id.searchView)
        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val businessImage = intent.getStringExtra("businessimage")
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")
        val previousActivity = intent.getIntExtra("checkprevious",0)


        mSearchView.setQueryHint("Search a Business")

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tempArrayList.clear()
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    businessArrayList.forEach{
                        if(it.businessName.toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    recyclerView1.adapter!!.notifyDataSetChanged()
                }else{
                    tempArrayList.clear()
                    tempArrayList.addAll(businessArrayList)
                    recyclerView1.adapter!!.notifyDataSetChanged()
                }
                return false

            }

        })

        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()


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
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("email", email)
                    intent.putExtra("phonenumber", phoneNumber)
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
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("email", email)
                    intent.putExtra("phonenumber", phoneNumber)
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
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("email", email)
                    intent.putExtra("phonenumber", phoneNumber)
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
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("email", email)
                    intent.putExtra("phonenumber", phoneNumber)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_order ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, OrdersActivity::class.java)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("email", email)
                    intent.putExtra("phonenumber", phoneNumber)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }

            true
        }



    }
    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
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
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location:Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        //Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        //Log.d("tag","You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude)
                        latitude = location.latitude
                        longitude = location.longitude
                        Log.d("TAG" ,"Your Location: "+ location.latitude + " " + location.longitude)
                        database = FirebaseDatabase.getInstance().getReference("Users")

                        database.child(currentUser.uid).child("latitude").setValue(latitude)
                        database.child(currentUser.uid).child("longitude").setValue(longitude)
                    }
                }
            }else{
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
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
        Looper.myLooper()?.let {
            fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,locationCallback, it
            )
        }
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            Log.d("Tag","You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude)
        }
    }

    private fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You have the Permission")
            }
        }
    }



    override fun onItemClick(position: Int) {
        val clickedItem = businessArrayList[position]
        //Toast.makeText(this, clickedItem.businessID.toString(), Toast.LENGTH_LONG).show()
        mainAdapter.notifyItemChanged(position)
        var bundle = Bundle()
        bundle!!.putSerializable("cartarraylist",cartArrayList)
        val intent = Intent(this, DetailedBusinessActivity::class.java)
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)
        intent.putExtra("businessname", clickedItem.businessName)
        intent.putExtra("businessaddress", clickedItem.businessAddress)
        intent.putExtra("businessimage", clickedItem.ImageUrl)
        intent.putExtra("name", name)
        intent.putExtra("surname", surname)
        intent.putExtra("email", email)
        intent.putExtra("phonenumber", phoneNumber)
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
                tempArrayList.addAll(businessArrayList)
            }
        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
}

}