package com.sanxingrenge.benben.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.retrofilt.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLastSeenViewModel @Inject constructor(application: Application, val repository: MainRepo
) : BaseViewModel(application) {

    private lateinit var database: DatabaseReference

    var userId=""
    fun start(){
        database = FirebaseDatabase.getInstance().reference
        viewModelScope.launch {
            context.dataStoreGetUserData().collect {
                userId=it.userId.toString()

                if(isStartedEarly){
                    isStartedEarly=false
                    makeOnline()
                }
            }
        }
    }

    fun makeOffline(){
        val ab= OnlineStatusObj("0", System.currentTimeMillis().toString())
        database.child("onlineStatus").child(userId).setValue(ab)
    }

    private var isStartedEarly=false

    fun makeOnline(){

        if(userId==""){
            isStartedEarly=true
            return
        }

        Log.d("TAG", "makeOnlinesfsdf: "+userId)
        val ab= OnlineStatusObj("1", System.currentTimeMillis().toString())
        database.child("onlineStatus").child(userId).setValue(ab)
    }


}

data class OnlineStatusObj(
    var status: String? = null,
    var lastSeen: String? = null,
)








