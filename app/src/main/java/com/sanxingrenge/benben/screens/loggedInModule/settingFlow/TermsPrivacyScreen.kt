package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.viewModel.AgreementViewModel

@Composable
fun TermsPrivacyScreen(type: String = "terms", popBack: (() -> Unit)? = null,showLoading: (Boolean) -> Unit,) {
    //type
    //terms
    //privacy
    val txtData = remember {
        mutableStateOf<String?>(null)
    }
    val viewModel: AgreementViewModel = hiltViewModel()
    showLoading.invoke(true)
    viewModel.getAgreementDataList { list,status ->
        //find no use item list mathi gotine show krvani
        showLoading.invoke(false)
        val desc: String?
        if (type == "privacy") {
            desc = list.find {
                it.title_key == "privacy_policy"

            }?.description
        } else if (type == "terms") {
            desc = list.find {
                it.title_key == "terms_and_agreements"
            }?.description
        } else {
            desc = list.find {
                it.title_key == "guidelines"
            }?.description

        }

        txtData.value = HtmlCompat.fromHtml(desc.toString(), 0).toString()
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        content = {

            if (type == "terms") {
                Common.SettingTopBar("Terms and Agreements") {
                    popBack?.invoke()
                }

                Text(
                    text = "Terms and Conditions of Use",
                    fontFamily = poppinsSemiBold,
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 10.dp)
                )
                Text(
                    text = txtData.value ?: "",
                    fontFamily = poppinsRegular,
                    fontSize = 11.sp,
                    color = SilverChalice,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 0.dp, bottom = 10.dp)
                )

            } else if (type == "privacy") {
                Common.SettingTopBar("Privacy Policy") {
                    popBack?.invoke()
                }
                Text(
                    text = txtData.value ?: "",
                    fontFamily = poppinsRegular,
                    fontSize = 11.sp,
                    color = SilverChalice,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 15.dp, bottom = 10.dp)
                )
            } else if (type == "guidelines") {
                Common.SettingTopBar("Guidelines") {
                    popBack?.invoke()
                }
                Text(
                    text = txtData.value ?: "",
                    fontFamily = poppinsRegular,
                    fontSize = 11.sp,
                    color = SilverChalice,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 15.dp, bottom = 10.dp)
                )
            }


        })


}
