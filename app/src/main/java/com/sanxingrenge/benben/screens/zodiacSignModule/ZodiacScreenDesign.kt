package com.sanxingrenge.benben.screens.zodiacSignModule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.responseModel.ChinesZodicDataResponse
import com.sanxingrenge.benben.responseModel.ConstellationDataResponse
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.delay

@Composable
fun TextDescription(
    occProvider: () -> String,
   // advProvider: () -> String,
   // weakProvider: () -> String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 30.dp)
    ) {
        InsideSmallDesc(text = "Description:")
        InsideSmallTitle(text = occProvider(), 13.sp)
        AddSpace(10.dp)
     //   InsideSmallDesc(text = "Advantage:")
     //   InsideSmallTitle(text = advProvider(), 13.sp)
     //   AddSpace(10.dp)
      //  InsideSmallDesc(text = "Weakness:")
      //  InsideSmallTitle(text = weakProvider(), 13.sp)
    }
}

@Composable
fun KeywordDesign(keywordProvider: () -> String, keywordTrait: () -> String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            InsideSmallTitle(text = "Symbol:")
            InsideSmallDesc(text = keywordProvider())
        }

        Row(
            modifier = Modifier.weight(1.8f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                InsideSmallTitle(text = "Key Trait:")
                InsideSmallDesc(text = keywordTrait())
            }
        }
    }
}

@Composable
fun luckynumberLuckygamDesign(
    luckynumber: () -> String,
    jeweltitleListNameProv: () -> List<String>
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            InsideSmallTitle(text = "Lucky number:")
            InsideSmallDesc(text = luckynumber())
        }

        Row(
            modifier = Modifier.weight(1.8f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
            val listJewelName = jeweltitleListNameProv()
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                InsideSmallTitle(text = "Lucky gem:")
                val listJewelName = jeweltitleListNameProv()

                LazyRow(content = {
                    items(listJewelName.size) { pos ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InsideSmallDesc(text = listJewelName[pos])
                        }
                    }
                })

            }
        }
    }
}

@Composable
fun LuckyDesign(
    luckNoProv: () -> String, luckColorProv: () -> String,
    jewelListImageProv: () -> List<String>,
    jewelListNameProv: () -> List<String>,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 17.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                InsideSmallTitle(text = "Lucky number:")
                InsideSmallDesc(text = luckNoProv())
            }

            Row(
                modifier = Modifier.weight(1.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                        .width(1.dp)
                        .background(White.copy(alpha = 0.15f))
                )
                Column(
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    InsideSmallTitle(text = "Lucky Jewelry:")

                    val listJewelImage = jewelListImageProv()
                    val listJewelName = jewelListNameProv()
                    LazyRow(content = {
                        items(listJewelImage.size) { pos ->
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(listJewelImage[pos])
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .size(18.dp)
                                )
                                InsideSmallDesc(text = listJewelName[pos])
                            }
                        }
                    })
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.Center,

            ) {
            InsideSmallTitle(text = "Lucky color:")
            InsideSmallDesc(text = luckColorProv())
        }
    }
}

@Composable
fun RulingPlanetDesign(
    planetImgProv: () -> String,
    planetNameProv: () -> String,
    eleImgProv: () -> String,
    eleNameProv: () -> String,
    bestMatchProv: () -> List<String>,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            InsideSmallTitle(text = "Ruling planet:")
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(planetImgProv())
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(18.dp)
                )
                InsideSmallDesc(text = planetNameProv())
            }
        }
        Row(
            modifier = Modifier.weight(0.8f),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
            Column(
                modifier = Modifier.weight(1f, true),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InsideSmallTitle(text = "Element:")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(eleImgProv())
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(18.dp)
                    )
                    InsideSmallDesc(text = eleNameProv())
                }
            }
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InsideSmallTitle(text = "Best match:")
            LazyRow(
                modifier = Modifier.padding(top = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val list = bestMatchProv()
                items(list.size) { pos ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(list[pos]).crossfade(true).build(),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(17.dp),

                        )
                }
            }
        }
    }
}


@Composable
fun spiritDesign(
    spliteNameProv: () -> String,
    polirityProv: () -> String,
    qualityProv: () -> String,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            InsideSmallTitle(text = "Spirit color:")
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                InsideSmallDesc(text = spliteNameProv())
            }
        }
        Row(
            modifier = Modifier.weight(0.8f),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
            Column(
                modifier = Modifier.weight(1f, true),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InsideSmallTitle(text = "Polarity:")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    InsideSmallDesc(text = polirityProv())
                }
            }
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InsideSmallTitle(text = "Quality:")

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                InsideSmallDesc(text = qualityProv())
            }

        }
    }
}


@Composable
fun RulingHousePlanetDesign(
    rulingNameProv: () -> String,
    flowerNameProv: () -> String,

    ) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            InsideSmallTitle(text = "Ruling house:")
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InsideSmallDesc(text = rulingNameProv())
            }
        }
        Row(
            modifier = Modifier.weight(1.8f),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.15f))
            )
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                InsideSmallTitle(text = "Flower:")
                InsideSmallDesc(text = flowerNameProv())
            }

        }

    }
}


