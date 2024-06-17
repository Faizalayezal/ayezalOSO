package com.sanxingrenge.benben.responseModel

data class GetFeedBackListResponse(
    val status: Boolean?,
    val message: String?,
    val data: List<GetFeedBackDataListResponse>?,
)
data class GetFeedBackDataListResponse(
    val id:Int?,
    val user_id:Int?,
    val email:String?,
    val feedback_type:String?,
    val description:String?,
    val date:String?,
    val created_at:String?,
    val updated_at:String?,
    val feedback_images:List<FeedBackList>?
)
data class FeedBackList(
    val id:Int?,
    val feedback_id:Int?,
    val image:String?,
    val updated_at:String?,
    val created_at:String?,
)
