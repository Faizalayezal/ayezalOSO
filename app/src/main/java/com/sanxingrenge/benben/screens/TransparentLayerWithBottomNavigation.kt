package com.sanxingrenge.benben.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.ui.theme.poppinsMedium
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.uiComponents.AddSpace

@Composable
fun TransparentLayerWithBottomNavigation(
    openScreen: ((String) -> Unit)? = null,
) {

    val openRecoderDialog = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable {
                    openRecoderDialog.value = true
                    //  openScreen?.invoke(Route.RAGI_PROFILE_BASE,true)
                }
        )
    }

    if (openRecoderDialog.value) {
        Dialog(onDismissRequest = {
            openRecoderDialog.value = false
        }) {
            OpenPopUpContent(openScreen) {
                openRecoderDialog.value = false
            }
        }
    }
}

@Composable
fun OpenPopUpContent(
    openScreen: ((String) -> Unit)? = null, dismissDialog: ((Boolean) -> Unit)?
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .background(
                Color.White, RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 20.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {


        AddSpace(20.dp)
        Text(
            text = "Update Profile",
            fontSize = 20.sp,
            fontFamily = poppinsSemiBold,
            color = Color.Black
        )
        AddSpace(10.dp)
        Text(
            lineHeight = 25.sp,
            text = "Please update your profile to explore \n" + "application",
            fontSize = 12.sp,
            fontFamily = poppinsRegular,
            color = Color.Black
        )
        AddSpace(25.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = {
                     openScreen?.invoke(Route.RAGI_PROFILE_BASE)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Amaranth
                ),
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 70.dp),
                contentPadding = PaddingValues(vertical = 9.dp)
            ) {
                Text(
                    text = "Update Profile",
                    fontFamily = poppinsMedium,
                    fontSize = 12.sp,
                    color = White
                )
            }
        }

    }
}