package com.sanxingrenge.benben.responseModel

data class AgreementResponse(
    val status: Boolean?,
    val data: List<AgreementDataResponse>?,
)
data class AgreementDataResponse(
    val id:Int?,
    val title_key:String?,
    val title:String?,
    val description:String?,
    val updated_at:String?,
    val created_at:String?,
)


data class DisEdit(
    var notEdit:Boolean=false
)

