package com.sanxingrenge.benben.uiComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold

@ExperimentalMaterialApi
@Composable
fun HeightWeightPicker(
    selectedWeight: String,
    selectedHeight: String,
    onSaveClicked: (date: String,data:String) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .heightIn(minBottomSheetHeight, maxBottomSheetHeight)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {

            val selectedWeight = remember {
                mutableStateOf(selectedWeight)
            }

            val selectedHeight = remember {
                mutableStateOf(selectedHeight)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Height - Weight",
                    color = Color.Black,
                    fontFamily = poppinsBold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Save",
                    color = Amaranth,
                    fontFamily = poppinsSemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        onSaveClicked.invoke(selectedWeight.value,selectedHeight.value)
                    }
                )
            }
            AddSpace(50.dp)

            CustomHeightSelector(){
                selectedHeight.value=it

            }
            CustomWeightPicker(){
                selectedWeight.value=it
            }

        }
    }

}
