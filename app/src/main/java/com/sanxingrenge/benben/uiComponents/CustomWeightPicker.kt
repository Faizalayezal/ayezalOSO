package com.sanxingrenge.benben.uiComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.constant.slideInEnterAnimation
import com.sanxingrenge.benben.constant.slideInExitAnimation
import com.sanxingrenge.benben.ui.theme.*

private const val TAG = "CustomHeightSelector"

@Preview(showBackground = true)
@Composable
fun CustomWeightPicker(onSelected:((String)->Unit)?=null) {
    val option1Name = "kg"
    val option2Name = "lbs"
    val selectedHeightIn = remember {
        mutableStateOf(option1Name)
    }
    val selectedKg = remember { mutableStateOf("0") }
    val selectedPound = remember { mutableStateOf("0") }

    onSelected?.invoke(
        when (selectedHeightIn.value) {
            option1Name -> selectedKg.value+"kg"
            option2Name -> selectedPound.value+"lbs"
            else -> ""
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Weight", fontFamily = poppinsMedium, fontSize = 15.sp, color = Color.Black)
            Row(
                modifier = Modifier
                    .border(
                        width = 0.5.dp,
                        color = Nobel,
                        shape = MaterialTheme.shapes.small.copy(
                            CornerSize(100.dp)
                        )
                    )
                    .padding(3.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = option1Name,
                    fontFamily = if (selectedHeightIn.value == option1Name) poppinsSemiBold else poppinsLight,
                    fontSize = 12.sp,
                    color = if (selectedHeightIn.value == option1Name) VioletBlue else Nobel,
                    modifier = Modifier
                        .background(
                            color = if (selectedHeightIn.value == option1Name) LavenderPinocchio else Color.Transparent,
                            shape = MaterialTheme.shapes.small.copy(
                                CornerSize(100.dp)
                            )
                        )
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                        .clickable {
                            selectedHeightIn.value = option1Name
                        },
                    textAlign = TextAlign.Center
                )
                Text(
                    text = option2Name,
                    fontFamily = if (selectedHeightIn.value == option2Name) poppinsSemiBold else poppinsLight,
                    fontSize = 12.sp,
                    color = if (selectedHeightIn.value == option2Name) VioletBlue else Nobel,
                    modifier = Modifier
                        .background(
                            color = if (selectedHeightIn.value == option2Name) LavenderPinocchio else Color.Transparent,
                            shape = MaterialTheme.shapes.small.copy(
                                CornerSize(100.dp)
                            )
                        )
                        .clickable {
                            selectedHeightIn.value = option2Name
                        }
                        .padding(horizontal = 10.dp, vertical = 2.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        AddSpace(10.dp)

        val sliderPositionKg = remember { mutableStateOf(0f) }
        val sliderPositionPound = remember { mutableStateOf(0f) }

        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedHeightIn.value == option1Name,
                enter = slideInEnterAnimation(),
                exit = slideInExitAnimation()
            ) {
                val minWeight = 30
                val maxWeight = 150

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomSlider(
                        value = sliderPositionKg.value,
                        onValueChange = {
                            sliderPositionKg.value = it
                            Log.d(TAG, "CustomWeightPicker: testflow>>" + sliderPositionKg.value)
                        }, colors = SliderDefaults.colors(
                            disabledActiveTrackColor = Harp,
                            inactiveTrackColor = Harp,
                        ),
                        modifier = Modifier.fillMaxWidth(0.90f)
                    )

                    selectedKg.value = (((maxWeight - minWeight) * sliderPositionKg.value) + minWeight).toInt()
                        .toString()
                    Text(
                        text = selectedKg.value,
                        fontSize = 15.sp,
                        fontFamily = poppinsBold,
                        color = VioletBlue,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedHeightIn.value == option2Name,
                enter = slideInEnterAnimation(),
                exit = slideInExitAnimation()
            ) {
                val minWeight = 65
                val maxWeight = 330
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomSlider(
                        value = sliderPositionPound.value,
                        onValueChange = {
                            sliderPositionPound.value = it
                            Log.d(TAG, "CustomWeightPicker: testflow>>" + sliderPositionPound.value)
                        }, colors = SliderDefaults.colors(
                            disabledActiveTrackColor = Harp,
                            inactiveTrackColor = Harp,
                        ),
                        modifier = Modifier.fillMaxWidth(0.90f)
                    )

                    selectedPound.value = (((maxWeight - minWeight) * sliderPositionPound.value) + minWeight).toInt().toString()

                    Text(
                        text = selectedPound.value,
                        fontSize = 15.sp,
                        fontFamily = poppinsBold,
                        color = VioletBlue,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }


    }

}