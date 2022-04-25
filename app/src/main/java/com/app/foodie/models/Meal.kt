package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Meal(val mealName: String = "", val mealID: Int = 0, val ImageUrl: String = "", val mealDescription: String = "",
                    val mealprice: Double = 0.0, val mealDiscountedPrice: Double = 0.0, val discount : Double = 0.0)
{
}