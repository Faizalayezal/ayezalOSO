package com.sanxingrenge.benben.dataModel


data class FilterDataObject(
    val userId:Int?=null,
    val email:String?=null,
    val emailIsError:Boolean?=null,


    val gender:String?="male",
    val genderIsError:Boolean?=null,


    val relationship:String?=null,
    val relationshipIsError:Boolean?=null,

    val interestId1:String?=null,
    val interestId2:String?=null,
    val interestId3:String?=null,
    val interestId4:String?=null,
    val interestId5:String?=null,

    val interestLevel1:String?=null,
    val interestLevel2:String?=null,
    val interestLevel3:String?=null,
    val interestLevel4:String?=null,
    val interestLevel5:String?=null,

    val address:String?=null,
    val latitude:String?=null,
    val longitude:String?=null,

    val count:Int=0,
)