package com.sanxingrenge.benben.uiComponents

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.*
import com.sanxingrenge.benben.responseModel.AllListItem
import com.sanxingrenge.benben.ui.theme.*

val maxBottomSheetHeight = 500.dp
val minBottomSheetHeight = 100.dp
val shimmerRotation = 90.0f
private const val TAG = "Basic"

@Composable
fun ListAnimator(listDesign: @Composable () -> Unit) {
    val showNow = remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = showNow.value,
        enter = slideInVertically(), exit = ExitTransition.None
    ) {
        listDesign.invoke()
    }
    LaunchedEffect(key1 = true, block = {
        showNow.value = true
    })
}

@Composable
fun AddSpace(size: Dp) {
    Spacer(modifier = Modifier.size(size))
}

@Composable
fun AddImage(intImage: Int, size: Dp, alignment: Alignment = Alignment.Center) {
    Image(
        painter = painterResource(id = intImage),
        contentDescription = "",
        modifier = Modifier
            .size(size),
        contentScale = ContentScale.Fit,
        alignment = alignment
    )
}

@Composable
fun AddImage(imgUrl: String, size: Dp, alignment: Alignment = Alignment.Center) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl).crossfade(true).build(),
        contentDescription = "",
        modifier = Modifier
            .size(size),
        contentScale = ContentScale.Fit,
        alignment = alignment
    )
}

enum class CustomInputType { Email, Pwd, PwdC, Name,Npwd,Cpwd}
sealed class SelectedItem {
    class ListItem(val data: MutableState<AllListItem?>?) : SelectedItem()
    class StringItem(val data: MutableState<String?>?) : SelectedItem()
}

@Composable
fun SignInClickableText(
    hint: String,
    selectedDate: String? = null,
    showAllError: MutableState<Boolean>? = null,
    isValidData: ((isError: Boolean, str: String) -> Unit)? = null,
    onDataChanged: (String) -> Unit,
    onclick: () -> Unit,
) {
    Log.d(TAG, "SignInClickableText: testBdAY>1>${selectedDate}>>${hint}")
    val errorMsg = remember { mutableStateOf("") }
    val isError = remember { mutableStateOf(false) }
    val isFocused = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val liveSelectedData = remember {
        mutableStateOf("")
    }
    selectedDate?.let {
        liveSelectedData.value = it
    }
    Log.d(TAG, "SignInClickableText: testBdAY>2>${liveSelectedData.value}>>${hint}")
    if (selectedDate != liveSelectedData.value) onDataChanged.invoke(liveSelectedData.value)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            readOnly = true,
            enabled = false,
            isError = isError.value,
            placeholder = { Text(text = hint) },
            value = liveSelectedData.value,
            onValueChange = {
                liveSelectedData.value = it
            },
            singleLine = true,
            textStyle = TextStyle(fontFamily = poppinsRegular, fontSize = 13.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Black,
                disabledTextColor = Color.Black,
                disabledPlaceholderColor = Nobel,
                placeholderColor = Nobel,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color =
                    if (isError.value) RichCarmine
                    else if (isFocused.value) VioletBlue
                    else Harp,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp))
                )
                .width(300.dp)
                .onFocusChanged {
                    isFocused.value = it.isFocused
                }
                .customBgGreyRounded()
                .clickable {
                    focusManager.clearFocus()
                    onclick.invoke()
                },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.right_arrow),
                    "",
                    Modifier.size(20.dp)
                )
            },
        )
    }
    LaunchedEffect(key1 = liveSelectedData.value, key2 = showAllError?.value, block = {
        isError.value = if ((liveSelectedData.value ?: "") == "" && showAllError?.value != true) {
            errorMsg.value = ""
            false
        } else if ((liveSelectedData.value ?: "").isEmpty()) {
            errorMsg.value = "$PLEASE_SELECT$hint."
            true
        } else false

        isValidData?.invoke(isError.value, liveSelectedData.value)
    })

    AnimatedTextDesign(isError.value, errorMsg.value)
}

private fun Modifier.customBgGreyRounded(): Modifier = composed {
    background(Harp, shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp)))
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedTextDesign(isError: Boolean, errorMsg: String) {
    val animationDuration = 500
    AnimatedVisibility(
        visible = isError,
        enter = expandIn(animationSpec = tween(durationMillis = animationDuration)),
        exit = shrinkOut(animationSpec = tween(durationMillis = animationDuration))
    ) {
        AnimatedContent(targetState = errorMsg) { target ->
            Text(text = target, color = RichCarmine, modifier = Modifier.width(300.dp))
        }
    }
}

