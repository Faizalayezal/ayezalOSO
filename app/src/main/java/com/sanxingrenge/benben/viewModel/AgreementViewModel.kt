package com.sanxingrenge.benben.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.responseModel.AgreementDataResponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private var AgreementDataList: ArrayList<AgreementDataResponse>? = null

    fun getAgreementDataList(onListReceived: (ArrayList<AgreementDataResponse>,status:Boolean/*, firstTimeSelectedItem: AgreementDataResponse?*/) -> Unit) =
        viewModelScope.launch {

            if (!AgreementDataList.isNullOrEmpty()) {
                onListReceived.invoke(AgreementDataList!!,false)
                return@launch
            }

            when (val data = repository.getAgreementContentList()) {
                is Resource.Error -> {
                    Log.d("TAG", "getAgreementDataList: " + data.message)

                }
                is Resource.Success -> {
                    AgreementDataList = data.data?.data?.let {
                        ArrayList(it)
                    }

                    if (!AgreementDataList.isNullOrEmpty()) onListReceived.invoke(
                        AgreementDataList!!,true
                       // AgreementDataList?.getOrNull(0)
                    )
                }
            }

        }


}



