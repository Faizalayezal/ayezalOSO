package com.sanxingrenge.benben.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.constant.*
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    fun getAllBasicLists(onListReceived: () -> Unit) = viewModelScope.launch {

        when (val data = repository.getZodiacList("")) {
            is Resource.Error -> {
                Log.d("TAG", "getZodiacList: " + data.message)

            }
            is Resource.Success -> {
                data.data?.data?.let {
                    MainCountryList = it.country_list ?: listOf()
                    MainReligionList = it.religion_list ?: listOf()
                    MainEducationList = it.education_list ?: listOf()
                    MainRelationList = it.relationship_list ?: listOf()
                    MainMaritalList = it.marital_status_list ?: listOf()
                    MainKidsList = it.kid_list ?: listOf()
                    ZodiacList = it.zodiac_sign_list ?: listOf()
                    AnimalList = it.animal_sign_list ?: listOf()
                    MainInterestLevelList = it.interest_list ?: listOf()
                    onListReceived.invoke()
                }
            }
        }

    }

    fun clearLocalData(context: Context, onComplete: () -> Unit) {
        viewModelScope.launch {
            launch {
                context.dataStoreSetTempLocation("", "", "", 38)
                context.dataStoreClearAllData()
            }.join()
            onComplete.invoke()
        }
    }

}



