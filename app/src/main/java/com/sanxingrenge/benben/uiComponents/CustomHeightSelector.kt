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
fun CustomHeightSelector(onSelected:((String)->Unit)?=null) {

    val option1Name = "cm"
    val option2Name = "ft"

    val selectedHeightIn = remember {
        mutableStateOf(option1Name)
    }
    val selectedCm = remember { mutableStateOf("0") }
    val selectedFt = remember { mutableStateOf("0") }
    val (feet, inches) = calculateFeetAndInches(selectedFt.value.toIntOrNull()?:0)

    onSelected?.invoke(
        when (selectedHeightIn.value) {
            option1Name -> selectedCm.value+"cm"
            option2Name ->"$feet'$inches"+"ft"
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
            Text(text = "Height", fontFamily = poppinsMedium, fontSize = 15.sp, color = Color.Black)
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

        val sliderPositionCm = remember { mutableStateOf(0f) }
        val sliderPositionFt = remember { mutableStateOf(0f) }

        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedHeightIn.value == option1Name,
                enter = slideInEnterAnimation(),
                exit = slideInExitAnimation()
            ) {
                val minWeight = 90
                val maxWeight = 220


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomSlider(
                        value = sliderPositionCm.value,
                        onValueChange = {
                            sliderPositionCm.value = it
                            Log.d(TAG, "CustomHeightPicker: testflow>>" + sliderPositionCm.value)
                        }, colors = SliderDefaults.colors(
                            disabledActiveTrackColor = Harp,
                            inactiveTrackColor = Harp,
                        ),
                        modifier = Modifier.fillMaxWidth(0.90f)
                    )

                    selectedCm.value = (((maxWeight - minWeight) * sliderPositionCm.value) + minWeight).toInt()
                        .toString()
                    Text(
                        text = selectedCm.value,
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
                val minWeight = 36
                val maxWeight = 84

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomSlider(
                        value = sliderPositionFt.value,
                        onValueChange = {
                            sliderPositionFt.value = it
                            Log.d(TAG, "CustomWeightPicker: testflow>>" + sliderPositionFt.value)
                        }, colors = SliderDefaults.colors(
                            disabledActiveTrackColor = Harp,
                            inactiveTrackColor = Harp,
                        ),
                        modifier = Modifier.fillMaxWidth(0.90f)
                    )

                    selectedFt.value = (((maxWeight - minWeight) * sliderPositionFt.value) + minWeight).toInt().toString()

                    Log.d(TAG, "CustomHeightSelectordsdsf:"+selectedFt.value)
                    Log.d(TAG, "CustomHeightSelector:ssdf     "+"Height: $feet feet $inches inches")
                    Text(
                        text = "$feet'$inches",
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

fun calculateFeetAndInches(heightInInches: Int): Pair<Int, Int> {
    val feet = heightInInches / 12
    val inches = heightInInches % 12
    return Pair(feet, inches)
}

/*val sliderPositionCm = remember { mutableStateOf(0f) }
    val sliderPositionFt = remember { mutableStateOf(0f) }

    val startPointInCm = 90
    val endPointInCm = 220

    val startPointInFt = 3
    val endPointInFt = 7

    val option1Name = "cm"
    val option2Name = "ft"
    val selectedHeightIn = remember {
        mutableStateOf(option1Name)
    }

    val selectedHeightValue = remember {
        mutableStateOf(when (selectedHeightIn.value) {
            option1Name -> {
                val diff = endPointInCm - startPointInCm

                ((((sliderPositionCm.value * diff) + startPointInCm)).toInt()).toString()

            }
            option2Name -> {
                val diff = endPointInFt - startPointInFt

                ((((sliderPositionFt.value * diff) + startPointInFt) * 10.0).roundToInt() / 10.0).toString()
            }
            else -> ""
        })
    }

    val new = selectedHeightValue.value+selectedHeightIn.value

    if(selectedData!=new) onSelected?.invoke(new)

    selectedData = new

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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Height",
                    fontFamily = poppinsMedium,
                    fontSize = 15.sp,
                    color = Color.Black
                )
                AddSpace(size = 20.dp)
                Text(
                    text = selectedHeightValue.value,
                    fontFamily = poppinsSemiBold,
                    fontSize = 12.sp,
                    color = VioletBlue,
                )
            }

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

        AddSpace(20.dp)

        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedHeightIn.value == option1Name,
                enter = slideInEnterAnimation(),
                exit = slideInExitAnimation()
            ) {

                val selectedPosCm = remember { mutableStateOf(13) }
                val pointSize = (endPointInCm - startPointInCm) / 5

                Box(contentAlignment = Alignment.BottomCenter) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            var newText = startPointInCm
                            while (newText < endPointInCm) {

                                Text(
                                    text = "$newText",
                                    color = VioletBlue,
                                    fontSize = 12.sp,
                                    modifier = Modifier.width(25.dp)
                                )

                                newText += 20
                            }

                        }
                        AddSpace(size = 20.dp)
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (i in 0 until pointSize) {
                                if (i % 2 == 0)
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .width(1.dp)
                                            .background(if (selectedPosCm.value == i) VioletBlue else Nobel)
                                    )
                                else Box(
                                    modifier = Modifier
                                        .height(23.dp)
                                        .width(1.dp)
                                        .background(if (selectedPosCm.value == i) VioletBlue else Nobel)
                                )
                            }
                        }
                    }
                    Slider(value = sliderPositionCm.value, onValueChange = {
                        sliderPositionCm.value = it
                        selectedPosCm.value = (pointSize * it).toInt()
                        Log.d(
                            TAG,
                            "CustomHeightSelector: testItemsListed>>" + selectedPosCm.value + ">>" + pointSize + ">>$it"
                        )
                    }, Modifier.alpha(0f))
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = selectedHeightIn.value == option2Name,
                enter = slideInEnterAnimation(),
                exit = slideInExitAnimation()
            ) {
                val pointSize = (endPointInFt - startPointInFt) * 10

                val selectedPosFt = remember { mutableStateOf(5) }
                Box(contentAlignment = Alignment.BottomCenter) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            var newText = startPointInFt.toDouble()
                            while (newText < endPointInFt) {

                                Text(
                                    text = "$newText",
                                    color = VioletBlue,
                                    fontSize = 12.sp,
                                    modifier = Modifier.width(25.dp)
                                )

                                newText += 0.5
                            }

                        }
                        AddSpace(size = 20.dp)
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (i in 0 until pointSize) {
                                if (i % 2 == 0)
                                    Box(
                                        modifier = Modifier
                                            .height(15.dp)
                                            .width(1.dp)
                                            .background(if (selectedPosFt.value == i) VioletBlue else Nobel)
                                    )
                                else Box(
                                    modifier = Modifier
                                        .height(23.dp)
                                        .width(1.dp)
                                        .background(if (selectedPosFt.value == i) VioletBlue else Nobel)
                                )
                            }
                        }
                    }
                    Slider(value = sliderPositionFt.value, onValueChange = {
                        sliderPositionFt.value = it
                        selectedPosFt.value = (pointSize * it).toInt()
                        Log.d(
                            TAG,
                            "CustomHeightSelector: testItemsListed>>" + selectedPosFt.value + ">>" + pointSize + ">>$it"
                        )
                    }, Modifier.alpha(0f))
                }
            }
        }

    }*/