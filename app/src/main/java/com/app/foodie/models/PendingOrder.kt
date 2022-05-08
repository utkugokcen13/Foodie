package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class PendingOrder(val orderBusinessName : String = "", val orderBusinessAddress : String = "",val orderDate: String = "",
                        val orderStatus: String = "pending", val totalAmount : Double = 0.0, val profit : Double = 0.0, val mealList : ArrayList<Meal> = arrayListOf<Meal>())
{
}