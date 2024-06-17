package com.sanxingrenge.benben.screens.loggedInModule.settingFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.responseModel.GetFeedBackDataListResponse
import com.sanxingrenge.benben.screens.zodiacSignModule.feedBackMainLList
import com.sanxingrenge.benben.ui.theme.Nobel
import com.sanxingrenge.benben.ui.theme.PearlBush
import com.sanxingrenge.benben.ui.theme.SilverChalice
import com.sanxingrenge.benben.ui.theme.WhiteSmoke
import com.sanxingrenge.benben.ui.theme.poppinsRegular
import com.sanxingrenge.benben.viewModel.AddFeedBackViewModel
import java.text.SimpleDateFormat
import java.util.TimeZone

@Preview
@Composable
fun FeedbackRecordsScreen(popBack: (() -> Unit)? = null) {

    val viewModel: AddFeedBackViewModel = hiltViewModel()
    val context = LocalContext.current
    // var feedBackMainList :List<GetFeedBackDataListResponse> = listOf()
    //var feedBackMainListImageList :List<FeedBackList?> = listOf()

    var feedBackMainList = remember { mutableStateOf<List<GetFeedBackDataListResponse>?>(null) }
    val contentData = remember { mutableStateOf<GetFeedBackDataListResponse?>(null) }


    var txtEmail by remember {
        mutableStateOf<String?>(null)
    }
    val userLiveData =

        viewModel.getFeedBack(context) { list, isFirstData ->
            feedBackMainList.value = list
            feedBackMainLList = list


        }
    /* LaunchedEffect(selectedItem.value) {
         delay(1000)
         contentData.value = selectedItem.value
     }*/

    /*Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), content = {*/


    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Common.SettingTopBar("Contact Us Records") {
            popBack?.invoke()
        }
        ItemDesign(feedBackMainList.value, feedBackMainLList)
        /*LazyColumn {
            items(feedBackMainLList.size) { pos ->
                ItemDesign(feedBackMainList.value, feedBackMainLList, pos)
            }
        }*/

    }


}




