package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val name: String? = null, val surname: String? = null, val phoneNumber: String? = null, val email: String? = null ) {

}