package com.sanxingrenge.benben.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.dataStoreClearAllData
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSettingScreenViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private val _podcasts = MutableStateFlow(UserDataObject())
    var podcastsLive: StateFlow<UserDataObject> = _podcasts

    fun collectUserData(context: Context)=viewModelScope.launch{
        context.dataStoreGetUserData().collect{
            _podcasts.value = it
        }
    }

    fun clearUserData(context: Context,onComplete: () -> Unit) =viewModelScope.launch{
        launch { context.dataStoreClearAllData() }.join()
        onComplete.invoke()
    }

    fun logout(
        context: Context,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.logout(uid.toString())) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
            }
        }

    fun updateFcmToken(
        context: Context,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.updateFcmToken(uid.toString(),"")) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
                is Resource.Success -> {
                    repository.updateFcmToken(uid.toString(),"")
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
            }
        }

    fun deleteUser(
        context: Context,
        onComplete: (status: Boolean, msg: String) -> Unit
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId

            when (val data =
                repository.deleteUser(uid ?: 0)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true, data.data?.message?:"")
                }
            }
        }
}