@Composable
private fun ItemDesign(
    value: List<GetFeedBackDataListResponse>?,
    feedBackMainLList: List<GetFeedBackDataListResponse>,
) {

    // Z TIME TO FORMAT YOUR DATA
    var newdate = value?.firstOrNull()?.created_at
    try {

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatter.parse(newdate.toString())
        val dateFormatter = SimpleDateFormat("dd-MMM-yyyy") //this format changeable
        dateFormatter.timeZone = TimeZone.getDefault()
        newdate = dateFormatter.format(value)
        //Log.d("ourDate", ourDate);
    } catch (e: Exception) {
        newdate = "00-00-0000 00:00"
    }

    LazyColumn{
        items(feedBackMainLList.size){pos->
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 0.dp)
                    .padding(horizontal = 25.dp)
                    .fillMaxSize()
                    .border(1.dp, Nobel, RoundedCornerShape(10.dp)),
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WhiteSmoke)
                        .border(1.dp, Nobel, RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = feedBackMainLList[pos].feedback_type ?: "",
                        fontFamily = poppinsRegular,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                    Text(
                        text = newdate ?: "",
                        fontFamily = poppinsRegular,
                        fontSize = 11.sp,
                        color = SilverChalice
                    )
                }

                Text(
                    text = feedBackMainLList[pos].description ?: "",
                    fontFamily = poppinsRegular,
                    fontSize = 12.sp,
                    color = SilverChalice,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 0.dp)
                        .padding(horizontal = 20.dp)
                )

                /* AsyncImage(
                     model = ImageRequest.Builder(LocalContext.current)
                         .data(feedBackMainLList[pos].feedback_images?.firstOrNull()?.image)
                         .crossfade(true).build(),
                     contentDescription = "",
                     modifier = Modifier
                         .padding(end = 10.dp)
                         .size(50.dp)
                         .clip(
                             RoundedCornerShape(10.dp)
                         )
                         .border(1.dp, PearlBush, RoundedCornerShape(10.dp)),
                     contentScale = ContentScale.Crop
                 )*/



                 LazyRow(modifier = Modifier
                     .padding(top = 10.dp, bottom = 0.dp)
                     .padding(horizontal = 20.dp), content = {
                     item {
                         AsyncImage(
                             model = ImageRequest.Builder(LocalContext.current)
                                 .data(feedBackMainLList[pos].feedback_images?.firstOrNull()?.image)
                                 .crossfade(true).build(),
                             contentDescription = "",
                             modifier = Modifier
                                 .padding(end = 10.dp)
                                 .size(50.dp)
                                 .clip(
                                     RoundedCornerShape(10.dp)
                                 )
                                 .border(1.dp, PearlBush, RoundedCornerShape(10.dp)),
                             contentScale = ContentScale.Crop
                         )

                     }


                 })

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.acc), contentDescription = "",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(15.dp)
                    )

                    Text(
                        text = feedBackMainLList[pos].email ?: "",
                        fontFamily = poppinsRegular,
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                }


            }
        }
    }




}
/*@Composable
private fun ItemDesign(
    value: List<GetFeedBackDataListResponse>?,
    feedBackMainLList: List<GetFeedBackDataListResponse>
) {

    // Z TIME TO FORMAT YOUR DATA
    var newdate = value?.firstOrNull()?.created_at
    try {

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatter.parse(newdate.toString())
        val dateFormatter = SimpleDateFormat("dd-MMM-yyyy") //this format changeable
        dateFormatter.timeZone = TimeZone.getDefault()
        newdate = dateFormatter.format(value)
        //Log.d("ourDate", ourDate);
    } catch (e: Exception) {
        newdate = "00-00-0000 00:00"
    }

    value?.forEach{

        Column(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 0.dp)
                .padding(horizontal = 25.dp)
                .fillMaxSize()
                .border(1.dp, Nobel, RoundedCornerShape(10.dp)),
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteSmoke)
                    .border(1.dp, Nobel, RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.feedback_type ?: "",
                    fontFamily = poppinsRegular,
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = newdate?:"",
                    fontFamily = poppinsRegular,
                    fontSize = 11.sp,
                    color = SilverChalice
                )
            }

            Text(
                text = it.description ?: "",
                fontFamily = poppinsRegular,
                fontSize = 12.sp,
                color = SilverChalice,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 0.dp)
                    .padding(horizontal = 20.dp)
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it.feedback_images?.firstOrNull()?.image)
                    .crossfade(true).build(),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(50.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .border(1.dp, PearlBush, RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

           *//* LazyRow(modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp)
                .padding(horizontal = 20.dp), content = {

                items(feedBackMainLList.size) { pos ->

                    Log.d("TAG", "ItemDesign321: "+value[pos].feedback_images?.firstOrNull()?.image)

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(feedBackMainLList[pos].feedback_images?.firstOrNull()?.image)
                            .crossfade(true).build(),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(50.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, PearlBush, RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )

                }

            })*//*

            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.acc), contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(15.dp)
                )

                Text(
                    text = it.email ?: "",
                    fontFamily = poppinsRegular,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }


        }



    }


}*/
/*
private fun ItemDesign(list: List<Int>? = null) {

    Column(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 0.dp)
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
            .border(1.dp, Nobel, RoundedCornerShape(10.dp)),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WhiteSmoke)
                .border(1.dp, Nobel, RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Unhealthy content",
                fontFamily = poppinsRegular,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = "26 aug 2022",
                fontFamily = poppinsRegular,
                fontSize = 11.sp,
                color = SilverChalice
            )
        }
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing \n" +
                    "and typesetting industry",
            fontFamily = poppinsRegular,
            fontSize = 12.sp,
            color = SilverChalice,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp)
                .padding(horizontal = 20.dp)
        )

        if (list != null) {

            LazyRow(modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp)
                .padding(horizontal = 20.dp), content = {

                items(list.size) { pos ->

                    Image(
                        painter = painterResource(id = list.getOrNull(pos) ?: 0),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(50.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, PearlBush, RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )

                }

            })

        }


        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.acc), contentDescription = "",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp)
            )

            Text(
                text = "maya0789@gmail.com",
                fontFamily = poppinsRegular,
                fontSize = 10.sp,
                color = Color.Black
            )
        }
    }

}*/
