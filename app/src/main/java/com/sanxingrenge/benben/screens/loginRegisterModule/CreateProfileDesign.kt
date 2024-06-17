package com.sanxingrenge.benben.screens.loginRegisterModule


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.CustomInputType
import com.sanxingrenge.benben.uiComponents.SignInClickableText
import com.sanxingrenge.benben.uiComponents.SignInEditText
import com.sanxingrenge.benben.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

private const val TAG = "CreateProfileDesign"

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CreateProfileDesign(
    bottomSheetScaffoldState: BottomSheetScaffoldState?=null,
    bottomSheetType: MutableState<BottomSheetType>?=null,
    viewModel: RegisterViewModel?=null,
    showAllError: MutableState<Boolean>?=null,
    liveUserData: UserDataObject?=null,
    selectedData: ((UserDataObject) -> Unit)?=null,
    openLocationPicker: (() -> Unit)? = null
) {
    Log.d(TAG, "CreateProfileDesign: testRecreated>>")
    WelcomeText("Create your profile")
    AddSpace(size = 60.dp)
    val context = LocalContext.current
    val liveLocationData = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(key1 = true, block = {
        viewModel?.getLocationSelected(context) {
            liveLocationData.value = it.name
        }
    })

    SignInEditText(
        "Name", CustomInputType.Name,
        showAllError = showAllError,
        selectedText = liveUserData?.name ?: "",
        onDataChanged = { item ->
        },
    ) { isError, str ->
        selectedData?.invoke(
            liveUserData?.copy(
                nameIsError = isError,
                name = str,
            )?: UserDataObject()
        )
    }
    AddSpace(size = 20.dp)

    val coroutineScope = rememberCoroutineScope()
    SignInClickableText("Birthdate", liveUserData?.bday ?: "", onDataChanged = { item ->
    }, showAllError = showAllError, isValidData = { isError, str ->
        selectedData?.invoke(
            liveUserData?.copy(
                bdayIsError = isError,
                bday = str,
            )?:UserDataObject()
        )
    }) {
        bottomSheetType?.value = BottomSheetType.Birthdate
        coroutineScope.launch {
            if (bottomSheetScaffoldState?.bottomSheetState?.isCollapsed == true) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState?.bottomSheetState?.collapse()
        }
    }

    AddSpace(size = 20.dp)
    SignInClickableText("Country", liveLocationData.value ?: "", onDataChanged = { item ->
        //onUpdated.invoke(liveUserData.copy(country = item))
    }, showAllError = showAllError, isValidData = { isError, str ->

    }) {
        openLocationPicker?.invoke()
    }
    AddSpace(size = 20.dp)
    SignInClickableText("Religion", liveUserData?.religion ?: "", onDataChanged = { item ->
        //onUpdated.invoke(liveUserData.copy(religion = item))

    }, showAllError = showAllError, isValidData = { isError, str ->
        selectedData?.invoke(
            liveUserData?.copy(
                religionIsError = isError,
                religion = str,
            )?:UserDataObject()
        )
    }) {
        bottomSheetType?.value = BottomSheetType.Religion
        coroutineScope.launch {
            if (bottomSheetScaffoldState?.bottomSheetState?.isCollapsed==true) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState?.bottomSheetState?.collapse()
        }
    }
    AddSpace(size = 80.dp)
}