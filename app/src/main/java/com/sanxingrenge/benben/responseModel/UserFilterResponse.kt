package com.sanxingrenge.benben.responseModel

data class UserFilterResponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<UserFilterDataResponse>?,
)
data class UserFilterDataResponse(
    val email:String?,
    val id:Int?,
    val name:String?,
    val birthdate:String?,
    val age:Int?,
    val register_otp:String?,
    val religion_id:String?,
    val gender:String?,
    val education_id:String?,
    val occupation:String?,
    val height:String?,
    val weight:String?,
    val relationship_id:String?,
    val marital_status_id:String?,
    val kid_id:String?,
    val address:String?,
    val latitude:String?,
    val longitude:String?,
    val image:String?,
    val updated_at:String?,
    val created_at:String?,
    val user_interest:ArrayList<InterstList>?
)

