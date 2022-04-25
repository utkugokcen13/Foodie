package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Meal(val meal_name: String = "", val meal_id: Int = 0, val ImageUrl: String = "", val meal_description: String = "",
                    val meal_price: Double = 0.4, val meal_discounted_price: Double = 0.2, val discount : Double = 0.0)
{
}