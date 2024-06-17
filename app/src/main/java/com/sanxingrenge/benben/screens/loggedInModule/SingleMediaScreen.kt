package com.sanxingrenge.benben.screens.loggedInModule

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.Constns.chatImageData
import com.sanxingrenge.benben.constant.Constns.currantIdOther
import com.sanxingrenge.benben.constant.Constns.userIdOther
import com.sanxingrenge.benben.ui.theme.QuillGrey
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.shimmerBaseColor
import com.sanxingrenge.benben.viewModel.GetMessageListViewModel


@Composable
@Preview
fun SingleMediaScreen(
    openScreen: ((String, Boolean) -> Unit)? = null,
    popBack: (() -> Unit)? = null,
    viewModel: GetMessageListViewModel? = null,

    ) {

    //setupScreen(imageDatas)
    Log.d("TAG", "MediaScreen321: " + userIdOther)
    Log.d("TAG", "MediaScreen321: " + currantIdOther)

    viewModel?.start(userIdOther.toIntOrNull() ?: 0, currantIdOther.toIntOrNull() ?: 0)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 15.dp)
        )
        {


            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "", modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        popBack?.invoke()
                    }


            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 70.dp)
        )
        {


            ChatSingleImageItemDesign(openScreen, viewModel)
        }


    }

}

@Composable
fun ChatSingleImageItemDesign(
    openScreen: ((String, Boolean) -> Unit)?,
    viewModel: GetMessageListViewModel?
) {

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(chatImageData)
                .crossfade(true).build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBaseColor, RoundedCornerShape(8.dp))
        )

        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxSize()
        )
        {
            IconButton(onClick = {
                viewModel?.downloadImage(chatImageData, context, "OSOMATE")
                // val uri = Uri.parse(data)
                // val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + uri.lastPathSegment
                // Log.d("TAG", "ChatImageItemDesign: "+path)
                // val file = File(path)
                // File(path).exists()
                // if (file.exists()) {
                //     Toast.makeText(context, "Image already exists", Toast.LENGTH_SHORT).show()
                //    // isImageExists.value = true
                // } else {
                //     viewModel?.downloadImage(imageDatas[pos].fileString, context, "OSOMATE")
                // }


            }, Modifier.size(50.dp)) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    painter = painterResource(R.drawable.file_download),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }


        }


    }


}

fun DfownloadImage(
    fileString: String,
    context: Context,
    downloadStatus: MutableState<DownloadStatus>
) {


    Log.d("TAG", "DownloadImage: " + fileString)


    /*
        fun downloadImage() {
            downloadStatus.value = DownloadStatus.DOWNLOADING
            storageReference.getBytes(1024 * 1024).addOnSuccessListener {
                downloadStatus.value = DownloadStatus.SUCCESS

                // Save the image to the gallery
                val uri = Uri.parse(fileString)
                Log.d("TAG", "downloadImage: "+uri)
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
                context.startActivity(intent)
            }.addOnFailureListener {
                downloadStatus.value = DownloadStatus.FAILED
                Log.e("DownloadImage", "Failed to download image", it)
            }
        }
    */


    /* when (downloadStatus.value) {
         DownloadStatus.IDLE -> {
             // Do nothing
             Log.d("TAG", "downloadImage: "+206)

         }
         DownloadStatus.DOWNLOADING -> {
             // Show a loading indicator
             Log.d("TAG", "downloadImage: "+211)

         }
         DownloadStatus.SUCCESS -> {
             // The image has been downloaded successfully
             Log.d("TAG", "downloadImage: "+216)

         }
         DownloadStatus.FAILED -> {
             // The image download failed
             Log.d("TAG", "downloadImage: "+221)

         }
     }


     if (downloadStatus.value != DownloadStatus.IDLE) {
         // The image is already downloading or has been downloaded
         Log.d("TAG", "downloadImage: "+228)

         return
     }*/

}

enum class DofwnloadStatus {
    IDLE,
    DOWNLOADING,
    SUCCESS,
    FAILED
}



