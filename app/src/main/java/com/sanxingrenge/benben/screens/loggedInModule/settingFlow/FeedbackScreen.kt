package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns.image1
import com.sanxingrenge.benben.constant.Constns.image2
import com.sanxingrenge.benben.constant.Constns.image3
import com.sanxingrenge.benben.constant.Constns.image4
import com.sanxingrenge.benben.constant.Constns.image5
import com.sanxingrenge.benben.constant.Constns.image6
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.Harp
import com.sanxingrenge.benben.ui.theme.Nobel
import com.sanxingrenge.benben.ui.theme.PearlBush
import com.sanxingrenge.benben.ui.theme.RichCarmine
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.uiComponents.CustomInputType
import com.sanxingrenge.benben.uiComponents.SignInEditText
import com.sanxingrenge.benben.uiComponents.SolidButton
import com.sanxingrenge.benben.viewModel.AddFeedBackViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FeedbackScreen(
    openScreen: ((String) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    openImagePicker: (() -> Unit)? = null,
    showLoading: (Boolean) -> Unit
) {
    var email = ""
    var feedbackType = ""
    val value = remember { mutableStateOf("") }

    val viewModel: AddFeedBackViewModel = hiltViewModel()
    val context = LocalContext.current
    val uid = MutableStateFlow(UserDataObject())
    val imagePathOrUrl1 = remember { mutableStateOf("") }
    val imagePathOrUrl2 = remember { mutableStateOf("") }
    val imagePathOrUrl3 = remember { mutableStateOf("") }
    val imagePathOrUrl4 = remember { mutableStateOf("") }
    val imagePathOrUrl5 = remember { mutableStateOf("") }
    val imagePathOrUrl6 = remember { mutableStateOf("") }


    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            if ((it.imagePath1 ?: "") == "" || (it.imagePath2 ?: "") == "" || (it.imagePath3
                    ?: "") == "" || (it.imagePath4 ?: "") == "" || (it.imagePath5
                    ?: "") == "" || (it.imagePath6 ?: "") == ""
            ) {
                imagePathOrUrl1.value = it.imagePath1 ?: ""
                imagePathOrUrl2.value = it.imagePath2 ?: ""
                imagePathOrUrl3.value = it.imagePath3 ?: ""
                imagePathOrUrl4.value = it.imagePath4 ?: ""
                imagePathOrUrl5.value = it.imagePath5 ?: ""
                imagePathOrUrl6.value = it.imagePath6 ?: ""

            }
        }
    })

    Log.d("TAG", "FeedbackScreen:70 " + image1.observeAsState().value)
    Log.d("TAG", "FeedbackScreen:71 " + image2.value)
    Log.d("TAG", "FeedbackScreen:72 " + image3.value)
    Log.d("TAG", "FeedbackScreen:73 " + image4.value)
    Log.d("TAG", "FeedbackScreen:74 " + image5.value)
    Log.d("TAG", "FeedbackScreen:75 " + image6.value)



    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), content = {

        /*Common.SettingTopBar("Contact Us", R.drawable.notepad, onClick = {
            openScreen?.invoke(Route.FEEDBACK_RECORDS_BASE)
            //popBack?.invoke()

        })*/
        TobBar(popBack,openScreen)

        Column(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInEditText(
                "Enter your Email",
                CustomInputType.Email, onDataChanged = { str ->
                    email = str
                }
            )
        }


        //AddSpace(15.dp)
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInEditText(
                "Select feedback type",
                CustomInputType.Name,
                onDataChanged = { feedback ->
                    feedbackType = feedback
                }
            )
        }


        AddSpace(15.dp)
        DescriptionDesign(value)
        AddSpace(15.dp)
        AddScreenshotsDesign(openImagePicker)

        Box(
            modifier = Modifier
                .padding(top = 50.dp, bottom = 30.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            SolidButton(
                "Submit", poppinsMedium,
                onclick = {

                    showLoading.invoke(true)
                    viewModel.addFeedBackApi(
                        context,
                        email,
                        feedbackType,
                        value.value,
                        image1.value ?: "",
                        image2.value ?: "",
                        image3.value ?: "",
                        image4.value ?: "",
                        image5.value ?: "",
                        image6.value ?: "",
                    ) { isSuccess, errorMsg ->
                        //showLoading?.invoke(false)
                        showLoading.invoke(false)
                        if (isSuccess) {
                            viewModel.clereimage()
                            popBack?.invoke()
                        } else {
                            context.showToast(errorMsg)
                        }

                    }

                }
            )
        }
    })
}

@Composable
private fun AddScreenshotsDesign(openImagePicker: (() -> Unit)? = null) {
    val viewModel: AddFeedBackViewModel = hiltViewModel()
    val context = LocalContext.current

    val imageCount = remember { mutableStateOf("") }
    LaunchedEffect(key1 = true, block = {
        viewModel.imageCount(context, {
            imageCount.value = it
        })
    })

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = { openImagePicker?.invoke() },
            border = BorderStroke(1.dp, PearlBush),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(300.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 7.dp, bottom = 7.dp, end = 3.dp, start = 3.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (imageCount.value == "") {
                        imageCount.value
                    } else {
                        imageCount.value
                    },
                    color = Nobel,
                    fontSize = 14.sp,
                    fontFamily = poppinsRegular,
                    modifier = Modifier.weight(1f, true)
                )
                AddSpace(size = 10.dp)
                Image(
                    painter = painterResource(id = R.drawable.plus_circle),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color.Black),
                    alpha = 0.35f
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "maximum 6 attechment",
            fontSize = 12.sp,
            fontFamily = poppinsRegular,
            color = Nobel,
            modifier = Modifier.width(300.dp),
            textAlign = TextAlign.End
        )
    }

}


@Composable
private fun DescriptionDesign(value: MutableState<String>) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val isFocused = remember { mutableStateOf(false) }
        val isError = remember { mutableStateOf(false) }

        OutlinedTextField(
            isError = isError.value,
            placeholder = { Text(text = "Description") },
            value = value.value,
            onValueChange = {
                value.value = it
                isError.value = it != ""
            },
            textStyle = TextStyle(
                fontFamily = poppinsRegular, fontSize = 13.sp,
                textAlign = TextAlign.Left
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Black,
                placeholderColor = Nobel,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color =
                    if (isError.value) RichCarmine
                    else if (isFocused.value) VioletBlue
                    else Harp,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp))
                )
                .width(300.dp)
                .height(130.dp)
                .onFocusChanged {
                    isFocused.value = it.isFocused
                }
                .background(Harp, shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp)))
        )
    }

}

@Composable
private fun TobBar(
    popBack: (() -> Unit)? = null,
    openScreen: ((String) -> Unit)?
) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(vertical = 10.dp)
            .padding(start = 10.dp, end = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow), contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable { popBack?.invoke() }
        )
        AddSpace(5.dp)
        Text(
            modifier = Modifier.weight(1f, true),
            text = "Contact Us",
            fontSize = 22.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black
        )
        Image(
            painter = painterResource(id = R.drawable.notepad), contentDescription = "",
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    openScreen?.invoke(Route.FEEDBACK_RECORDS_BASE)
                }
        )


    }
}