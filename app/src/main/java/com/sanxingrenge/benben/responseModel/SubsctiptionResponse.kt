package com.sanxingrenge.benben.responseModel

data class SubsctiptionResponse(
    val status: Boolean?,
    val message: String?,
    val data: SubsctiptionDataResponse?,
)
data class SubsctiptionDataResponse(

    val user_id:String?,
    val start_date:String?,
    val end_date:String?,
    val plan_type:String?,
    val amount:String?,
    val transaction_id:String?,
    val updated_at:String?,
    val created_at:String?,
    val id:Int?,
)

