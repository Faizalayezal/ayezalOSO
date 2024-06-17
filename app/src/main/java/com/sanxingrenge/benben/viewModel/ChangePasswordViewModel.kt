package com.sanxingrenge.benben.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {


    fun changePassword(
        context: Context,
        currantPassword: String,
        password: String,
        passwordConfirm: String,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.changePassword(uid ?: 0, currantPassword, password, passwordConfirm)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
            }
        }


    fun changeEmailSandOtp(
        context: Context,
        current_email: String,
        new_email: String,
        onComplete: (status: Boolean, msg: String, otp: Int) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.changeEmailSandOtp(uid ?: 0, current_email, new_email)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status ?: true, data.data?.message.toString(), data.data?.otp ?: 0)
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status ?: true, data.data?.message.toString(), data.data?.otp ?: 0)
                }
            }
        }

    fun changeEmail(
        context: Context,
        new_email: String,
        otp: String,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.changeEmail(uid ?: 0, new_email, otp)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message.toString())
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message.toString())
                }
            }
        }



}



