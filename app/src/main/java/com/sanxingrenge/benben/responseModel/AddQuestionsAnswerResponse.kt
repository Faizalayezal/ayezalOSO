package com.sanxingrenge.benben.responseModel

data class AddQuestionsAnswerResponse(
    val status: Boolean?,
    val message:String?,
    val data:AddQuestionsAnswerDataResponse?,
)
data class AddQuestionsAnswerDataResponse(
    val id:Int?,
    val user_id:Int?,
    val question_id:Int?,
    val answer:String?,
    val created_at:String?,
    val updated_at:String?,
)