@Composable
fun SignInEditText(
    hintt: String, inputType: CustomInputType, isEnabled: Boolean = true,
    showAllError: MutableState<Boolean>? = null,
    selectedText: String? = null,
    onDataChanged: (String) -> Unit,
    isValidData: ((isError: Boolean, str: String) -> Unit)? = null
) {

    val isError = remember { mutableStateOf(false) }
    val liveSelectedText = remember { mutableStateOf(selectedText ?: "") }
    val errorMsg = remember { mutableStateOf("") }
    onDataChanged.invoke(liveSelectedText.value)
    val keyboardOption = when (inputType) {
        CustomInputType.Email -> {
            KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done)
        }
        CustomInputType.Pwd -> {
            KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        }
        CustomInputType.PwdC -> {
            KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        }
        CustomInputType.Npwd -> {
            KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        }
        CustomInputType.Cpwd -> {
            KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        }
        CustomInputType.Name -> {
            KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
        }

    }
    val passwordVisible = remember { mutableStateOf(false) }

    val tempState =
        if (inputType == CustomInputType.Pwd) PasswordVisualTransformation() else VisualTransformation.None
        if (inputType == CustomInputType.PwdC) PasswordVisualTransformation() else VisualTransformation.None
    if (inputType == CustomInputType.Cpwd) PasswordVisualTransformation() else VisualTransformation.None
    if (inputType == CustomInputType.Npwd) PasswordVisualTransformation() else VisualTransformation.None

    val visualTransformation = remember {
        mutableStateOf(
            tempState
        )
    }

    if (inputType == CustomInputType.Pwd && !passwordVisible.value) {
        visualTransformation.value = PasswordVisualTransformation()
    }else if(inputType == CustomInputType.PwdC && !passwordVisible.value){
        visualTransformation.value = PasswordVisualTransformation()
    }else if(inputType == CustomInputType.Npwd && !passwordVisible.value){
        visualTransformation.value = PasswordVisualTransformation()
    }else if(inputType == CustomInputType.Cpwd && !passwordVisible.value){
        visualTransformation.value = PasswordVisualTransformation()
    }
    else{
        visualTransformation.value = VisualTransformation.None
    }

    val isFocused = remember { mutableStateOf(false) }

    OutlinedTextField(
        visualTransformation = visualTransformation.value,
        isError = isError.value,
        placeholder = { Text(text = hintt) },
        value = liveSelectedText.value,
        onValueChange = {
            liveSelectedText.value = it
        },
        enabled = isEnabled,
        keyboardOptions = keyboardOption,
        singleLine = true,
        textStyle = TextStyle(fontFamily = poppinsRegular, fontSize = 13.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            placeholderColor = Nobel,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            disabledTextColor = Color.Black,
            disabledPlaceholderColor = Color.Black,
            disabledBorderColor = Color.Transparent
        ),
        modifier = Modifier
            .border(
                width = 1.dp,
                color =
                if (isError.value) RichCarmine
                else if (isFocused.value) VioletBlue
                else Harp,
                shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp))
            )
            .width(300.dp)
            .onFocusChanged {
                isFocused.value = it.isFocused
            }
            .customBgGreyRounded(),
        trailingIcon = {
            if (inputType == CustomInputType.Pwd) {

                val imageId = if (passwordVisible.value)
                    R.drawable.pwd_unhide
                else R.drawable.pwd_hide

                // Please provide localized description for accessibility services
                val description =
                    if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(id = imageId), description,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
            if (inputType == CustomInputType.PwdC) {

                val imageId = if (passwordVisible.value)
                    R.drawable.pwd_unhide
                else R.drawable.pwd_hide

                // Please provide localized description for accessibility services
                val description =
                    if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(id = imageId), description,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
            if (inputType == CustomInputType.Npwd) {

                val imageId = if (passwordVisible.value)
                    R.drawable.pwd_unhide
                else R.drawable.pwd_hide

                // Please provide localized description for accessibility services
                val description =
                    if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(id = imageId), description,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
        },
    )

    LaunchedEffect(key1 = liveSelectedText.value, key2 = showAllError?.value, block = {
        isError.value = when (inputType) {
            CustomInputType.Email -> {
                if (liveSelectedText.value == "" && showAllError?.value != true) {
                    errorMsg.value = ""
                    false
                } else if (liveSelectedText.value == "") {
                    errorMsg.value = ENTER_EMAIL
                    true
                } else if (liveSelectedText.value.length < 3) {
                    errorMsg.value = EMAIL_TOO_SHORT
                    true
                } else if (!isValidEmailId(liveSelectedText.value)) {
                    errorMsg.value = EMAIL_NOT_VALID
                    true
                } else false
            }

            CustomInputType.Pwd -> {
                if (liveSelectedText.value == "" && showAllError?.value != true) {
                    errorMsg.value = ""
                    false
                } else if (liveSelectedText.value == "") {
                    errorMsg.value = ENTER_PWD
                    true
                } else if (liveSelectedText.value.length < 6) {
                    errorMsg.value = PWD_TOO_SHORT
                    true
                } else false
            }
            CustomInputType.PwdC -> {
                if (liveSelectedText.value == "" && showAllError?.value != true) {
                    errorMsg.value = ""
                    false
                } else if (liveSelectedText.value == "") {
                    errorMsg.value = ENTER_PWDc
                    true
                } else if (liveSelectedText.value.length < 6) {
                    errorMsg.value = PWD_TOO_SHORT
                    true
                } else false
            }
            CustomInputType.Npwd -> {
                if (liveSelectedText.value == "" && showAllError?.value != true) {
                    errorMsg.value = ""
                    false
                } else if (liveSelectedText.value == "") {
                    errorMsg.value = ENTER_NPWDc
                    true
                } else if (liveSelectedText.value.length < 6) {
                    errorMsg.value = PWD_TOO_SHORT
                    true
                } else false
            }
            CustomInputType.Cpwd -> {
                if (liveSelectedText.value == "" && showAllError?.value != true) {
                    errorMsg.value = ""
                    false
                } else if (liveSelectedText.value == "") {
                    errorMsg.value = ENTER_CPWD
                    true
                } else if (liveSelectedText.value.length < 6) {
                    errorMsg.value = PWD_TOO_SHORT
                    true
                } else false
            }

            CustomInputType.Name -> {
                if (liveSelectedText.value == "" && showAllError?.value != true) {
                    errorMsg.value = ""
                    false
                } else if (liveSelectedText.value == "") {
                    errorMsg.value = "Please enter $hint"
                    true
                } else if (liveSelectedText.value.length < 3) {
                    errorMsg.value = hint + TOO_SHORT
                    true
                } else false
            }
        }

        isValidData?.invoke(isError.value, liveSelectedText.value)
    })

    AnimatedTextDesign(isError.value, errorMsg.value)
}


@Composable
fun OtpEditText(
    showAllError: MutableState<Boolean>? = null,
    onChanged: ((String, Boolean) -> Unit)? = null
) {
    val value = remember { mutableStateOf("") }
    val isError = remember { mutableStateOf(false) }
    val keyboardOption =
        KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done)
    val passwordVisible = remember { mutableStateOf(false) }

    val tempState: VisualTransformation =
        PasswordVisualTransformation("●".toCharArray().first())

    val visualTransformation = remember {
        mutableStateOf(
            tempState
        )
    }

    visualTransformation.value =
        if (!passwordVisible.value) PasswordVisualTransformation("●".toCharArray().first())
        else VisualTransformation.None

    val isFocused = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = value.value, key2 = showAllError?.value, block = {
        if (value.value == "" && showAllError?.value != true) isError.value = false
        else {
            isError.value = value.value.length != 6
            onChanged?.invoke(value.value, isError.value)
        }
    })

    OutlinedTextField(
        visualTransformation = visualTransformation.value,
        isError = isError.value,
        placeholder = { Text(text = "") },
        value = value.value,
        onValueChange = {
            if (it.length <= 6) {
                value.value = it
            }
        },
        keyboardOptions = keyboardOption,
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = poppinsRegular,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 11.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            placeholderColor = Nobel,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent
        ),
        modifier = Modifier
            .border(
                width = 1.dp,
                color =
                if (isError.value) RichCarmine
                else if (isFocused.value) VioletBlue
                else Harp,
                shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp))
            )
            .width(300.dp)
            .onFocusChanged {
                isFocused.value = it.isFocused
            }
            .customBgGreyRounded(),
    )

}

