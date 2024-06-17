package com.sanxingrenge.benben.screens.loggedInModule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.MainInterestLevelList
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.GainsBoro
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.InterestChildListItem
import com.sanxingrenge.benben.uiComponents.InterestChildListLayout
import com.sanxingrenge.benben.uiComponents.InterestGridItem
import com.sanxingrenge.benben.uiComponents.InterestGridLayout
import com.sanxingrenge.benben.viewModel.EditInterestViewModel


private enum class InterestScreenType2 {
    MainInterest,
    ChildInterest,
}

private const val TAG = "EditInterestScreen"

@Composable
fun RegisterInterestActivity(
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    showLoading: (Boolean) -> Unit,
) {
    val viewModel: EditInterestViewModel = hiltViewModel()
    val context = LocalContext.current

    val otp = remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {

            otp.value = it.otp ?: ""
            Log.d(TAG, "RegisterInterestActivity213: " + it)
        }

    })
    Column(modifier = Modifier.fillMaxSize()) {

        val screenState = remember {
            mutableStateOf(InterestScreenType2.MainInterest)
        }

        MainTitle(
            when (screenState.value) {
                InterestScreenType2.MainInterest -> "Register Interests"
                InterestScreenType2.ChildInterest -> "Set Interests Level"
            }, screenState, popBack
        )

        var isFirstTime by remember {
            mutableStateOf(true)
        }
        var selectrfvalue by remember {
            mutableStateOf("")
        }
        var listGridData by remember {
            mutableStateOf(MainInterestLevelList.map {
                InterestGridItem(it.name ?: "", it.image ?: "")
            })
        }

        viewModel.updateSelectedPos(listGridData, context) {
            if (isFirstTime) {
                listGridData = it
                isFirstTime = false
            }
        }

        Log.d(TAG, "updateSelectedPos: testTestItemUpdatedNEW>>+++++++++++++++++++++")
        listGridData.forEach {
            Log.d(TAG, "updateSelectedPos: testTestItemUpdatedNEW>>${it.name}>>${it.isSelected}")
        }

        when (screenState.value) {
            InterestScreenType2.MainInterest -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        InterestGridLayout(listGridData, 4, clickable = true, openPopupOnClick = {
                            //save
                            viewModel.updateInterestList(it, context)
                            selectrfvalue = it.firstOrNull()?.name ?: ""
                            Log.d(TAG, "RegisterInterestActivity13: " + it.firstOrNull()?.name)
                        })
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 30.dp, vertical = 20.dp)
                                .height(50.dp)
                                .fillMaxWidth(),
                            onClick = {
                                if (selectrfvalue == "") {
                                    context.showToast("Please Select Any One Interest")
                                } else {
                                    screenState.value = InterestScreenType2.ChildInterest
                                }
                                // screenState.value = InterestScreenType2.ChildInterest
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = VioletBlue,
                            ),
                        ) {
                            Text(text = "Continue", fontFamily = poppinsMedium, color = White)
                        }
                    }

                }


            }

            InterestScreenType2.ChildInterest -> {

                val interestList: MutableState<ArrayList<InterestChildListItem>?> =
                    remember {
                        mutableStateOf(null)
                    }
                viewModel.getSelectedInterestList {
                    interestList.value = it

                }

                LazyColumn {
                    item {
                        interestList.value?.let {
                            InterestChildListLayout(it,listGridData) {
                                viewModel.updateChildInterestLevel(it, context)
                                Log.d(TAG, "RegisterInterestActivity:123 " + it)
                            }
                        }

                        val context = LocalContext.current
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 30.dp, vertical = 20.dp)
                                .height(50.dp)
                                .fillMaxWidth(),
                            onClick = {
                                //loading show

                                /* viewModel.updateUserInterestWithApi(context) {
                                     //loading dismiss
                                     if (it) {
                                         popBack?.invoke()
                                     }
                                 }*/


                                showLoading.invoke(true)
                                viewModel.callVerifyOtpAndRegisterApi(context) { it, msg ->
                                    showLoading.invoke(false)
                                    context.showToast(msg)
                                    if (it) {
                                        //   popBack?.invoke()
                                        openScreen?.invoke(Route.HOME_BASE)
                                    }
                                }

                                //
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = VioletBlue,
                            )
                        ) {
                            Text(text = "Save", fontFamily = poppinsMedium, color = White)
                        }

                    }
                }


                //InterestChildListLayout(list)

            }
        }

    }

}

@Composable
private fun MainTitle(
    title: String,
    screenState: MutableState<InterestScreenType2>,
    popBack: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 20.dp, start = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            when (screenState.value) {
                InterestScreenType2.MainInterest -> popBack?.invoke()
                InterestScreenType2.ChildInterest -> screenState.value =
                    InterestScreenType2.MainInterest
            }
        }, modifier = Modifier.size(50.dp)) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "", modifier = Modifier.size(40.dp)
            )
        }
        AddSpace(5.dp)
        Text(
            text = title,
            fontSize = 20.sp,
            fontFamily = poppinsBold,
            color = Color.Black
        )
    }
    Spacer(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(GainsBoro)
    )
}
