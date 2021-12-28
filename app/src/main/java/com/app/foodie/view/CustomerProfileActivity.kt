package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.foodie.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_profile)

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

    fun aboutClicked(view: View){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)

    }
}