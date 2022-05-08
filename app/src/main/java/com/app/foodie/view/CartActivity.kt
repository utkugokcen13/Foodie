package com.app.foodie.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.CartRecyclerAdapter
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.models.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.activity_detailed_business.*
import kotlinx.android.synthetic.main.recycler_row_cart.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

//TODO SharedPreferences kullanarak carttaki ürünleri save et.

class CartActivity : AppCompatActivity() {
    private lateinit var cartArrayList : ArrayList<Meal>
    private var totalAmount : Double = 0.0
    private var earning : Double = 0.0
    private lateinit var cartAdapter : CartRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var spinner : Spinner
    private lateinit var currentUser : FirebaseUser
    private lateinit var currentDate : String

    private lateinit var name : String
    private lateinit var surname : String
    private lateinit var email : String
    private lateinit var phoneNumber : String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        getCurrentDate()
        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val businessImage = intent.getStringExtra("businessimage")
        var actionBar = supportActionBar
        database = Firebase.database.reference
        currentUser = FirebaseAuth.getInstance().currentUser!!
        val PaymentMethods = resources.getStringArray(R.array.PaymentMethods)

        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()



        if(actionBar != null){
            actionBar.setTitle("Cart")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        if(intent !=null)
        {
            val bundles:Bundle = intent.extras!!.get("bundle") as Bundle
            if(bundles!=null)
            {
                cartArrayList = bundles.getSerializable("cartarraylist") as ArrayList<Meal>
                if(cartArrayList.size == 0){
                    trashIcon.visibility = View.INVISIBLE
                    pickupTimeRange.visibility = View.INVISIBLE
                    businessNameCart.visibility = View.INVISIBLE
                    cardView2.visibility = View.INVISIBLE
                    confirmButton.visibility = View.INVISIBLE
                }else{
                    trolley.visibility = View.INVISIBLE
                    emptyCart.visibility = View.INVISIBLE

                    businessNameCart.text = businessName
                    cartAdapter = CartRecyclerAdapter(cartArrayList,this)
                    RecyclerViewCart.layoutManager = LinearLayoutManager(this)
                    RecyclerViewCart.setHasFixedSize(true)
                    RecyclerViewCart.adapter = cartAdapter
                }

            }
        }

        calculateTotalAmount()
        calculateEarning()
        totalPrice.text = totalAmount.toString() + " ₺"
        earningValue.text = earning.toString() + " ₺"



        trashIcon.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Empty you cart")
            builder.setMessage("Are you sure you want to empty your cart?")
            builder.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                cartArrayList.clear()
                var bundle = Bundle()
                bundle!!.putSerializable("cartarraylist",cartArrayList)
                val intent = Intent(this, CustomerMainActivity::class.java)
                intent.putExtra("businessname", businessName)
                intent.putExtra("businessaddress", businessAddress)
                intent.putExtra("name", name)
                intent.putExtra("surname", surname)
                intent.putExtra("email", email)
                intent.putExtra("phonenumber", phoneNumber)
                intent.putExtra("bundle",bundle)
                startActivity(intent)
                finish()
                dialogInterface.cancel()
            })
            builder.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            var alert = builder.create()
            alert.show()

        }

        confirmButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Complete the order")
            builder.setMessage("Are you sure you want to complete the order?")
            builder.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this,"You have successfully ordered.",Toast.LENGTH_SHORT).show()
                val order = PendingOrder(businessName!!,businessAddress!!,currentDate,"pending",totalAmount,earning,cartArrayList)
                val orderForBusiness = RequestedOrder(currentUser.uid,name + " " + surname,currentDate,"Cash",totalAmount,cartArrayList)
                database.child("Users").child(currentUser.uid).child("pending_orders").push().setValue(order)
                Log.d("business Name", businessName)
                database.child("Business").child(businessName).child("Orders").child("requested_orders").push().setValue(orderForBusiness)
                var bundle = Bundle()
                bundle!!.putSerializable("cartarraylist",cartArrayList)
                val intent = Intent(this, CustomerMainActivity::class.java)
                intent.putExtra("businessname", businessName)
                intent.putExtra("businessaddress", businessAddress)
                intent.putExtra("businessimage", businessImage)
                intent.putExtra("bundle",bundle)
                startActivity(intent)
                finish()
                dialogInterface.cancel()
            })
            builder.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            var alert1 = builder.create()
            alert1.show()

        }






        businessNameCart.text = businessName

        //val cartArrayList = intent.getSerializableExtra("cartarraylist" )


        //Log.d("TAG", cartArrayList.get(0).meal_name)

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
                    val intent = Intent(this, CustomerProfileActivity::class.java)
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

    private fun calculateTotalAmount(){
        for (item in cartArrayList){
            totalAmount += (item.meal_discounted_price) * item.meal_count
        }
    }

    private fun calculateEarning(){
        for (item in cartArrayList){
            earning += (item.meal_price - item.meal_discounted_price) * item.meal_count
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(){
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        currentDate = formatted
    }
}
