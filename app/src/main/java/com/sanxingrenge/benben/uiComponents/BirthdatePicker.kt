package com.sanxingrenge.benben.uiComponents

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.DatePicker
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
import androidx.compose.ui.viewinterop.AndroidView
import com.sanxingrenge.benben.ui.theme.Amaranth
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.poppinsBold
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold

private const val TAG = "BirthdatePicker"
@SuppressLint("InflateParams")
@ExperimentalMaterialApi
@Composable
fun BirthdatePicker(
    selectedBirthdate: String,
    //onSaveClicked: (date: String,year:String,month:Int,day:String) -> Unit
    onSaveClicked: (date: String,month:Int,day:String,year:String) -> Unit
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


            val selectedDate = remember {
                mutableStateOf(selectedBirthdate)
            }
            val selectedYears = remember {
                mutableStateOf("")
            }
            val selectedMonth = remember {
                mutableStateOf(0)
            }
            val selectedDay = remember {
                mutableStateOf("")
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Birthdate",
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
                        Log.d(TAG, "BirthdatePicker: SAVE CLICKED>>${selectedDate.value}")
                       // onSaveClicked.invoke(selectedDate.value,selectedYears.value,selectedMonth.value,selectedDay.value)
                        onSaveClicked.invoke(selectedDate.value,selectedMonth.value,selectedDay.value,selectedYears.value)
                    }
                )
            }
            AddSpace(50.dp)
            AndroidView(
                factory = { context ->
                    val view = LayoutInflater.from(context)
                        .inflate(com.sanxingrenge.benben.R.layout.date_picker_layout, null, false)
                    val datePicker =
                        view.findViewById<DatePicker>(com.sanxingrenge.benben.R.id.datePicker1)

                    datePicker.maxDate =
                        System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 365 * 18)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->

                            val monthName = when (monthOfYear) {
                                0 -> "Jan"
                                1 -> "Feb"
                                2 -> "Mar"
                                3 -> "Apr"
                                4 -> "May"
                                5 -> "Jun"
                                6 -> "Jul"
                                7 -> "Aug"
                                8 -> "Sep"
                                9 -> "Oct"
                                10 -> "Nov"
                                11 -> "Dec"
                                else -> ""
                            }
                            Log.d(TAG, "BirthdatePicker132: "+monthOfYear)

                           // selectedDate.value = "$dayOfMonth $monthName $year"
                           // selectedDate.value = "$monthName $dayOfMonth $year"
                            //mdy
                            selectedDate.value = "${monthOfYear.plus(1)}-$dayOfMonth-$year"
                            selectedMonth.value = monthOfYear
                            selectedDay.value = "$dayOfMonth"
                            selectedYears.value = "$year"

                        }
                    }

                    view
                },
                update = {

                }
            )
            AddSpace(25.dp)
            Text(
                text = selectedDate.value,
                color = VioletBlue,
                fontFamily = poppinsSemiBold,
                fontSize = 20.sp
            )
            AddSpace(30.dp)
        }
    }

}