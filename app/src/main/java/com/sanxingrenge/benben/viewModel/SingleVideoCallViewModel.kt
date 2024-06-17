package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SingleVideoCallViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {


    fun singleVideoCall(
        context: Context,
        reciveuid:String,
        onComplete: (status: Boolean,msg:String,sender_token:String,sender_u_id:String,channel_name:String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data =
                repository.singleVideoCall(
                    reciveuid,uid.toString()
                )) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"","","",data.data?.data?.channel_name?:"")

                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"","","",data.data?.data?.channel_name?:"")
                    Log.d("TAG", "subscriptionPlanPurchesApi: "+data)

                }
            }
        }


}



