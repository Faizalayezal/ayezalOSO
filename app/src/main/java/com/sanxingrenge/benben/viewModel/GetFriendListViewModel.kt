package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.responseModel.FriendRequestDataResponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetFriendListViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {


  //  private var friendDataList: ArrayList<FriendRequestDataResponse>? = null
    private var friendDataList: List<FriendRequestDataResponse>? = null


    fun getFriendRequestList(
        onListReceived: (List<FriendRequestDataResponse>?, status: Boolean) -> Unit,
        context: Context,
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data =
                repository.getFriendRequestList(uid ?: 0)) {
                is Resource.Error -> {
                    onListReceived.invoke(null, false)

                }
                is Resource.Success -> {
                    val datas=data.data?.data?.filter {
                        it.sender_user!=null
                    }

                    friendDataList = datas


                    if (!friendDataList.isNullOrEmpty()) {
                        Log.d("TAG", "getFriendRequestList: "+friendDataList)
                        onListReceived.invoke(
                            friendDataList, true
                        )


                    } else {

                        onListReceived.invoke(null, false)

                    }

                }
            }
        }


    fun approveFrinendRequest(
        uid:Int,
        Status: String,
        context: Context,
        onComplete: (status: Boolean,msg:String) -> Unit
    ) =
        viewModelScope.launch {
         //   val userObj = context.dataStoreGetUserData().firstOrNull()
          //  val uid = userObj?.userId


            when (val data =
                repository.approveFrinendRequest(uid , Status)) {
                is Resource.Error -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"")

                }
                is Resource.Success -> {
                    onComplete.invoke(data.data?.status?:true,data.data?.message?:"")

                }
            }
        }


}



