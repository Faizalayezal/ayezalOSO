package com.sanxingrenge.benben.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanxingrenge.benben.responseModel.ChinesZodicDataResponse
import com.sanxingrenge.benben.responseModel.ConstellationDataResponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NormalZodiaViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private var zodiacDataList: ArrayList<ConstellationDataResponse>? = null

    fun getConstellationList(onListReceived: (ArrayList<ConstellationDataResponse>, firstTimeSelectedItem: ConstellationDataResponse?) -> Unit) =
        viewModelScope.launch {

            if (!zodiacDataList.isNullOrEmpty()) {
                onListReceived.invoke(zodiacDataList!!, null)
                return@launch
            }

            when (val data = repository.getConstellationList()) {
                is Resource.Error -> {
                    Log.d("TAG", "getZodiacList: " + data.message)

                }
                is Resource.Success -> {
                    zodiacDataList = data.data?.data

                    if (!zodiacDataList.isNullOrEmpty()) onListReceived.invoke(
                        zodiacDataList!!,
                        zodiacDataList?.getOrNull(0)
                    )
                }
            }

        }

    private var animalDataList: ArrayList<ChinesZodicDataResponse>? = null

    fun getAnimalList(onListReceived: (ArrayList<ChinesZodicDataResponse>, firstTimeSelectedItem: ChinesZodicDataResponse?) -> Unit) =
        viewModelScope.launch {

            if (!animalDataList.isNullOrEmpty()) {
                onListReceived.invoke(animalDataList!!, null)
                return@launch
            }

            when (val data = repository.getChinesZodiacList()) {
                is Resource.Error -> {
                    Log.d("TAG", "getZodiacList: " + data.message)

                }
                is Resource.Success -> {
                    animalDataList = data.data?.data

                    if (!animalDataList.isNullOrEmpty()) onListReceived.invoke(
                        animalDataList!!,
                        animalDataList?.getOrNull(0)
                    )
                }
            }

        }


}



