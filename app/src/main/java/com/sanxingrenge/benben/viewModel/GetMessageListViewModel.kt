package com.sanxingrenge.benben.viewModel

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sanxingrenge.benben.constant.ChatMessageListItemType
import com.sanxingrenge.benben.constant.ChatMessageObject
import com.sanxingrenge.benben.constant.Constns.currantIdOther
import com.sanxingrenge.benben.constant.Constns.generateChatNode
import com.sanxingrenge.benben.constant.Constns.userIdOther
import com.sanxingrenge.benben.constant.FileData
import com.sanxingrenge.benben.constant.FileDownloadStatus
import com.sanxingrenge.benben.constant.FormatDateUseCase.getDifferenceOfYears
import com.sanxingrenge.benben.constant.FormatDateUseCase.toDateString
import com.sanxingrenge.benben.constant.FormatDateUseCase.toTimeString
import com.sanxingrenge.benben.constant.NotificationHelper
import com.sanxingrenge.benben.constant.dataStoreGetUserData
import com.sanxingrenge.benben.dataModel.MessageObject
import com.sanxingrenge.benben.dataModel.MessageType
import com.sanxingrenge.benben.responseModel.ConversionDataRsponse
import com.sanxingrenge.benben.retrofilt.MainRepo
import com.sanxingrenge.benben.retrofilt.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@HiltViewModel
class GetMessageListViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private val messageNode = "chatMessages"
    private lateinit var database: DatabaseReference
    private lateinit var storageDatabase: StorageReference

    private var currentUserId: Int = 0
    private var otherUserId: Int = 0
    private var nodeId = ""
    var nodeId2 = ""
    var readconverion = true

    @RequiresApi(Build.VERSION_CODES.O)
    fun start(
        currentUserId: Int,
        otherUserId: Int,
    ) {
        this.currentUserId = currentUserId
        this.otherUserId = otherUserId
        database = FirebaseDatabase.getInstance().reference
        storageDatabase = FirebaseStorage.getInstance().reference
        nodeId = generateChatNode(currentUserId, otherUserId)

        viewModelScope.launch {
            if (readconverion) readmsg(currentUserId.toString(), otherUserId.toString())
            readconverion = false

        }

    }

    fun sendMessage(message: String) {
        val messageObject = MessageObject(
            currentUserId,
            System.currentTimeMillis().toString(),
            MessageType.StringMessage().type,
            message,
            ""
        )
        database.child(messageNode).child(nodeId).push().setValue(messageObject)

        viewModelScope.launch {

            when (val data =
                repository.sendChatMessage(
                    currentUserId.toString(),
                    message,
                    otherUserId.toString()
                )) {
                is Resource.Error -> {}
                is Resource.Success -> {}
            }
        }
    }

    var msgConversion = MutableLiveData<ArrayList<ChatMessageObject>>(ArrayList())
   // var msgConversion = MutableStateFlow<ArrayList<ChatMessageObject>>(ArrayList())

  //  val _msgConversion = msgConversion.asStateFlow()
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun addIssuePost(issuePost: ChatMessageObject) {
        Log.d(
            "TAG",
            "addIssuePost: test81>>" + issuePost.deleteStatus + ">>" + issuePost.message + ">>" + issuePost.type.intType + ">>" + issuePost.filesArray
        )
        try {
            //date is different from other items
            if (msgConversion.value?.size == 0) {
                val newDateItem = ChatMessageObject(
                    ChatMessageListItemType.DateType(),
                    "",
                    issuePost.date,
                    "",
                    null,
                    false,
                    key = "",
                    deleteStatus = ""
                )
                msgConversion.value?.add(newDateItem)
            }
            // akhi list msg ni hoy teni uper date aave
            else if (msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.date
                != issuePost.date
            ) {
                val newDateItem = ChatMessageObject(
                    ChatMessageListItemType.DateType(),
                    "",
                    issuePost.date,
                    "",
                    null,
                    false,
                    key = "",
                    deleteStatus = ""
                )
                Log.d("TAG", "addIssuePost312: " + newDateItem)
                msgConversion.value?.add(newDateItem)
            }

            Log.d(
                "TAG",
                "addIssuePost312: " + msgConversion.value?.size + "------>" + msgConversion.value?.get(
                    msgConversion.value?.size?.minus(1) ?: -1
                )?.type?.intType
            )

            if ((msgConversion.value?.size ?: 0) > 0 &&
                (msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.type?.intType
                        == ChatMessageListItemType.SingleImageTypeSender().intType
                        || msgConversion.value?.get(
                    msgConversion.value?.size?.minus(1) ?: -1
                )?.type?.intType == ChatMessageListItemType.ImageListTypeSender().intType
                        /* msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.type?.intType == ChatMessageListItemType.AudioTypeSender().intType*/)
                && (issuePost.type.intType == ChatMessageListItemType.SingleImageTypeSender().intType ||
                        issuePost.type.intType == ChatMessageListItemType.ImageListTypeSender().intType)
            ) {
                msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.apply {
                    Log.d("TAG", "addIsssdffsdfuePost: " + filesArray)
                    if (filesArray?.size == 10) {
                        issuePost.type = ChatMessageListItemType.StringSenderType()
                        msgConversion.value?.add(issuePost)
                    } else {
                        val diff = getDifferenceOfYears(
                            this.date,
                            this.time,
                            issuePost.date,
                            issuePost.time
                        )
                        val ab: Long = 1000 * 60 * 10
                        if (diff > ab) msgConversion.value?.add(issuePost)
                        else if (filesArray == null) {

                            val newList = ArrayList<FileData>()
                            newList.add(FileData(this.message, this.key, issuePost.deleteStatus))
                            newList.add(
                                FileData(
                                    issuePost.message,
                                    issuePost.key,
                                    issuePost.deleteStatus
                                )
                            )
                            filesArray = newList
                            this.type = ChatMessageListItemType.ImageListTypeSender()
                            this.key = ""
                        } else {
                            filesArray!!.add(
                                FileData(
                                    issuePost.message,
                                    issuePost.key,
                                    issuePost.deleteStatus
                                )
                            )
                            this.key = ""
                            this.type = ChatMessageListItemType.ImageListTypeSender()
                            Log.d("TAG", "addIssdfsuePost: " + filesArray)

                        }
                    }
                }
                // msgConversion.value?.add(issuePost)
            } else if (
                (msgConversion.value?.size ?: 0) > 0 &&
                (msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.type?.intType
                        == ChatMessageListItemType.SingleImageTypeReceiver().intType
                        || msgConversion.value?.get(
                    msgConversion.value?.size?.minus(1) ?: -1
                )?.type?.intType == ChatMessageListItemType.ImageListTypeReceiver().intType /*|| msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.type?.intType == ChatMessageListItemType.AudioTypeReceiver().intType*/)
                && (issuePost.type.intType == ChatMessageListItemType.SingleImageTypeReceiver().intType ||
                        /*issuePost.type.intType == ChatMessageListItemType.AudioTypeReceiver().intType ||*/
                        issuePost.type.intType == ChatMessageListItemType.ImageListTypeReceiver().intType)
            ) {
                Log.d("TAG", "addIssuePost:321 " + issuePost.type)
                msgConversion.value?.get(msgConversion.value?.size?.minus(1) ?: -1)?.apply {
                    if (filesArray?.size == 10) {

                        issuePost.type = ChatMessageListItemType.StringReceiverType()
                        msgConversion.value?.add(issuePost)

                    } else {
                        val diff = getDifferenceOfYears(
                            this.date,
                            this.time,
                            issuePost.date,
                            issuePost.time
                        )

                        val ab: Long = 1000 * 60 * 10
                        Log.d("TAG", "adsdsfsdfdIssuePost: " + diff + "-->" + issuePost)
                        if (diff > ab) msgConversion.value?.add(issuePost)
                        else if (filesArray == null) {

                            val newList = ArrayList<FileData>()
                            newList.add(FileData(this.message, this.key, issuePost.deleteStatus))
                            newList.add(
                                FileData(
                                    issuePost.message,
                                    issuePost.key,
                                    issuePost.deleteStatus
                                )
                            )
                            filesArray = newList
                            this.type = ChatMessageListItemType.ImageListTypeReceiver()
                            this.key = ""
                        } else {
                            filesArray!!.add(
                                FileData(
                                    issuePost.message,
                                    issuePost.key,
                                    issuePost.deleteStatus
                                )
                            )
                            this.type = ChatMessageListItemType.ImageListTypeReceiver()
                            this.key = ""
                        }
                    }

                }
            } else {
                Log.d("TAG", "addIssuePost2714:2 " + issuePost.type)
                Log.d("TAG", "addIssuePosadasdst2714:2 " + issuePost.filesArray)


                if ((/*issuePost.type.intType == ChatMessageListItemType.SingleImageTypeSender().intType ||*/
                            issuePost.type.intType == ChatMessageListItemType.AudioTypeSender().intType)
                ) {
                    //issuePost.type = ChatMessageListItemType.StringSenderType()
                    issuePost.type = ChatMessageListItemType.AudioTypeSender()
                } else if ((issuePost.type.intType == ChatMessageListItemType.SingleImageTypeSender().intType)) {
                    Log.d("TAG", "addIssuePost: " + 123)
                    issuePost.type = ChatMessageListItemType.SingleImageTypeSender()
                } else if ((/*issuePost.type.intType == ChatMessageListItemType.SingleImageTypeReceiver().intType ||*/
                            issuePost.type.intType == ChatMessageListItemType.AudioTypeReceiver().intType)
                ) {
                    // issuePost.type = ChatMessageListItemType.StringReceiverType()
                    issuePost.type = ChatMessageListItemType.AudioTypeReceiver()
                } else if ((issuePost.type.intType == ChatMessageListItemType.SingleImageTypeReceiver().intType)) {
                    issuePost.type = ChatMessageListItemType.SingleImageTypeReceiver()
                    Log.d("TAG", "addIssuePost: " + 12)
                }

                Log.d("TAG", "addIssuePost2714: " + issuePost)

                msgConversion.value?.add(issuePost)

            }

            viewModelScope.launch(Dispatchers.Main) {
                msgConversion.notifyObserver()
                /*synchronized(msgConversion){
                    msgConversion.notify()
                }*/

            }

        } catch (e: Exception) {
        }
    }

    fun getMessageList() = viewModelScope.launch(Dispatchers.IO) {
        val queryCategory: Query =
            FirebaseDatabase.getInstance().reference.child(messageNode).child(nodeId)
        queryCategory.keepSynced(true)
        //local ma catch bne
        Log.d("TAG", "getMessageList294: " + queryCategory)

        queryCategory.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                try {

                    snapshot.getValue(MessageObject::class.java)?.let { data ->
                        var tempItemType: ChatMessageListItemType? = null
                        Log.d("TAG", "onChildAdd465ed: " + data.msgType)
                        if ((data.sendBy ?: 0) == currentUserId) {
                            when (data.msgType) {
                                MessageType.StringMessage().type -> tempItemType =
                                    ChatMessageListItemType.StringSenderType()

                                MessageType.ImageMessage().type -> tempItemType =
                                    ChatMessageListItemType.SingleImageTypeSender()

                                MessageType.AudioMessage().type -> tempItemType =
                                    ChatMessageListItemType.AudioTypeSender()
                            }
                        } else {
                            when (data.msgType) {

                                MessageType.StringMessage().type -> tempItemType =
                                    ChatMessageListItemType.StringReceiverType()

                                MessageType.ImageMessage().type -> tempItemType =
                                    ChatMessageListItemType.SingleImageTypeReceiver()

                                MessageType.AudioMessage().type -> tempItemType =
                                    ChatMessageListItemType.AudioTypeReceiver()
                            }
                        }

                        if (tempItemType != null) {

                            val messg = data.message ?: ""

                            Log.d("TAG", "onChildAddasaasded: " + tempItemType + "--->" + messg)
                            addIssuePost(
                                ChatMessageObject(
                                    tempItemType,
                                    message = messg,
                                    date = data.msgTimestamp?.toLong()?.toDateString() ?: "",
                                    time = data.msgTimestamp?.toLong()?.toTimeString() ?: "",
                                    key = snapshot.key.toString(),
                                    deleteStatus = data.deleteStatus ?: ""
                                )
                            )
                        }


                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var isNewData = false
                msgConversion.value?.apply {

                    for (i in indices) {

                        try {
                            if (this[i].key == snapshot.key) {

                                snapshot.getValue(MessageObject::class.java)?.let {
                                    this[i].deleteStatus = it.deleteStatus ?: ""

                                    if (
                                        this[i].type.intType == ChatMessageListItemType.AudioTypeSender().intType ||
                                        this[i].type.intType == ChatMessageListItemType.SingleImageTypeSender().intType ||
                                        this[i].type.intType == ChatMessageListItemType.ImageListTypeSender().intType
                                    ) {
                                        this[i].type = ChatMessageListItemType.StringSenderType()
                                    } else if (
                                        this[i].type.intType == ChatMessageListItemType.AudioTypeReceiver().intType ||
                                        this[i].type.intType == ChatMessageListItemType.SingleImageTypeReceiver().intType ||
                                        this[i].type.intType == ChatMessageListItemType.ImageListTypeReceiver().intType
                                    ) {
                                        this[i].type = ChatMessageListItemType.StringReceiverType()
                                    }

                                    Log.d("TAG", "onChildChanged: " + i + "->>" + this[1])
                                    msgConversion.value?.set(i, this[i])

                                    isNewData = true

                                }

                            } else {

                                this[i].filesArray?.let {

                                    for (z in it.indices) {
                                        if (it[z].fileKey == snapshot.key) {

                                            var oldList: List<FileData>?
                                            var newList: List<FileData>?

                                            val newSize = it.size - z - 1
                                            val tempPos: Int = i
                                            oldList = it.take(z)
                                            newList = it.takeLast(newSize)

                                            if (tempPos != -1) {
                                                val list11 = ArrayList<FileData>()
                                                val list22 = ArrayList<FileData>()

                                                oldList.let { thi -> list11.addAll(thi) }
                                                newList.let { thi -> list22.addAll(thi) }

                                                this[i].filesArray?.let {

                                                    this[i].filesArray!!.removeIf { filedata ->
                                                        filedata.fileKey != snapshot.key
                                                    }

                                                    if (this[i].type.intType == ChatMessageListItemType.AudioTypeReceiver().intType ||
                                                        this[i].type.intType == ChatMessageListItemType.SingleImageTypeReceiver().intType ||
                                                        this[i].type.intType == ChatMessageListItemType.ImageListTypeReceiver().intType
                                                    ) {
                                                        this[i].type =
                                                            ChatMessageListItemType.StringReceiverType()
                                                    } else if (this[i].type.intType == ChatMessageListItemType.AudioTypeSender().intType ||
                                                        this[i].type.intType == ChatMessageListItemType.SingleImageTypeSender().intType ||
                                                        this[i].type.intType == ChatMessageListItemType.ImageListTypeSender().intType
                                                    ) {
                                                        this[i].type =
                                                            ChatMessageListItemType.StringSenderType()
                                                    }

                                                }
                                                if (list22.isNotEmpty()) {
                                                    if (list22.size == 1) {

                                                        Log.d(
                                                            "TAG",
                                                            "onChildChanged: " + list22.map { it.fileString })

                                                        val newType =
                                                            if (this[i].type.intType == ChatMessageListItemType.ImageListTypeReceiver().intType) {
                                                                ChatMessageListItemType.SingleImageTypeReceiver()
                                                                /* if (list22[0].fileString.contains("audio")) {
                                                                     ChatMessageListItemType.AudioTypeReceiver()
                                                                 } else ChatMessageListItemType.SingleImageTypeReceiver()*/
                                                            } else if (this[i].type.intType == ChatMessageListItemType.ImageListTypeSender().intType) {
                                                                ChatMessageListItemType.SingleImageTypeSender()
                                                                /* if (list22[0].fileString.contains("audio")) {
                                                                     ChatMessageListItemType.AudioTypeSender()
                                                                 } else ChatMessageListItemType.SingleImageTypeSender()*/
                                                            } else null

                                                        newType?.let {
                                                            this.add(
                                                                tempPos + 1, ChatMessageObject(
                                                                    type = newType,
                                                                    message = list22[0].fileString,
                                                                    date = this[i].date,
                                                                    time = this[i].time,
                                                                    filesArray = null,
                                                                    isSelected = false,
                                                                    fileDownloadStatus = FileDownloadStatus.NotDownloaded,
                                                                    key = list22[0].fileKey,
                                                                    deleteStatus = list22[0].deleteStatus,
                                                                )
                                                            )
                                                        }

                                                    } else {
                                                        this.add(
                                                            tempPos + 1, ChatMessageObject(
                                                                type = this[i].type,
                                                                message = this[i].message,
                                                                date = this[i].date,
                                                                time = this[i].time,
                                                                filesArray = list22,
                                                                isSelected = this[i].isSelected,
                                                                fileDownloadStatus = this[i].fileDownloadStatus,
                                                                key = "nomeaning",
                                                                deleteStatus = this[i].deleteStatus,
                                                            )
                                                        )
                                                    }
                                                }
                                                if (list11.isNotEmpty()) {
                                                    if (list11.size == 1) {

                                                        val newType =
                                                            if (this[i].type.intType == ChatMessageListItemType.ImageListTypeReceiver().intType) {
                                                                ChatMessageListItemType.SingleImageTypeReceiver()
                                                                /* if (list11[0].fileString.contains("image")) {
                                                                     ChatMessageListItemType.AudioTypeReceiver()
                                                                 } else ChatMessageListItemType.SingleImageTypeReceiver()*/
                                                            } else if (this[i].type.intType == ChatMessageListItemType.ImageListTypeSender().intType) {
                                                                ChatMessageListItemType.SingleImageTypeSender()
                                                                /* if (list11[0].fileString.contains("image")) {
                                                                     ChatMessageListItemType.AudioTypeSender()
                                                                 } else ChatMessageListItemType.SingleImageTypeSender()*/
                                                            } else null

                                                        newType?.let {
                                                            this.add(
                                                                tempPos, ChatMessageObject(
                                                                    type = newType,
                                                                    message = list11[0].fileString,
                                                                    date = this[i].date,
                                                                    time = this[i].time,
                                                                    filesArray = null,
                                                                    isSelected = false,
                                                                    fileDownloadStatus = FileDownloadStatus.NotDownloaded,
                                                                    key = list11[0].fileKey,
                                                                    deleteStatus = list11[0].deleteStatus,
                                                                )
                                                            )
                                                        }

                                                    } else {
                                                        this.add(
                                                            tempPos, ChatMessageObject(
                                                                type = this[i].type,
                                                                message = this[i].message,
                                                                date = this[i].date,
                                                                time = this[i].time,
                                                                filesArray = list11,
                                                                isSelected = this[i].isSelected,
                                                                fileDownloadStatus = this[i].fileDownloadStatus,
                                                                key = "nomeaning",
                                                                deleteStatus = this[i].deleteStatus,
                                                            )
                                                        )
                                                    }
                                                }


                                            }
                                        }
                                    }

                                }

                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            isNewData = true
                        }

                        if (isNewData) break

                    }

                }

                if (isNewData) {
                    viewModelScope.launch(Dispatchers.Main) {
                        msgConversion.notifyObserver()
                      /*  synchronized(msgConversion){
                            msgConversion.notify()
                        }*/
                    }
                }

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private var uploadFileStatusList = ArrayList<UploadFileProgress>()

    data class UploadFileProgress(
        var data: UploadStatus,
        var key: Int,
    )

    sealed class UploadStatus {
        object Failed : UploadStatus()
        object Successful : UploadStatus()
        class InProgress(var progress: Int) : UploadStatus()
    }

    enum class SendFileType {
        Images, Audio
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendFiles(bitmaps: ArrayList<Uri>, sendFileType: SendFileType, context: Context) {
        uploadFileStatusList.clear()
        var selectedType: String? = null
        val typeName =
            when (sendFileType) {
                SendFileType.Images -> {
                    selectedType = MessageType.ImageMessage().type
                    "images"
                }

                else -> {}
            }

        GlobalScope.launch {
            for (i in bitmaps.indices) {
                Log.d("TAG", "sendFiles693: " + i)

                uploadFileStatusList.add(UploadFileProgress(UploadStatus.InProgress(0), i))

                var name = "$typeName${System.currentTimeMillis()}"

                viewModelScope.launch {
                    repository.sendChatMessage(
                        currentUserId.toString(),
                        "Media",
                        otherUserId.toString()
                    )
                }


                nodeId2 =
                    generateChatNode(
                        currantIdOther.toIntOrNull() ?: 0,
                        userIdOther.toIntOrNull() ?: 0
                    )

                storageDatabase = FirebaseStorage.getInstance().reference
                database = FirebaseDatabase.getInstance().reference

                storageDatabase.child("images").child(name)
                    .putFile(bitmaps[i])
                    .addOnProgressListener {
                        val percent = ((it.bytesTransferred * 100) / it.totalByteCount).toInt()
                        uploadFileStatusList[i].data = UploadStatus.InProgress(percent)

                        if (percent == 100) {


                            if (it.metadata != null) {
                                if (it.metadata!!.reference != null) {
                                    val result: Task<Uri> = it.storage.downloadUrl
                                    result.addOnSuccessListener { uri ->
                                        val imageUrl = uri.toString()
                                        val messageObject = MessageObject(
                                            currantIdOther.toIntOrNull() ?: 0,
                                            System.currentTimeMillis().toString(),
                                            selectedType,
                                            imageUrl,
                                            ""
                                        )



                                        database.child(messageNode).child(nodeId2).push()
                                            .setValue(messageObject)

                                        Log.d("TAG", "seddddndFiles: " + imageUrl)
                                        if (java.io.File(imageUrl).exists()) {
                                            java.io.File(imageUrl).delete()
                                        }


                                    }
                                }
                            }

                            uploadFileStatusList[i].data = UploadStatus.Successful
                        }
                        NotificationHelper(uploadFileStatusList[i], name, context)
                    }
            }
        }

    }


    fun sendAudio(
        audio: Uri,
        sendFileType: SendFileType,
        context: Context,
    ) {
        // uploadFileStatusList.clear()
        var selectedType: String? = null
        val typeName =
            when (sendFileType) {
                SendFileType.Images -> {
                    selectedType = MessageType.ImageMessage().type
                    "images"
                }

                SendFileType.Audio -> {
                    selectedType = MessageType.AudioMessage().type
                    "audios"
                }

            }


        GlobalScope.launch {
            val name = "$typeName${System.currentTimeMillis()}"

            viewModelScope.launch {
                repository.sendChatMessage(
                    currentUserId.toString(),
                    "Recoder",
                    otherUserId.toString()
                )
            }


            storageDatabase.child(typeName).child(name)
                .putFile(audio)
                .addOnProgressListener {
                    if (it.metadata != null) {
                        if (it.metadata!!.reference != null) {
                            val result: Task<Uri> = it.storage.downloadUrl
                            result.addOnSuccessListener { uri ->
                                val imageUrl = uri.toString()
                                val messageObject = MessageObject(
                                    currentUserId,
                                    System.currentTimeMillis().toString(),
                                    selectedType,
                                    imageUrl,
                                    ""
                                )


                                database.child(messageNode).child(nodeId).push()
                                    .setValue(messageObject)

                                if (File(imageUrl).exists()) {
                                    File(imageUrl).delete()
                                }

                            }
                        }
                    }


                }

        }
    }


    private var userMsgDataList: ArrayList<ConversionDataRsponse>? = null
    fun getUserListOfMessage(
        onListReceived: (ArrayList<ConversionDataRsponse>?, status: Boolean) -> Unit,
        context: Context,
    ) =
        viewModelScope.launch {
            val userObj = context.dataStoreGetUserData().firstOrNull()
            val uid = userObj?.userId


            when (val data =
                repository.getUserListOfMessage(uid ?: 0)) {
                is Resource.Error -> {
                    onListReceived.invoke(null, false)

                }

                is Resource.Success -> {

                    userMsgDataList = data.data?.data


                    if (!userMsgDataList.isNullOrEmpty()) {
                        onListReceived.invoke(
                            userMsgDataList!!, true
                        )


                    } else {

                        onListReceived.invoke(null, false)

                    }

                }
            }
        }

    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer

    private var time2: Duration = Duration.ZERO
    private lateinit var timer2: Timer

    var seconds by mutableStateOf("00")
    var minutes by mutableStateOf("00")
    var isPlaying by mutableStateOf(false)

    var seconds2 by mutableStateOf("00")
    var minutes2 by mutableStateOf("00")
    var isPlaying2 by mutableStateOf(false)

    fun start() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(1.seconds)
            updateTimeStates()
        }
        isPlaying = true
    }

    private fun updateTimeStates() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@GetMessageListViewModel.seconds = seconds.pad()
            this@GetMessageListViewModel.minutes = minutes.pad()
        }
    }

    fun start2() {
        timer2 = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time2 = time2.plus(1.seconds)
            updateTimeStates2()
        }
        isPlaying2 = true
    }
    private fun updateTimeStates2() {
        time2.toComponents { hours, minutes2, seconds2, _ ->
            this@GetMessageListViewModel.seconds2 = seconds2.pad()
            this@GetMessageListViewModel.minutes2 = minutes2.pad()
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2, '0')
    }

    fun pause() {
        timer.cancel()
        isPlaying = false
    }

    fun stop() {
        pause()
        time = Duration.ZERO
        updateTimeStates()
    }

    fun stop2() {
        pause()
        time2 = Duration.ZERO
        updateTimeStates2()
    }

    /*fun idGet(i: Int, i1: Int) {
        //  this.currentUserId2.value = i.toString()
        //  this.otherUserId2.value = i1.toString()
        //  Log.d("TAG", "idGet: " + currentUserId2.value + "-->" + otherUserId2.value)
    }*/
    var status = OnlineStatusObj("", "")
    fun getLastSeenDetails(userId: String, listener: OnOnlineStatusListener) {
        status = OnlineStatusObj("", "")

        Log.d("TAG", "getLastSeesfsnDetails: " + userId)
        val queryCategory: Query =
            FirebaseDatabase.getInstance().reference.child("onlineStatus").child(userId)
        queryCategory.keepSynced(true)
        queryCategory.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TAG", "onChildAdded: >>$snapshot")

                try {
                    snapshot.getValue(OnlineStatusObj::class.java)?.let {
                        Log.d("TAG", "onDatasdChangesddf: " + it)
                        status.lastSeen = it.lastSeen
                        status.status = it.status
                    }
                    listener.onStatusChanged(status)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    interface OnOnlineStatusListener {
        fun onStatusChanged(data: OnlineStatusObj)
    }


    fun downloadImage(imageUrl: String, context: Context, folderName: String) {
        /* val request = ImageRequest.Builder(context)
             .data(imageUrl)
             .build()*/

        //  val abc=request.context.cacheDir.absolutePath
        Log.d("TAG", "downloadImage: " + imageUrl)

        val downloadManager = context.getSystemService<DownloadManager>()

        val filepath = File(imageUrl).absolutePath
        Log.d("TAG", "downloadImage: " + filepath)


        /*val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "$folderName/$filepath")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
*/
        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Osomate")


        Log.d("TAG", "downloadImage: " + request)

        downloadManager?.enqueue(request)


    }

    sealed class ReadMsg {
        class Success(val result: String) : ReadMsg()
        class Failure(val errorText: String) : ReadMsg()
        object Loading : ReadMsg()
        object Empty : ReadMsg()
    }

    private val _readConversion = MutableStateFlow<ReadMsg>(
        ReadMsg.Empty
    )

    fun readmsg(currantId: String, otherId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _readConversion.value = ReadMsg.Loading
            when (val call = repository.readMessage(currantId, otherId)) {
                is Resource.Error -> {
                    _readConversion.value = ReadMsg.Failure(call.message ?: "")
                }

                is Resource.Success -> {
                    if (call.data == null) {
                        _readConversion.value = ReadMsg.Failure(call.message ?: "")
                    } else {
                        _readConversion.value = ReadMsg.Success(call.data)
                    }
                }
            }
        }
    }

}









