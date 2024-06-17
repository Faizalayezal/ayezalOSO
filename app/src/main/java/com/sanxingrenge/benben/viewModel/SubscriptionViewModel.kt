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
class SubscriptionViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {


    fun subscriptionPlanPurchesApi(
        context: Context,
        start_date:String,
        end_date: String,
        plan_type: String,
        amount: String,
        onComplete: (status: Boolean,msg:String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data =
                repository.addSubscriptionPlan(
                    uid ?: 0,start_date, end_date, plan_type, amount, "test"
                )) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"")

                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"")
                    Log.d("TAG", "subscriptionPlanPurchesApi: "+data)

                }
            }
        }


}



