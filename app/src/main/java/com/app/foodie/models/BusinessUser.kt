package com.app.foodie.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class BusinessUser(val businessName: String? = null, val addressBusiness: String? = null,
                        val phoneNumber: String? = null, val email: String? = null,
                        val bankAccountName: String?=null,val bankAccountNo: String?=null)
{
}