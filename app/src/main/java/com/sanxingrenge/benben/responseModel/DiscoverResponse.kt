package com.sanxingrenge.benben.responseModel

data class DiscoverResponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<DiscoverResponseData>?,
)

data class DiscoverResponseData(
    val id: Int?,
    val name: String?,
    val image: String?,
    val age: String?,
    val user_total_points: Int?,
    val user_points_percentage: String?,
    val personality: String?,
    val gender: String?,
    val zodiac_sign: signList?,
    val animal_sign: signList?,




    val email: String?,
    val country_id: Int?,
    val religion_id: String?,
    val education_id: String?,
    val relationship_id: String?,
    val marital_status_id: String?,
    val kid_id: String?,
    val city_id: String?,
    val latitude: String?,
    val longitude: String?,
    val zodiac_sign_id: Int?,
    val animal_sign_id: Int?,
    val status: String?,

    val gender_image: String?,
    val distance: String?,
    val birthdate:String?,
    val register_otp:String?,
    val occupation:String?,
    val height:String?,
    val weight:String?,
    val address:String?,
    val updated_at:String?,
    val created_at:String?,
    val user_interest:ArrayList<InterstList>?,
    var isFilter:Boolean=false



)
data class signList(
    val id: Int?,
    val title: String?,
    val image: String?,
    val image_full_path: String?,
    val status: String?,
    val updated_at:String?,
    val created_at:String?,
)

