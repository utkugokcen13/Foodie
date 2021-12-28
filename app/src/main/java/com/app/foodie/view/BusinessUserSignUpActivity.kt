package com.app.foodie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.foodie.R
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import com.app.foodie.models.BusinessUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BusinessUserSignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCustomerSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_user_sign_up)
    }

    fun BusinessSignUp(view : View) {

        val email = binding.customerEmail.text.toString()
        val password = binding.customerEmail.text.toString()
        val addressBusiness = binding.customerEmail.text.toString()
        val businessName = binding.customerPhoneNumber.text.toString()
        val bankAccountName = binding.customerEmail.text.toString()
        val bankAccountNo = binding.customerEmail.text.toString()
        val phoneNumber = binding.customerPhoneNumber.text.toString()

        if (addressBusiness.isNotEmpty() && businessName.isNotEmpty()) {
            
        }
    }

    fun writeNewBusinessUser(businessName: String, addressBusiness: String) {
        database = Firebase.database.reference
        val user = BusinessUser(businessName, addressBusiness)

        database.child("BusinessUsers").child(businessName).setValue(user)
    }
}