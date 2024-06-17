package com.sanxingrenge.benben.screens.loginRegisterModule

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.CustomInputType
import com.sanxingrenge.benben.uiComponents.SignInClickableText
import com.sanxingrenge.benben.uiComponents.SignInEditText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkLifeDesign(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    bottomSheetType: MutableState<BottomSheetType>,

    showAllError: MutableState<Boolean>,
    liveUserData: UserDataObject,
    onUpdated: (UserDataObject) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    WelcomeText("About your work life")
    AddSpace(size = 60.dp)
    SignInClickableText("Education", liveUserData.education ?: "", onDataChanged = { item ->
    }, showAllError = showAllError, isValidData = { isError, str ->
        onUpdated.invoke(
            liveUserData.copy(
                educationIsError = isError,
                education = str,
            )
        )
    }) {
        bottomSheetType.value = BottomSheetType.Education
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
            else bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }
    AddSpace(size = 20.dp)
    SignInEditText(
        "Occupation", CustomInputType.Name,
        showAllError = showAllError,
        selectedText = liveUserData.occupation ?: "",
        onDataChanged = { item ->

        },
    ) { isError, str ->
        onUpdated.invoke(
            liveUserData.copy(
                occupationIsError = isError,
                occupation = str,
            )
        )
    }
    AddSpace(size = 60.dp)
}
