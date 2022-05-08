package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Business(val businessName: String = "", val businessID: Int = 0, val ImageUrl: String = "", val businessType: String = "",
                    val totalRating: Double = 0.0, val ratingCount : Int = 0 , val latitude: Double = 0.0, val longitude: Double = 0.0, val businessAddress: String = "",
                    val markerID: String = "")
{
}