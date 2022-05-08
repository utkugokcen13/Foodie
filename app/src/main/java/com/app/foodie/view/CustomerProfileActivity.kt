package com.app.foodie.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.app.foodie.R
import com.app.foodie.adapter.ViewPagerAdapter
import com.app.foodie.databinding.ActivityCustomerProfileBinding
import com.app.foodie.models.Meal
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_customer_profile.*
import me.relex.circleindicator.CircleIndicator3

class CustomerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerProfileBinding
    private lateinit var auth : FirebaseAuth

    private var textList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    private var colorList = mutableListOf<Int>()

    private lateinit var money : String
    private lateinit var food : String
    private lateinit var invite : String
    private lateinit var color1 : Color

    private lateinit var database: DatabaseReference
    private lateinit var mealArrayList : ArrayList<Meal>
    private lateinit var businessName : String
    private lateinit var businessImage : String
    private lateinit var businessAddress : String
    private lateinit var cartArrayList : ArrayList<Meal>

    private lateinit var name : String
    private lateinit var surname : String
    private lateinit var email : String
    private lateinit var phoneNumber : String

    private lateinit var currentUser : FirebaseUser

    private var totalSavedMoney : Double = 0.0
    private var totalSavedFood : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerProfileBinding.inflate(layoutInflater)
        var actionBar = supportActionBar
        currentUser = FirebaseAuth.getInstance().currentUser!!

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()

        businessName = intent.getStringExtra("businessname").toString()
        businessAddress = intent.getStringExtra("businessaddress").toString()
        businessImage = intent.getStringExtra("businessimage").toString()
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")
        val previousActivity = intent.getIntExtra("checkprevious",0)

        totalSavedMoney = intent.getDoubleExtra("totalsavedmoney", 0.0)
        totalSavedFood = intent.getIntExtra("totalsavedfood", 0)

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




        //val nameText = findViewById<TextView>(R.id.nameText)
        //val surnameText = findViewById<TextView>(R.id.surnameText)

        //nameText.text = name
        //surnameText.text = surname

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
            when(it.itemId) {
                R.id.nav_map -> {
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist", cartArrayList)
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("name", name)
                    intent.putExtra("surname", surname)
                    intent.putExtra("email", email)
                    intent.putExtra("phonenumber", phoneNumber)
                    intent.putExtra("bundle", bundle)
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
        val color1s = "#F08080"
        val color1 = Color.parseColor(color1s)
        val color2s = "#ADD8E6"
        val color2 = Color.parseColor(color2s)
        val color3s = "#ADDFAD"
        val color3 = Color.parseColor(color3s)

        money = "You saved " + totalSavedMoney + " ₺"
        food = "You saved " + totalSavedFood + " servings of food from being thrown away"
        invite = "You invited " + 4 + " person"

        addToList(money,R.drawable.dollar,color3)
        addToList(food,R.drawable.recyclebin,color2)
        addToList(invite,R.drawable.group, color1 )

        view_pager2.adapter = ViewPagerAdapter(textList,imagesList,colorList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        //indicator.setViewPager(view_pager2)



    }

    private fun addToList(text : String, image : Int, color : Int){
        textList.add(text)
        imagesList.add(image)
        colorList.add(color)
    }



    fun aboutClicked(view: View){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)

    }
    fun logoutClicked(view: View){
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}