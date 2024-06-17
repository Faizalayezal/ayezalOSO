package com.sanxingrenge.benben.screens.loggedInModule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.responseModel.SearchUserResponseData
import com.sanxingrenge.benben.screens.Route
import com.sanxingrenge.benben.ui.theme.*
import com.sanxingrenge.benben.uiComponents.AddSpace
import com.sanxingrenge.benben.viewModel.DiscoverScreenViewModel

@Preview
@Composable
fun SearchUserScreen(popBack: (() -> Unit)? = null,openScreen: ((String) -> Unit)? = null) {

    val context = LocalContext.current
    val viewModel: DiscoverScreenViewModel = hiltViewModel()

    val data = viewModel.listOfElemenats.value
    Log.d("TAG", "SdfsdearchUserScreen: " + data)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchBar(popBack)
        SearchResults(data,openScreen,data.firstOrNull()?.id)


    }
}

@Composable
private fun SearchResults(
    data: List<SearchUserResponseData>,
    openScreen: ((String) -> Unit)?,
    userId: Int?
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize(), content = {
        items(data.size) { pos ->
            val dataItem = data[pos]

            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 15.dp)
                    .fillMaxWidth()
                    .clickable {
                        openScreen?.invoke(Route.PROFILE_DETAIL_BASE +"/${userId}")
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(dataItem.image).crossfade(true).build(),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(55.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = dataItem.name ?: "",
                        fontSize = 15.sp,
                        fontFamily = poppinsBold,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), verticalAlignment = CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(dataItem.animal_sign?.image_full_path)
                                .crossfade(true).build(),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                                .background(DawnPink, RoundedCornerShape(20.dp))
                                .padding(2.dp)
                        )
                        CustomSpacer()
                        AsyncImage(
                            model =ImageRequest.Builder(LocalContext.current)
                                .data(dataItem.zodiac_sign?.image_full_path)
                                .crossfade(true).build(),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        CustomSpacer()
                        AsyncImage(
                            model =ImageRequest.Builder(LocalContext.current)
                                .data(dataItem.gender_image).crossfade(true).build(),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(BondiBlue),
                            modifier = Modifier
                                .rotate(-45f)
                                .size(13.dp), contentScale = ContentScale.FillBounds
                        )
                        AddSpace(size = 10.dp)
                        Text(
                            text = dataItem.gender ?: "",
                            fontFamily = poppinsRegular,
                            fontSize = 13.sp,
                            color = SilverChalice
                        )
                        CustomSpacer()
                        Text(
                            text = dataItem.distance ?: "",
                            fontFamily = poppinsRegular,
                            fontSize = 13.sp,
                            color = SilverChalice
                        )
                    }
                }
            }

        }
    })
}

/*@Composable

private fun SearchResults(userId: Int, pos: Int, SearchList: List<SearchUserResponseData>) {


    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 15.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(SearchList[pos].image).crossfade(true).build(),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 15.dp)
                .size(55.dp)
                .clip(RoundedCornerShape(100.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "",
                fontSize = 15.sp,
                fontFamily = poppinsBold,
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(), verticalAlignment = CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("SearchList[pos].animal_sign?.firstOrNull()?.image_full_path")
                        .crossfade(true).build(),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .background(DawnPink, RoundedCornerShape(20.dp))
                        .padding(2.dp)
                )
                CustomSpacer()
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("SearchList[pos].zodiac_sign?.firstOrNull()?.image_full_path")
                        .crossfade(true).build(),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp),
                    contentScale = ContentScale.FillBounds
                )
                CustomSpacer()
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("SearchList[pos].gender_image").crossfade(true).build(),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(BondiBlue),
                    modifier = Modifier
                        .rotate(-45f)
                        .size(13.dp), contentScale = ContentScale.FillBounds
                )
                AddSpace(size = 10.dp)
                Text(
                    text = "",
                    fontFamily = poppinsRegular,
                    fontSize = 13.sp,
                    color = SilverChalice
                )
                CustomSpacer()
                Text(
                    text = "",
                    fontFamily = poppinsRegular,
                    fontSize = 13.sp,
                    color = SilverChalice
                )
            }
        }
    }

}
*/


@Composable
private fun CustomSpacer() {
    Spacer(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .height(12.dp)
            .width(1.dp)
            .background(
                SilverChalice
            )
    )
}

@Composable
private fun SearchBar(popBack: (() -> Unit)? = null) {

    val context = LocalContext.current
    val viewModel: DiscoverScreenViewModel = hiltViewModel()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 20.dp, start = 5.dp),
        verticalAlignment = CenterVertically
    ) {
        IconButton(onClick = { popBack?.invoke() }, modifier = Modifier.size(50.dp)) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "", modifier = Modifier.size(40.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true)
                .height(50.dp)
                .background(SoftPeach, RoundedCornerShape(100.dp)),
            verticalAlignment = CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.search_black),
                contentDescription = "", modifier = Modifier
                    .padding(start = 15.dp)
                    .size(22.dp), alpha = 0.22f
            )
            val currentSearchText = remember {
                mutableStateOf("")
            }

            BasicTextField(
                singleLine = true,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .fillMaxWidth()
                    .align(CenterVertically)
                    .padding(end = 20.dp),
                value = currentSearchText.value,
                onValueChange = {
                    currentSearchText.value = it
                    if (currentSearchText.value.length >= 3) {

                        viewModel.userSearch(onListReceived = { list ->

                        }, currentSearchText.value, context)
                    }


                },
                textStyle = TextStyle.Default.copy(
                    color = Color.Black,
                    fontFamily = poppinsMedium,
                    fontSize = 18.sp
                ),
            )
        }
        IconButton(onClick = { }, modifier = Modifier.size(40.dp)) {
            Image(
                painter = painterResource(id = R.drawable.setting_black),
                contentDescription = "", modifier = Modifier.size(25.dp)
            )
        }
    }
    Spacer(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(GainsBoro)
            .padding(bottom = 10.dp)
    )
}
