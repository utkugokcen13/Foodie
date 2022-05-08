package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.CartRecyclerAdapter
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.adapter.PastRecyclerAdapter
import com.app.foodie.adapter.PendingRecyclerAdapter
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.app.foodie.models.PendingOrder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity(), PastRecyclerAdapter.OnItemClickListener {
    private lateinit var pendingArrayList : ArrayList<PendingOrder>
    private lateinit var pastArrayList : ArrayList<PendingOrder>
    private var totalAmount : Double = 0.0
    private var earning : Double = 0.0

    private lateinit var pendingAdapter : PendingRecyclerAdapter
    private lateinit var pastAdapter : PastRecyclerAdapter

    private lateinit var database: DatabaseReference
    private lateinit var currentUser : FirebaseUser

    private lateinit var businessName : String
    private lateinit var businessImage : String
    private lateinit var businessAddress : String

    private lateinit var name : String
    private lateinit var surname : String
    private lateinit var email : String
    private lateinit var phoneNumber : String

    private lateinit var cartArrayList : ArrayList<Meal>

    private var totalSavedMoney : Double = 0.0
    private var totalSavedFood : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        currentUser = FirebaseAuth.getInstance().currentUser!!

        var actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setTitle("Orders")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        pendingArrayList = ArrayList<PendingOrder>()
        pastArrayList = ArrayList<PendingOrder>()
        //getPendingData()
        pastAdapter = PastRecyclerAdapter(pastArrayList,this)
        past_recyclerView.layoutManager = LinearLayoutManager(this)
        past_recyclerView.setHasFixedSize(true)
        getPastData()





        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()

        cartArrayList = ArrayList<Meal>()

        businessName = intent.getStringExtra("businessname").toString()
        businessAddress = intent.getStringExtra("businessaddress").toString()
        businessImage = intent.getStringExtra("businessimage").toString()
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")



        /*activeButton.setOnClickListener {

            pendingAdapter = PendingRecyclerAdapter(pendingArrayList)
            pending_recyclerView.layoutManager = LinearLayoutManager(this)
            pending_recyclerView.setHasFixedSize(true)
            pending_recyclerView.adapter = pendingAdapter

        }*/





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
                    intent.putExtra("totalsavedmoney", totalSavedMoney)
                    intent.putExtra("totalsavedfood", totalSavedFood)
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

    /*private fun getPendingData(){
        database = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.uid).child("pending_orders")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (pendingSnapShot in snapshot.children){
                        val order = pendingSnapShot.getValue(PendingOrder::class.java)
                        pendingArrayList.add(order!!)
                    }
                    //pending_recyclerView.adapter = pendingAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }*/

    private fun getPastData(){
        database = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.uid).child("past_orders")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (pastSnapShot in snapshot.children){
                        val order = pastSnapShot.getValue(PendingOrder::class.java)
                        pastArrayList.add(order!!)
                        totalSavedMoney = totalSavedMoney + order.profit
                        totalSavedFood = totalSavedFood + 1
                    }
                    past_recyclerView.adapter = pastAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onItemClick(position: Int) {
        val clickedItem = pastArrayList[position]
        Log.d("UTKUDEBUG", "debug data")
        //Toast.makeText(this, clickedItem.businessID.toString(), Toast.LENGTH_LONG).show()
        pastAdapter.notifyItemChanged(position)
        var bundle = Bundle()
        bundle!!.putSerializable("pastarraylist",clickedItem.mealList)
        val intent = Intent(this, DetailedPastOrderActivity::class.java)
        intent.putExtra("orderbusinessname", clickedItem.orderBusinessName)
        intent.putExtra("orderbusinessaddress", clickedItem.orderBusinessAddress)
        intent.putExtra("date",clickedItem.orderDate)
        intent.putExtra("profit",clickedItem.profit)
        intent.putExtra("totalamount",clickedItem.totalAmount)
        intent.putExtra("name", name)
        intent.putExtra("surname", surname)
        intent.putExtra("email", email)
        intent.putExtra("phonenumber", phoneNumber)
        intent.putExtra("bundle",bundle)
        //intent.putExtra("pickuptimerange", clickedItem.)
        startActivity(intent)
        finish()
    }


}