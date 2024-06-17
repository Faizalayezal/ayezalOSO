package com.sanxingrenge.benben.constant

import android.os.Build
import androidx.annotation.RequiresApi

data class ChatMessageObject(
    var type: ChatMessageListItemType,
    var message: String,
    val date: String,
    val time: String,
    var filesArray: ArrayList<FileData>?=null ,
    var isSelected: Boolean = false,
    var fileDownloadStatus:FileDownloadStatus=FileDownloadStatus.NotDownloaded,
    var key :String,
    var deleteStatus :String,
    var userName : String = "",
    var userImage : String = "",


)
@RequiresApi(Build.VERSION_CODES.O)
fun playAudio(filePath: String) {
    Constns.playerAudio?.reset()
    Constns.playerAudio?.setDataSource(filePath)
    Constns.playerAudio?.prepare()
    Constns.playerAudio?.start()
}

data class FileData(
    var fileString:String,
    var fileKey:String,
    var deleteStatus:String
) : java.io.Serializable

enum class FileDownloadStatus{
    NotDownloaded,
    Downloaded,
    Downloading
}
sealed class ChatMessageListItemType(val intType: Int) {
    class DateType : ChatMessageListItemType(0)

    class StringReceiverType : ChatMessageListItemType(1)
    class StringSenderType : ChatMessageListItemType(2)

    class InvitationType : ChatMessageListItemType(3)

    class AudioTypeReceiver : ChatMessageListItemType(9)
    class AudioTypeSender : ChatMessageListItemType(5)

    class SingleImageTypeReceiver : ChatMessageListItemType(10)
    class SingleImageTypeSender : ChatMessageListItemType(6)

    class ImageListTypeReceiver : ChatMessageListItemType(11)
    class ImageListTypeSender : ChatMessageListItemType(7)
}