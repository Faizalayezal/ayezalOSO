package com.sanxingrenge.benben.uiComponents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.FBName
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.constant.dataStoreSetUserData
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun CustomTextInputPicker(
    selectedCountryStr: String,
    placeholder: String,
    title: String,
    onSaveClicked: (date: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context= LocalContext.current
    var userObj: UserDataObject?=null
    CoroutineScope(Dispatchers.IO).launch {
        userObj = context.dataStoreGetUserData().firstOrNull() ?: return@launch
    }
    Log.d("TAG", "CustomTextInputPicker: " + selectedCountryStr)
    Log.d("TAG", "CustomTextInputPicker:577 " + userObj?.facebook)
    Log.d("TAG", "CustomTextInputPicker:5999 " + FBName)
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

            val selectedCountry =
                remember { mutableStateOf(if (selectedCountryStr == "Enter Name" || selectedCountryStr == "Enter Occupation" || selectedCountryStr == "Enter Bio") "" else selectedCountryStr) }
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
                    color = if(title=="Bio" || title=="Occupation" || userObj?.facebook==null || userObj?.facebook=="" ) Amaranth else Amaranth2,
                    fontFamily = poppinsSemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        if (selectedCountry.value == "") {
                            keyboardController?.hide()
                        } else {
                            onSaveClicked.invoke(selectedCountry.value)
                            keyboardController?.hide()
                        }
                    }
                )
            }
            AddSpace(15.dp)

            Log.d("TAG", "CustomTextInputPicker:86 "+FBName)
            if (title=="Bio" || title=="Occupation" || userObj?.facebook==null || userObj?.facebook=="") {
                BasicTextField(value = selectedCountry.value, onValueChange = {
                    /* try{
                        Log.d("TAG", "CustomTextInputPicker:81 "+it)
                        Log.d("TAG", "CustomTextInputPicker: 82"+it.last().toString())
                         if (it.first().toString() == "a" || it.last().toString() == "b" || it.last().toString() == "c" || it.last().toString() == "d" || it.last().toString() == "e" || it.last().toString() == "f" || it.last().toString() == "g" || it.last().toString() == "h" || it.last().toString() == "i" || it.last().toString() == "j" || it.last().toString() == "k" || it.last().toString() == "l" || it.last().toString() == "m" || it.last().toString() == "n" || it.last().toString() == "o" || it.last().toString() == "p" || it.last().toString() == "q" || it.last().toString() == "r" || it.last().toString() == "s" || it.last().toString() == "t" || it.last().toString() == "u" || it.last().toString() == "v" || it.last().toString() == "w" || it.last().toString() == "x" || it.last().toString() == "y" || it.last().toString() == "z" || it.last().toString() == "A" || it.last().toString() == "B" || it.last().toString() =="C" ||it.last().toString() == "D" ||it.last().toString() == "E" ||it.last().toString() == "F" || it.last().toString() =="G" || it.last().toString() =="H" ||it.last().toString() == "I" ||it.last().toString() == "J" ||it.last().toString() == "K" ||it.last().toString() == "L" ||it.last().toString() =="M" ||it.last().toString() == "N" ||it.last().toString() == "O" ||it.last().toString() == "P" ||it.last().toString() == "Q" ||it.last().toString() == "R" || it.last().toString() =="S" ||it.last().toString() == "T" ||it.last().toString() == "U" || it.last().toString() =="V" ||it.last().toString() == "W" ||it.last().toString() == "X" || it.last().toString() =="Y" ||it.last().toString() == "Z") {
                             selectedCountry.value = it
                         }else{
                             Log.d("TAG", "CustomTextInputPicker: "+it.last().toString())
                         }
                     }catch (e:Exception){e.printStackTrace()}*/

                    selectedCountry.value = it

                },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 14.sp,
                        fontFamily = poppinsMedium,
                        color = Color.Black,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 10.dp)
                        .fillMaxWidth()
                        .background(Harp, RoundedCornerShape(10.dp))
                        .padding(horizontal = 17.dp, vertical = 13.dp),
                    decorationBox = { innerTextField ->
                        Box() {
                            if (selectedCountry.value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            } else {
                BasicTextField(value = selectedCountry.value, enabled = false, onValueChange = {
                    /* try{
                        Log.d("TAG", "CustomTextInputPicker:81 "+it)
                        Log.d("TAG", "CustomTextInputPicker: 82"+it.last().toString())
                         if (it.first().toString() == "a" || it.last().toString() == "b" || it.last().toString() == "c" || it.last().toString() == "d" || it.last().toString() == "e" || it.last().toString() == "f" || it.last().toString() == "g" || it.last().toString() == "h" || it.last().toString() == "i" || it.last().toString() == "j" || it.last().toString() == "k" || it.last().toString() == "l" || it.last().toString() == "m" || it.last().toString() == "n" || it.last().toString() == "o" || it.last().toString() == "p" || it.last().toString() == "q" || it.last().toString() == "r" || it.last().toString() == "s" || it.last().toString() == "t" || it.last().toString() == "u" || it.last().toString() == "v" || it.last().toString() == "w" || it.last().toString() == "x" || it.last().toString() == "y" || it.last().toString() == "z" || it.last().toString() == "A" || it.last().toString() == "B" || it.last().toString() =="C" ||it.last().toString() == "D" ||it.last().toString() == "E" ||it.last().toString() == "F" || it.last().toString() =="G" || it.last().toString() =="H" ||it.last().toString() == "I" ||it.last().toString() == "J" ||it.last().toString() == "K" ||it.last().toString() == "L" ||it.last().toString() =="M" ||it.last().toString() == "N" ||it.last().toString() == "O" ||it.last().toString() == "P" ||it.last().toString() == "Q" ||it.last().toString() == "R" || it.last().toString() =="S" ||it.last().toString() == "T" ||it.last().toString() == "U" || it.last().toString() =="V" ||it.last().toString() == "W" ||it.last().toString() == "X" || it.last().toString() =="Y" ||it.last().toString() == "Z") {
                             selectedCountry.value = it
                         }else{
                             Log.d("TAG", "CustomTextInputPicker: "+it.last().toString())
                         }
                     }catch (e:Exception){e.printStackTrace()}*/

                    selectedCountry.value = it

                },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 14.sp,
                        fontFamily = poppinsMedium,
                        color = Color.Black,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 10.dp)
                        .fillMaxWidth()
                        .background(Harp, RoundedCornerShape(10.dp))
                        .padding(horizontal = 17.dp, vertical = 13.dp),
                    decorationBox = { innerTextField ->
                        Box() {
                            if (selectedCountry.value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            AddSpace(120.dp)
        }
    }

}

