package com.sanxingrenge.benben.dataModel

sealed class MessageType{
    class StringMessage(val type:String="string"):MessageType()
   // class VideoMessage(val type:String="video"):MessageType()
    // class FileMessage(val type:String="file"):MessageType()
    class ImageMessage(val type:String="image"):MessageType()

    class AudioMessage(val type:String="audio"):MessageType()
}

/*
sealed class MessageType {
    class Date(val dateStr: String) : MessageType()

    class MsgReceived(val msg: String, val image: Int, val date: String) : MessageType()
    class ImagesReceived(val images: List<Int>, val userImage: Int, val date: String) :
        MessageType()

    class MsgSent(val msg: String, val date: String) : MessageType()
    class AudioSent(val urlStr: String, val date: String) : MessageType()
    class ImagesSent(val images: List<Int>, val date: String) : MessageType()
}*/
