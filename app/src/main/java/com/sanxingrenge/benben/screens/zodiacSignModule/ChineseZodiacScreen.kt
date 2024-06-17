package com.sanxingrenge.benben.screens.zodiacSignModule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanxingrenge.benben.constant.chineseDescriptionCustomString
import com.sanxingrenge.benben.responseModel.ChinesZodicDataResponse
import com.sanxingrenge.benben.ui.theme.poppinsSemiBold
import com.sanxingrenge.benben.viewModel.NormalZodiaViewModel
import kotlinx.coroutines.delay

@Composable
fun ChineseZodiacScreen(popBack: (() -> Unit)? = null,showLoading: (Boolean) -> Unit) {
    resetCircularListData()
    val viewModel: NormalZodiaViewModel = hiltViewModel()

   /* val selectedItem = remember {
        mutableStateOf(chineseZodiacMainList[0])
    }*/
    val selectedItem = remember { mutableStateOf<ChinesZodicDataResponse?>(null) }
    val contentData = remember { mutableStateOf<ChinesZodicDataResponse?>(null) }


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    showLoading.invoke(true)

    viewModel.getAnimalList {list,isFirstData->
        showLoading.invoke(false)
        if(isFirstData!=null) {
            chineseZodiacMainList = list
            chinesZodiacSmallImageList = list.map { it.icon }

            selectedItem.value = isFirstData
            contentData.value = isFirstData
        }
    }
    LaunchedEffect(selectedItem.value) {
        delay(1000)
        contentData.value = selectedItem.value
    }

    if(selectedItem.value==null || contentData.value==null) return

    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(contentData.value?.title ?: "", Color.Black, null) {
            popBack?.invoke()
        }

        CircularList(
            CircularListDataType.ChineseData(chineseZodiacMainList),
            Modifier.fillMaxWidth(),
            (screenWidth / 7) - 1.dp
        ) { selected ->
            (selected as? ChinesZodicDataResponse)?.let {
                contentData.value = it
            }
        }

        val itemData = contentData.value
        if (itemData != null) {
            LazyListDesign(itemData)
        }
    }

}

@Composable
private fun LazyListDesign(itemData: ChinesZodicDataResponse) {
    LazyColumn(modifier = Modifier
        .padding(top = 15.dp)
        .fillMaxSize(), content = {

        item {
            ChineseBigImageDesign(itemData)
        }
        item {
            ImageNameDesign(itemData)
        }
        item {
            luckyChinesDesign(itemData)
        }
        item {
            luckyChinesColorDesign(itemData)
        }
        item {
            BirthDateChinesDesign(itemData)
        }
        item {
            SignDetails(itemData)
        }

       /* item {
            Text(
                text = "Advantage:",
                fontFamily = poppinsSemiBold,
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .padding(horizontal = 20.dp)
            )
        }*/


       /* item {

            Text(
                text = chineseDescriptionCustomString(
                    HtmlCompat.fromHtml(itemData.advantage ?: "",0).toString()),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }*/


        /* items(itemData.advantages.size) { pos ->
             Text(
                 text = chineseDescriptionCustomString(itemData.advantages[pos]),
                 modifier = Modifier.padding(horizontal = 20.dp)
             )
         }*/
       /* item {
            Text(
                text = "Disadvantage:",
                fontFamily = poppinsSemiBold,
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .padding(horizontal = 20.dp)
            )
        }*/
       /* item {

            Text(
                text = chineseDescriptionCustomString(HtmlCompat.fromHtml(itemData.disadvantage ?: "",0).toString()),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }*/
        /* items(itemData.disadvantages.size) { pos ->
             Text(
                 text = chineseDescriptionCustomString(itemData.disadvantages[pos]),
                 modifier = Modifier.padding(horizontal = 20.dp)
             )
         }*/
    })
}