@Composable
fun BirthDateDesign(
    birthNameProv: () -> String,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(MidnightMoss, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            InsideSmallTitle(text = "Brithdate:")
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                InsideSmallDesc(text = birthNameProv())
            }
        }
    }
}

@Composable
fun InsideSmallTitle(text: String, textSize: TextUnit = 11.sp) {
    Text(
        text = text,
        fontFamily = poppinsLight,
        fontSize = textSize,
        color = Color.White.copy(alpha = 0.55f),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun InsideSmallDesc(text: String) {
    Text(
        text = text, fontFamily = poppinsRegular, fontSize = 15.sp, color = Color.White,
        modifier = Modifier
            .padding(end = 10.dp)
    )
}

@Composable
fun ChinesSmallDesc(text: String) {
    Text(
        text = text, fontFamily = poppinsRegular, fontSize = 15.sp, color = Color.Black,
        modifier = Modifier
            .padding(end = 10.dp)
    )
}

@Composable
fun ChinesSmallTitle(text: String, textSize: TextUnit = 11.sp) {
    Text(
        text = text,
        fontFamily = poppinsLight,
        fontSize = textSize,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun ImageDescriptionDesign(bigImageProvider: () -> String) {
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = bigImageProvider(),
        fontFamily = poppinsExtraLight,
        fontSize = 11.sp,
        color = White
    )
}

private var valueChangeCount = 0
private var isFirstTime = true

fun resetCircularListData() {
    valueChangeCount = 0
    isFirstTime = true
}

//enum class CircularListDataType { ChineseData, NormalData, }

sealed class CircularListDataType {
    class ChineseData(val list: List<ChinesZodicDataResponse>) : CircularListDataType()
    class NormalData(val list: List<ConstellationDataResponse>) : CircularListDataType()
}

@OptIn(ExperimentalSnapperApi::class)
@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun CircularList(
    circularListDataType: CircularListDataType,
    modifier: Modifier = Modifier, widthOffset: Dp, onItemClick: (Any) -> Unit
) {
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)
    val currentPage = remember {
        mutableStateOf(1073741829 + 3)
    }
    LaunchedEffect(key1 = listState.firstVisibleItemIndex, block = {
        if (valueChangeCount > 1) {
            delay(800)
            currentPage.value = listState.firstVisibleItemIndex + 4
            val index = (listState.firstVisibleItemIndex + 4) % 12

            when (circularListDataType) {
                is CircularListDataType.ChineseData -> chineseZodiacMainList
                is CircularListDataType.NormalData -> normalZodiacMainList
            }.getOrNull(index)?.let {
                onItemClick.invoke(it)
            }
        }
        valueChangeCount += 1
    })
    LazyRow(
        state = listState,
        modifier = modifier,
        flingBehavior = rememberSnapperFlingBehavior(listState),
    ) {
        items(Int.MAX_VALUE) { pos ->

            val index = pos % 12
            Log.d("TAG", "CircularList: " + pos + " index " + index)
            Box(
                modifier = Modifier
                    .size(
                        widthOffset
                    ), contentAlignment = Alignment.Center
            ) {
                val animation: AnimationSpec<Dp> = tween(500)
                val offsetAnimation: Dp by animateDpAsState(
                    if (currentPage.value == pos) 65.dp
                    else 20.dp, animation
                )

                Box(
                    modifier = Modifier
                        .size(offsetAnimation),
                    contentAlignment = Alignment.Center
                ) {
                    when (circularListDataType) {
                        is CircularListDataType.ChineseData -> {
                            ChineseSliderItemMain(
                                page = pos,
                                currentPageState = currentPage.value,
                                selectedData = chinesZodiacSmallImageList[index] ?: "",
                            )
                        }

                        is CircularListDataType.NormalData -> {
                            NormalSliderItemMain(
                                page = pos,
                                currentPageState = currentPage.value,
                                selectedData = normalZodiacSmallImageList[index] ?: "",
                            )
                        }
                    }

                }
            }
        }
    }
    LaunchedEffect(key1 = true, block = {
        if (isFirstTime) {
            listState.scrollToItem(1073741829)
            isFirstTime = false
        }
    })
}

@Composable
fun NormalBigImageDesign(bigImageGetter: () -> String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(bigImageGetter()).crossfade(true).build(),
        contentDescription = "",
        modifier = Modifier
            .padding(top = 15.dp)
            .size(200.dp)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NormalSliderItemMain(
    page: Int, currentPageState: Int, selectedData: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = currentPageState == page,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.dark_selected),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        val animation: AnimationSpec<Dp> = tween(500)
        val offsetAnimation3: Dp by animateDpAsState(
            if (currentPageState == page) 18.dp
            else if ((currentPageState - page) == 1 || (currentPageState - page) == -1) 0.dp
            else 2.dp, animation
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedData)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .padding(offsetAnimation3)
                .fillMaxSize(),
            alpha = if (currentPageState == page) 1f
            else if ((currentPageState - page) == 1 || (currentPageState - page) == -1) 0.75f
            else 0.30f
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChineseSliderItemMain(
    page: Int, currentPageState: Int, selectedData: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = currentPageState == page,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.selected_item_bg),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()

            )

        }
        val animation: AnimationSpec<Dp> = tween(500)
        val offsetAnimation3: Dp by animateDpAsState(
            if (currentPageState == page) 18.dp
            else 0.dp, animation
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedData)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .padding(offsetAnimation3)
                .fillMaxSize(),
            alpha = if (currentPageState == page) 1f
            else if ((currentPageState - page) == 1 || (currentPageState - page) == -1) 0.75f
            else 0.30f
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun SignDetails(
    @PreviewParameter(ChineseZodiacDataProvider::class)
    selectedItemStateMain: ChinesZodicDataResponse
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Description",
            fontFamily = poppinsSemiBold,
            fontSize = 15.sp,
            color = Color.Black
        )
        AddSpace(5.dp)
        AnimatedContent(targetState = selectedItemStateMain.introduction) { targetState ->
            Text(
                text = HtmlCompat.fromHtml(targetState ?: "", 0).toString(),
                fontFamily = poppinsLight,
                fontSize = 11.sp,
                color = SilverChalice
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TitleBar(
    title: String,
    txtColor: Color, colorFilter: ColorFilter?, onCloseClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(modifier = Modifier.size(40.dp), onClick = { onCloseClick.invoke() }) {
            Image(
                painter = painterResource(R.drawable.back_arrow), contentDescription = "",
                Modifier.size(40.dp), colorFilter = colorFilter
            )
        }
        AnimatedContent(targetState = title) { text ->
            Text(
                text = text, fontSize = 21.sp, fontFamily = poppinsSemiBold,
                color = txtColor, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun ImageNameDesign(
    @PreviewParameter(ChineseZodiacDataProvider::class)
    selectedItemStateMain: ChinesZodicDataResponse
) {
    Box(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedItemStateMain.chinese_sign).crossfade(true).build(),
            contentDescription = "",
            modifier = Modifier.size(45.dp)
        )

        Log.d("TAG", "IsdmageNameDesign: " + selectedItemStateMain.chinese_sign)
        /*AnimatedContent(targetState = selectedItemStateMain.title) { targetState ->
            Image(
                painter = painterResource(id = targetState),
                contentDescription = "",
                modifier = Modifier.size(45.dp)
            )

        }*/
    }
}


@Composable
fun luckyChinesDesign(
    @PreviewParameter(ChineseZodiacDataProvider::class)
    selectedItemStateMain: ChinesZodicDataResponse
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(Chinesbg, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            ChinesSmallTitle(text = "Lucky Numbers:")
            ChinesSmallDesc(text = selectedItemStateMain.lucky_numbers ?: "")

        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(VioletBlue)
            )
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                ChinesSmallTitle(text = "Unlucky Numbers:")
                ChinesSmallDesc(text = selectedItemStateMain.unlucky_numbers ?: "")
            }
        }

        Log.d("TAG", "IsdmageNameDesign: " + selectedItemStateMain.unlucky_numbers)

    }
}

@Composable
fun luckyChinesColorDesign(
    @PreviewParameter(ChineseZodiacDataProvider::class)
    selectedItemStateMain: ChinesZodicDataResponse
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(Chinesbg, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            ChinesSmallTitle(text = "Lucky Colors:")
            ChinesSmallDesc(text = selectedItemStateMain.lucky_colors ?: "")

        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(VioletBlue)
            )
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                ChinesSmallTitle(text = "Unlucky Colors:")
                ChinesSmallDesc(text = selectedItemStateMain.unlucky_colors ?: "")
            }
        }

        Log.d("TAG", "IsdmageNameDesign: " + selectedItemStateMain.unlucky_numbers)

    }
}

