package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.foodie.R

class SelectUserTypeActivity : AppCompatActivity() {
    var userTypeCounter : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user_type)


    }

    fun customerClicked(view: View) {
        userTypeCounter = 1
    }

    fun businessClicked(view: View) {
        userTypeCounter = 2
    }

    fun continueClicked(view: View) {
        if (userTypeCounter == 1){
            val intent = Intent(this, CustomerSignUpActivity::class.java).apply {
            }
            startActivity(intent)
        }
        if (userTypeCounter == 2){
            val intent = Intent(this, LoginActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
}