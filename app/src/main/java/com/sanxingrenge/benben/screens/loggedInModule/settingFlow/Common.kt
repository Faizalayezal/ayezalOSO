package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.ui.theme.GainsBoro
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold

object Common {

    @Composable
    fun SettingTopBar(title: String, imageResource: Int? = null, onClick: () -> Unit) {

        Row(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onClick.invoke()
                    })

            Text(
                modifier = Modifier.weight(1f, true),
                text = title,
                fontSize = 22.sp,
                fontFamily = poppinsSemiBold,
                color = Color.Black
            )

            imageResource?.let {
                IconButton(
                    onClick = { onClick.invoke()}, modifier = Modifier
                        .size(40.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(20.dp),
                        painter = painterResource(id = it),
                        contentDescription = ""
                    )
                }
            }

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GainsBoro)
        )

    }

}