@Composable
fun BirthDateChinesDesign(
    @PreviewParameter(ChineseZodiacDataProvider::class)
    selectedItemStateMain: ChinesZodicDataResponse
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .background(Chinesbg, RoundedCornerShape(10.dp))
            .padding(vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            ChinesSmallTitle(text = "Year of brith:")
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                ChinesSmallDesc(text = selectedItemStateMain.year_of_birth?:"")
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun ChineseBigImageDesign(
    @PreviewParameter(ChineseZodiacDataProvider::class)
    selectedItemStateMain: ChinesZodicDataResponse
) {
    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedItemStateMain.thumbnail).crossfade(true).build(),
            contentDescription = "",
            modifier = Modifier.size(170.dp)
        )
        /* AnimatedContent(targetState = selectedItemStateMain.thumbnail) { targetState ->
            *//* Image(
                painter = painterResource(id = targetState),
                contentDescription = "",
                modifier = Modifier.size(170.dp)
            )*//*
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedItemStateMain.thumbnail).crossfade(true).build(),
                contentDescription = "",
                modifier = Modifier.size(170.dp)
            )
        }*/
    }
}

class ChineseZodiacDataProvider : PreviewParameterProvider<ChinesZodicDataResponse> {
    override val values = sequenceOf(chineseZodiacMainList[0])
}
