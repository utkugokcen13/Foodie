package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Toast
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import com.app.foodie.models.Business
import com.app.foodie.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CustomerSignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCustomerSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var currentUser : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerSignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        database = Firebase.database.reference


    }

    fun signUpClicked(view : View){

        val email = binding.customerEmail.text.toString()
        val password = binding.customerPassword.text.toString()
        val reEnteredPassword = binding.customerReEnterPassword.text.toString()
        val name = binding.customerName.text.toString()
        val surname = binding.customerSurname.text.toString()
        val phoneNumber = binding.customerPhoneNumber.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty() && reEnteredPassword.isNotEmpty()) {
            if (password != reEnteredPassword) {
                val text = "Passwords do not match"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    // success
                    writeNewUser(name, surname, phoneNumber, email)

                    val intent = Intent(this, CustomerMainActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("checkprevious",1)
                    intent.putExtra("surname", surname)
                    intent.putExtra("phoneNumber", phoneNumber)
                    intent.putExtra("email", email)


                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    fun writeNewUser(name: String, surname: String, phoneNumber: String, email: String) {
        currentUser = FirebaseAuth.getInstance().currentUser!!
        database = Firebase.database.reference
        val user = User(name, surname, phoneNumber, email, null, null)

        database.child("Users").child(currentUser.uid).setValue(user)
    }

    fun haveAccountClicked(view : View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*fun writeNewBusiness(name: String, type: String, rating: Double, latitude: Double, longitude: Double){
        database = Firebase.database.reference
        val business = Business(name, type, rating, latitude, longitude)

        database.child("Business").child(name).setValue(business)
    }*/


}