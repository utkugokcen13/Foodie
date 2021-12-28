package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.databinding.ActivityCustomerMainBinding
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCustomerMainBinding
    private lateinit var arrayList : ArrayList<String>
    private lateinit var mainAdapter : MainRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        arrayList = ArrayList<String>()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainRecyclerAdapter(arrayList)
        binding.recyclerView.adapter = mainAdapter

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



}