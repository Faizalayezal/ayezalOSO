package com.sanxingrenge.benben.responseModel

data class GetQuestionsListResponse(
    val status: Boolean?,
    val message:String?,
    val data: ArrayList<GetQuestionsListDataResponse>?,
)
data class GetQuestionsListDataResponse(
    val id:Int?,
    val question:String?,
    val answer1:String?,
    val answer2:String?,
    val answer3:String?,
    val status:String?,
    var selectedId:Int?,
)

