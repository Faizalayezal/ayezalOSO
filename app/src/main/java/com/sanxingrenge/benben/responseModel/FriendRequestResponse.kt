package com.sanxingrenge.benben.responseModel

data class FriendRequestResponse(
    val status: Boolean?,
    val message: String?,
    val data: ArrayList<FriendRequestDataResponse>?,
)
data class FriendRequestDataResponse(
    val id:Int?,
    val sender_id:Int?,
    val receiver_id:Int?,
    val status:String?,
    val created_at:String?,
    val updated_at:String?,
    val sender_user:SenderList?,
    val receiver_user:SenderList?
)
data class SenderList(
    val id:Int?,
    val name:String?,
    val image:String?,
    val user_photos: ArrayList<usersphoto>?,

    )
