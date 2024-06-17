package com.sanxingrenge.benben.responseModel

data class ChatResponse(
    val status: Boolean?,
    val message: String?,
    val data: ChatResponseData?,
)
data class ChatResponseData(

    val sender_id:String?,
    val message:String?,
    val receiver_id:String?,
    val updated_at:String?,
    val created_at:String?,
    val id:Int?,
)