@Composable
fun CustomSignUpText(itemClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = signUpHelpCondition,
            onClick = { offset ->
                signUpHelpCondition.getStringAnnotations(
                    tag = "UserClicked",
                    start = offset,
                    end = offset
                ).getOrNull(0)?.let { _ ->
                    //do your stuff when it gets clicked
                    itemClicked.invoke()
                }
            },
        )
    }
}

@Composable
fun CustomSignInText(itemClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = alreadyHaveAccountHelpCondition,
            onClick = { offset ->
                alreadyHaveAccountHelpCondition.getStringAnnotations(
                    tag = "UserClicked",
                    start = offset,
                    end = offset
                ).getOrNull(0)?.let { _ ->
                    //do your stuff when it gets clicked
                    itemClicked.invoke()
                }
            },
        )
    }
}

@Composable
fun SolidButton(
    text: String,
    fontFamily: androidx.compose.ui.text.font.FontFamily = poppinsRegular,
    onclick: () -> Unit
) {
    Button(
        modifier = Modifier.size(300.dp, 50.dp),
        onClick = {
            onclick.invoke()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = VioletBlue,
        )
    ) {
        Text(text = text, fontFamily = fontFamily, color = White)
    }
}

@Composable
fun SolidButton2(
    text: String,
    fontFamily: androidx.compose.ui.text.font.FontFamily = poppinsRegular,
    onclick: () -> Unit,
) {
    Button(
        modifier = Modifier.size(300.dp, 50.dp),
        onClick = {
            onclick.invoke()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = VioletBlue,
        )
    ) {
        Text(
            text = text,
            fontFamily = fontFamily,
            color = White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun WelcomeText(title: String, desc: String) {
    Text(
        modifier = Modifier
            .width(300.dp)
            .height(36.dp),
        text = title,
        fontSize = 25.sp,
        fontFamily = poppinsBold,
        color = Color.Black
    )
    Text(
        modifier = Modifier.width(300.dp),
        text = desc,
        fontSize = 15.sp,
        fontFamily = poppinsRegular,
        color = Nobel
    )

}
