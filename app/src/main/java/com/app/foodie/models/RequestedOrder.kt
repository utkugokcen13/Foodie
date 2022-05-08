package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class RequestedOrder(val customerUid : String = "", val customerName : String = "",val orderDate: String = "", val paymentMethod : String = "Cash",
                         val totalAmount : Double = 0.0, val mealList : ArrayList<Meal> = arrayListOf<Meal>())
{
}