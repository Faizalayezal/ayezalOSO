package com.sanxingrenge.benben.screens.zodiacSignModule

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.responseModel.ConstellationDataResponse
import com.sanxingrenge.benben.ui.theme.BlackRock
import com.sanxingrenge.benben.ui.theme.White
import com.sanxingrenge.benben.viewModel.NormalZodiaViewModel
import kotlinx.coroutines.delay

private const val TAG = "NormalZodiacScreen"

@Composable
fun NormalZodiacScreen(popBack: (() -> Unit)? = null,showLoading: (Boolean) -> Unit) {
    resetCircularListData()

    val viewModel: NormalZodiaViewModel = hiltViewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(BlackRock),
            painter = painterResource(id = R.drawable.mainbg),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alpha = 0.22f
        )
        MainScreenDesign(popBack, viewModel,showLoading)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainScreenDesign(
    popBack: (() -> Unit)? = null,
    viewModel: NormalZodiaViewModel,
    showLoading: (Boolean) -> Unit
) {
    val selectedItem = remember { mutableStateOf<ConstellationDataResponse?>(null) }
    val contentData = remember { mutableStateOf<ConstellationDataResponse?>(null) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    showLoading.invoke(true)
    viewModel.getConstellationList { list, isFirstData ->
        showLoading.invoke(false)
        if (isFirstData != null) {
            normalZodiacMainList = list
            normalZodiacSmallImageList = list.map { it.icon }

            selectedItem.value = isFirstData
            contentData.value = isFirstData
        }
    }

    LaunchedEffect(selectedItem.value) {
        delay(1000)
        contentData.value = selectedItem.value
    }

    if (selectedItem.value == null || contentData.value == null) return

    Column(modifier = Modifier.fillMaxSize()) {

        TitleBar(contentData.value?.title ?: "", Color.White, ColorFilter.tint(White)) {
            popBack?.invoke()
        }

        CircularList(
            CircularListDataType.NormalData(normalZodiacMainList),
            Modifier.fillMaxWidth(),
            (screenWidth / 7) - 1.dp
        ) { selected ->
            Log.d(TAG, "MainScreenDesign: testFlowItemCHanged>>$selected")
            (selected as? ConstellationDataResponse)?.let {
                selectedItem.value = it
            }
        }
        Log.d(TAG, "MainScreenDesign: testContentData>>${contentData.value?.title}")
        AnimatedContent(targetState = contentData.value) { target ->
            Column(modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    NormalBigImageDesign {
                        target?.icon ?: ""
                    }

                    ImageDescriptionDesign {
                        target?.introduction ?: ""
                    }

                    BirthDateDesign(
                        birthNameProv = {target?.birthdate ?: ""  },
                    )
                    RulingHousePlanetDesign(
                        rulingNameProv = {target?.ruling_house ?: ""  },
                        flowerNameProv = {target?.flower ?: ""  },
                    )

                    RulingPlanetDesign(
                        planetImgProv = { target?.ruling_planet_icon ?: "" },
                        planetNameProv = { target?.ruling_planet_title ?: "" },
                        eleImgProv = { target?.element_icon ?: "" },
                        eleNameProv = { target?.element_title ?: "" },
                        bestMatchProv = {
                            target?.best_matches_images?.map {
                                it.image ?: ""
                            } ?: listOf()
                        },
                    )

                    KeywordDesign(
                        keywordProvider = { target?.key_words ?: "" },
                        keywordTrait = { target?.key_trait ?: "" })

                    luckynumberLuckygamDesign(
                        luckynumber = { target?.lucky_number ?: "" },
                        jeweltitleListNameProv = {
                            target?.jewelry_icon?.map{
                                it.title?:"" }?: listOf()
                        })

                    spiritDesign(
                        spliteNameProv = { target?.lucky_color ?: "" },
                        polirityProv = {target?.polarity ?: "" },
                        qualityProv={target?.quality ?: "" }
                    )

                    /*LuckyDesign(luckNoProv = { target?.lucky_number ?: "" },
                        luckColorProv = { target?.lucky_color ?: "" },
                        // jewelListImageProv = { listOf(target?.lucky_jewelry_icon ?: "") },
                        jewelListImageProv = {
                            target?.jewelry_icon?.map {
                                it.icon ?: ""

                            } ?: listOf()
                        },
                        jewelListNameProv = {
                            target?.jewelry_icon?.map {
                                it.title ?: ""

                            } ?: listOf()
                        })*/

                    TextDescription(
                        occProvider = {
                            HtmlCompat.fromHtml(target?.description ?: "", 0).toString()
                        },
                       // advProvider = {HtmlCompat.fromHtml(target?.advantage ?: "", 0).toString()},
                       // weakProvider = { HtmlCompat.fromHtml(target?.weakness ?: "", 0).toString() }
                    )
                })
        }
    }
}

