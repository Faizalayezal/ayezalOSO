package com.sanxingrenge.benben.screens.loggedInModule

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.backepersonality
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.viewModel.PersonalityTestViewModel

@Preview
@Composable
fun MyPersonalityScreen(selectedPos: String = "2", popBack: (() -> Unit)? = null) {
    val viewModel: PersonalityTestViewModel = hiltViewModel()

    val txtData = remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(key1 = true, block = {

        viewModel.PersonalityDataApi { list ->

            var desc: String? = ""

            when (selectedPos) {
                "1" -> {
                    desc = list.find {
                        it.title == "Alpha"
                    }?.introduction
                }
                "3" -> {
                    desc = list.find {
                        it.title == "Beta"
                    }?.introduction

                }
                "2" -> {
                    desc = list.find {
                        it.title == "Omega"
                    }?.introduction

                }
            }

            txtData.value = HtmlCompat.fromHtml(desc.toString(), 0).toString()

        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TitleBar(popBack)
        PersonalityHeader(selectedPos)
        Description(txtData.value)
    }
}


@Composable
private fun Description(txtDescription: String?) {
    Text(
        text = "Character Introduction",
        fontSize = 16.sp,
        fontFamily = poppinsMedium,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
    Text(
        text = txtDescription ?: "",
        fontSize = 13.sp,
        fontFamily = poppinsRegular,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 0.dp)
    )
    Text(
        text = "All Copyrights reserved by Osomate LLC",
        fontSize = 13.sp,
        fontFamily = poppinsMedium,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 30.dp),
        textAlign = TextAlign.Center
    )
}

private val bigString =
    "Alpha is the first in Greek alphabet. In human society, when a person is calledalpha, it usually means that person has a strong mind, strength, and resources,alpha is value orianted.\nAlpha is the first in Greek alphabet. In human society, when a person is calledalpha, it usually means that person has a strong mind, strength, and resources,alpha is value orianted.Alpha is the first in Greek alphabet. In human society, when a person is calledalpha, it usually means that person has a strong mind, strength, and resources,alpha is value orianted.\nAlpha is the first in Greek alphabet. In human society, when a person is calledalpha,\nAlpha is the first in Greek alphabet. In human society, when a person is calledalpha,\nAlpha is the first in Greek alphabet. In human society, when a person is calledalpha,\nAlpha is the first in Greek alphabet. In human society, when a person is calledalpha,"

@Composable
private fun PersonalityHeader(
    selectedPos: String,
) {
    var title = ""
    var color = RedBrown
    var imgId = R.drawable.alpha_group
    // var imgId = personalityDataList.
    when (selectedPos) {
        "1" -> {
            title = "Alpha Group"
            color = RedBrown
            imgId = R.drawable.alpha_group
        }
        "2" -> {
            title = "Omega Group"
            color = RipePlum
            imgId = R.drawable.omega_group
        }
        "3" -> {
            title = "Beta Group"
            color = BlueOrchid
            imgId = R.drawable.beta_group
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 20.dp)
    ) {
        Image(
            painter = painterResource(id = imgId), contentDescription = "",
            modifier = Modifier.size(100.dp), contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(start = 25.dp)
                .fillMaxWidth()
                .height(100.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "My Character",
                fontFamily = poppinsMedium,
                fontSize = 17.sp,
                color = Color.Black
            )
            Text(text = title, fontFamily = poppinsBold, fontSize = 27.sp, color = color)
        }
    }
}

@Composable
private fun TitleBar(popBack: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow), contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .clickable {
                    backepersonality="1"
                    popBack?.invoke()

                }
        )

        Image(
            painter = painterResource(id = R.drawable.logo2), contentDescription = "",
            modifier = Modifier.size(50.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.search), contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .clickable { }
        )

    }
}
