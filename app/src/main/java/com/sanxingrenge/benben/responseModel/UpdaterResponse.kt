package com.sanxingrenge.benben.responseModel

data class UpdaterResponse(
    val status: Boolean?,
    val message: String?,
    val data: UpdateDataResponse?,
)

data class UpdateDataResponse(
    val id: Int?,
    val email: String?,
    val name: String?,
    val birthdate: String?,
    val age: Int?,
    val register_otp: String?,
    val religion_id: String?,
    val gender: String?,
    val education_id: String?,
    val occupation: String?,
    val height: String?,
    val weight: String?,
    val relationship_id: String?,
    val marital_status_id: String?,
    val kid_id: String?,
    val zodiac_sign_id: String?,
    val animal_sign_id: String?,
    val personality: String?,
    val subscribe_type: String?,
    val user_total_points: String?,
    val user_points_percentage: String?,
    val address: String?,
    val latitude: String?,
    val longitude: String?,
    val bio: String?,
    val image: String?,
    val updated_at: String?,
    val created_at: String?,
    val user_interest: ArrayList<InterstList>?,
    val user_subscription: ArrayList<SubscriptionList>?,
    val zodiac_sign: FutureSignList?,
    val animal_sign: FutureSignList?,
    val google_id: String?,
    val facebook_id: String?,
    val user_photos: ArrayList<usersphoto>?,


    )

data class usersphoto(
    val id: Int?,
    val user_id: Int?,
    val name: String?,
    val type: String?,
    val created_at: String?,
    val updated_at: String?,
    val image_url: String?,

    )

data class InterstList(
    // val id:Int?,
    val user_id: Int?,
    val interest_id: Int?,
    val level: String?,
    val updated_at: String?,
    val created_at: String?,
)

data class SubscriptionList(
    val id: Int?,
    val user_id: Int?,
    val start_date: String?,
    val end_date: String?,
    val plan_type: String?,
    val amount: Int?,
    val transaction_id: String?,
    val updated_at: String?,
    val created_at: String?,
)

data class FutureSignList(
    val id: Int?,
    val title: String?,
    val image: String?,
    val image_full_path: String?,
)
