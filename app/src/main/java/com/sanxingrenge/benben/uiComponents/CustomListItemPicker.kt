package com.sanxingrenge.benben.uiComponents

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.responseModel.AllListItem
import com.sanxingrenge.benben.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun CustomListItemPicker(
    selectedCountryStr: String,
    title: String,
    strList: List<String>,
    onSaveClicked: (date: String) -> Unit
) {
    Log.d("TAG", "CustomListItemPickersdf: "+selectedCountryStr)
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
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {

            val selectedCountry = remember {
                mutableStateOf(selectedCountryStr)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
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
                        onSaveClicked.invoke(selectedCountry.value)
                    }
                )
            }
            AddSpace(50.dp)

            LazyColumn(modifier = Modifier
                .heightIn(100.dp, 400.dp)
                .width(300.dp),
                content = {
                    items(strList.size) { pos ->

                        val itemData = strList[pos]

                        val isCurrentSelected = remember {
                            mutableStateOf(false)
                        }

                        isCurrentSelected.value = selectedCountry.value == itemData

                        CustomChip(
                            data = itemData,
                            isSelected = isCurrentSelected.value,
                            onSelectionChanged = {
                                it?.let {
                                    selectedCountry.value = it
                                }
                            },
                        )

                    }
                })

        }
    }

}

@Composable
fun CustomChip(
    data: String?,
    isSelected: Boolean = false,
    onSelectionChanged: (String?) -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(100.dp),
        color = if (isSelected) VioletBlue else Seashell
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(data)
                }
            )
        ) {
            Text(
                text = data?: "",
                fontFamily = if (isSelected) poppinsSemiBold else poppinsMedium,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected) Color.White else SilverChalice,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}