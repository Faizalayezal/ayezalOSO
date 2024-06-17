package com.sanxingrenge.benben.screens.loginRegisterModule

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.ui.theme.QuillGrey
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.SignInClickableText
import kotlinx.coroutines.launch

@Composable
fun GenderSelectDesign(selectedGender: String, onUpdated: (String) -> Unit) {
    WelcomeText("Which one are you?")
    AddSpace(size = 60.dp)
    val liveSelectedData = remember {
        mutableStateOf(selectedGender)
    }
    onUpdated.invoke(liveSelectedData.value)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.width(280.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { liveSelectedData.value = "male" }) {
                Image(
                    painter = painterResource(id = R.drawable.boy),
                    contentDescription = "",
                    Modifier
                        .border(
                            width = 1.dp,
                            if (liveSelectedData.value == "male") VioletBlue else QuillGrey,
                            shape = CircleShape
                        )
                        .size(120.dp)
                        .padding(30.dp),
                )
            }
            AddSpace(15.dp)
            Text(
                text = "Male",
                fontFamily = poppinsRegular,
                fontSize = 15.sp,
                color = if (liveSelectedData.value == "male") VioletBlue else QuillGrey
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { liveSelectedData.value = "female" }) {
                Image(
                    painter = painterResource(id = R.drawable.girl),
                    contentDescription = "",
                    Modifier
                        .border(
                            width = 1.dp,
                            if (liveSelectedData.value == "female") VioletBlue else QuillGrey,
                            shape = CircleShape
                        )
                        .size(120.dp)
                        .padding(30.dp),

                    )
            }
            AddSpace(15.dp)
            Text(
                text = "Female",
                fontFamily = poppinsRegular,
                fontSize = 15.sp,
                color = if (liveSelectedData.value == "female") VioletBlue else QuillGrey
            )
            AddSpace(size = 60.dp)
        }
    }
}