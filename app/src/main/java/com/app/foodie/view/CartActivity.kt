package com.app.foodie.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.CartRecyclerAdapter
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.activity_detailed_business.*
import kotlinx.android.synthetic.main.recycler_row_cart.*

//TODO SharedPreferences kullanarak carttaki ürünleri save et.

class CartActivity : AppCompatActivity() {
    private lateinit var cartArrayList : ArrayList<Meal>
    private var totalAmount : Double = 0.0
    private var earning : Double = 0.0
    private lateinit var cartAdapter : CartRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var spinner : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val businessImage = intent.getStringExtra("businessimage")
        var actionBar = supportActionBar
        val PaymentMethods = resources.getStringArray(R.array.PaymentMethods)

        spinner = findViewById(R.id.spinner1)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, PaymentMethods
            )
            spinner.adapter = adapter
        }
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
            var bundle = Bundle()
            bundle!!.putSerializable("cartarraylist",cartArrayList)
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("businessname", businessName)
            intent.putExtra("businessaddress", businessAddress)
            intent.putExtra("businessimage", businessImage)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
            finish()
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
}
