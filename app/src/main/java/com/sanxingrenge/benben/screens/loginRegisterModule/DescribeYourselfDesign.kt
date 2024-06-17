package com.sanxingrenge.benben.screens.loginRegisterModule


import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.SignInClickableText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DescribeYourselfDesign(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    bottomSheetType: MutableState<BottomSheetType>,
    showAllError: MutableState<Boolean>,
    liveUserData: UserDataObject,
    onUpdated: (UserDataObject) -> Unit
) {
    WelcomeText("Describe yourself")
    AddSpace(size = 60.dp)
    val coroutineScope = rememberCoroutineScope()
    SignInClickableText("Relationship", liveUserData.relationship ?: "", onDataChanged = { item ->
    }, showAllError = showAllError, isValidData = { isError, str ->
        onUpdated.invoke(
            liveUserData.copy(
                relationshipIsError = isError,
                relationship = str,
            )
        )
    }) {
        bottomSheetType.value = BottomSheetType.Relationship
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    AddSpace(size = 20.dp)
    SignInClickableText("Marital status",
        liveUserData.maritalStatus ?: "",
        onDataChanged = { item ->

        },
        showAllError = showAllError,
        isValidData = { isError, str ->
            onUpdated.invoke(
                liveUserData.copy(
                    maritalStatusIsError = isError,
                    maritalStatus = str,
                )
            )
        }) {
        bottomSheetType.value = BottomSheetType.MaritalStatus
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }
    AddSpace(size = 20.dp)
    SignInClickableText("Kids", liveUserData.kids ?: "", onDataChanged = { item ->
    }, showAllError = showAllError, isValidData = { isError, str ->
        onUpdated.invoke(
            liveUserData.copy(
                kidsIsError = isError,
                kids = str,
            )
        )
    }) {
        bottomSheetType.value = BottomSheetType.Kids
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    AddSpace(size = 20.dp)
    SignInClickableText("Zodiac Sign",
        liveUserData.zodiacSign ?: "",
        onDataChanged = { item ->
    }, showAllError = showAllError, isValidData = { isError, str ->
        onUpdated.invoke(
            liveUserData.copy(
                zodiacSignError = isError,
                zodiacSign = str,
            )
        )
    }) {
        bottomSheetType.value = BottomSheetType.zodiac
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    AddSpace(size = 20.dp)

    SignInClickableText("Animal Sign", liveUserData.animalSign ?: "", onDataChanged = { item ->
    }, showAllError = showAllError, isValidData = { isError, str ->
        onUpdated.invoke(
            liveUserData.copy(
                animalSignError = isError,
                animalSign = str,
            )
        )
    }) {
        bottomSheetType.value = BottomSheetType.animal
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    AddSpace(size = 60.dp)
}