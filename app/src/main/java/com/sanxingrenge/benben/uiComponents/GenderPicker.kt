package com.sanxingrenge.benben.uiComponents

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun GenderPicker(
    selectedGenderStr: String,
    onSaveClicked: (date: String) -> Unit
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
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {

            val selectedGender = remember {
                mutableStateOf(selectedGenderStr)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gender",
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
                        onSaveClicked.invoke(selectedGender.value)
                    }
                )
            }
            AddSpace(50.dp)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(280.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { selectedGender.value = "Male" }) {
                        Image(
                            painter = painterResource(id = R.drawable.boy),
                            contentDescription = "",
                            Modifier
                                .border(
                                    width = 1.dp,
                                    if (selectedGender.value.equals(
                                            "male",
                                            true
                                        )
                                    ) VioletBlue else QuillGrey,
                                    shape = CircleShape
                                )
                                .size(90.dp)
                                .padding(20.dp),
                        )
                    }
                    AddSpace(15.dp)
                    Text(
                        text = "Male",
                        fontFamily = poppinsRegular,
                        fontSize = 15.sp,
                        color = if (selectedGender.value.equals(
                                "male",
                                true
                            )
                        ) VioletBlue else QuillGrey
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { selectedGender.value = "Female" }) {
                        Image(
                            painter = painterResource(id = R.drawable.girl),
                            contentDescription = "",
                            Modifier
                                .border(
                                    width = 1.dp,
                                    if (selectedGender.value.equals(
                                            "female",
                                            true
                                        )
                                    ) VioletBlue else QuillGrey,
                                    shape = CircleShape
                                )
                                .size(90.dp)
                                .padding(20.dp),

                            )
                    }
                    AddSpace(15.dp)
                    Text(
                        text = "Female",
                        fontFamily = poppinsRegular,
                        fontSize = 15.sp,
                        color = if (selectedGender.value.equals(
                                "female",
                                true
                            )
                        ) VioletBlue else QuillGrey
                    )
                    AddSpace(size = 60.dp)
                }

            }

        }
    }

}
