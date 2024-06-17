package com.sanxingrenge.benben.screens.loginRegisterModule

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.showToast
import com.sanxingrenge.benben.ui.theme.Harp
import com.sanxingrenge.benben.ui.theme.Nobel
import com.sanxingrenge.benben.ui.theme.RichCarmine
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.uiComponents.AddSpace


@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun ImagePickerDesign(openImagePicker: (() -> Unit)? = null) {

    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imageFromLocalPath = remember { mutableStateOf("") }

    isError.value = imageFromLocalPath.value == ""

    LaunchedEffect(key1 = true, block = {
        context.dataStoreGetUserData().collect {
            imageFromLocalPath.value = it.imagePath ?: ""
        }
    })

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WelcomeText("Add your photo")
        AddSpace(size = 10.dp)
        Text(
            text = "Get more people to know you better",
            fontFamily = poppinsRegular,
            fontSize = 14.sp,
            color = Nobel
        )
        AddSpace(size = 90.dp)
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(
                    color = Harp, shape = MaterialTheme.shapes.small.copy(CornerSize(15.dp))
                ), contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.camera_black),
                contentDescription = "",
                alpha = 0.35F,
                modifier = Modifier.size(25.dp)
            )

            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(imageFromLocalPath.value).crossfade(true).build(),
                contentDescription = "",
                alpha = 1.00F,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        openImagePicker?.invoke()
                    }
                    .clip(MaterialTheme.shapes.small.copy(CornerSize(15.dp))))
        }
        val tempError = remember {
            mutableStateOf("")
        }
        AnimatedContent(targetState = tempError.value) { target ->
            Text(
                text = target,
                color = RichCarmine,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }


        LaunchedEffect(key1 = isError.value, block = {
            tempError.value = "Please select profile image."
        })
        AddSpace(size = 90.dp)
    }
}