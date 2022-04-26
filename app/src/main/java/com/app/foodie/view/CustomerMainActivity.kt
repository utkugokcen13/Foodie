package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.databinding.ActivityCustomerMainBinding
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.models.Business
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.recycler_row.*


class CustomerMainActivity : AppCompatActivity(), MainRecyclerAdapter.OnItemClickListener {
    //private lateinit var binding : ActivityCustomerMainBinding

    private lateinit var mainAdapter : MainRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var businessArrayList : ArrayList<Business>
    //private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_main)
        businessArrayList = ArrayList<Business>()
        getBusinessData()
        mainAdapter = MainRecyclerAdapter(businessArrayList,this)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)


        val mSearchView = findViewById<SearchView>(R.id.searchView)


        mSearchView.setQueryHint("Search a Business");

        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val email = intent.getStringExtra("email")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile ->{
                    val intent = Intent(this, CustomerProfileActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("phoneNumber", phoneNumber)
                    intent.putExtra("email", email)
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


    override fun onItemClick(position: Int) {

        val clickedItem = businessArrayList[position]
        //Toast.makeText(this, clickedItem.businessID.toString(), Toast.LENGTH_LONG).show()
        mainAdapter.notifyItemChanged(position)
        val intent = Intent(this, DetailedBusinessActivity::class.java)
        intent.putExtra("businessname", clickedItem.businessName)
        intent.putExtra("businessaddress", clickedItem.businessAddress)
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