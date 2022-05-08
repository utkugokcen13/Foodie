package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.CartRecyclerAdapter
import com.app.foodie.adapter.DetailedPastMenuAdapter
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.app.foodie.models.PendingOrder
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_detailed_past_order.*
import kotlinx.android.synthetic.main.activity_orders.*

class DetailedPastOrderActivity : AppCompatActivity() {
    private lateinit var pastArrayList : ArrayList<Meal>
    private lateinit var detailedPastMenuAdapter : DetailedPastMenuAdapter

    private lateinit var databaseForTotalRating: DatabaseReference
    private lateinit var databaseForRatingCount: DatabaseReference

    private lateinit var businessName : String
    private lateinit var businessImage : String
    private lateinit var businessAddress : String
    private lateinit var date : String
    private lateinit var profit : String
    private lateinit var totalAmount : String

    private lateinit var name : String
    private lateinit var surname : String
    private lateinit var email : String
    private lateinit var phoneNumber : String
    private var totalRating : Double? = 0.0
    private var ratingCount : Int? = 0

    private var tempRating : Double? = 0.0

    private lateinit var currentUser : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_past_order)

        if(intent !=null)
        {
            val bundles:Bundle = intent.extras!!.get("bundle") as Bundle
            if(bundles!=null)
            {
                pastArrayList = bundles.getSerializable("pastarraylist") as ArrayList<Meal>
                detailedPastMenuAdapter = DetailedPastMenuAdapter(pastArrayList,this)
                recyclerViewDetailedPast.layoutManager = LinearLayoutManager(this)
                recyclerViewDetailedPast.setHasFixedSize(true)
                recyclerViewDetailedPast.adapter = detailedPastMenuAdapter

            }
        }

        businessName = intent.getStringExtra("orderbusinessname").toString()
        businessAddress = intent.getStringExtra("orderbusinessaddress").toString()
        date = intent.getStringExtra("date").toString()
        profit = intent.getDoubleExtra("profit",0.0).toString()
        totalAmount = intent.getDoubleExtra("totalamount",0.0).toString()

        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()




        orderBusinessName.text = businessName
        orderBusinessAddress.text = businessAddress
        orderDate.text = date
        orderProfit.text = profit + " ₺"
        orderTotalAmount.text = totalAmount + " ₺"

        button3.setOnClickListener {
            getRatingData()
        }

    }

    private fun getRatingData(){
        databaseForTotalRating = FirebaseDatabase.getInstance().getReference("Business").child(businessName).child("totalRating")
        databaseForTotalRating.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    totalRating = snapshot.getValue(Double::class.java)
                    //totalRating = totalRating?.plus(ratingBar.rating.toDouble())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        databaseForRatingCount = FirebaseDatabase.getInstance().getReference("Business").child(businessName).child("ratingCount")
        databaseForRatingCount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    ratingCount = snapshot.getValue(Int::class.java)
                    //ratingCount = ratingCount?.plus(1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //databaseForTotalRating.setValue(totalRating)
        //databaseForRatingCount.setValue(ratingCount)
        Log.d("Total Rating:",totalRating.toString())
        Log.d("RatingCount",ratingCount.toString())
        val totalRatingTemp = totalRating!! + ratingBar.rating.toDouble()
        val ratingCountTemp = ratingCount!! + 1

        databaseForTotalRating.setValue(totalRatingTemp)
        databaseForRatingCount.setValue(ratingCountTemp)

    }

    fun backClicked(view:View){

        val intent = Intent(this, OrdersActivity::class.java)
        intent.putExtra("businessname", businessName)
        intent.putExtra("businessaddress", businessAddress)
        intent.putExtra("name", name)
        intent.putExtra("surname", surname)
        intent.putExtra("email", email)
        intent.putExtra("phonenumber", phoneNumber)
        startActivity(intent)
        finish()
    }


}