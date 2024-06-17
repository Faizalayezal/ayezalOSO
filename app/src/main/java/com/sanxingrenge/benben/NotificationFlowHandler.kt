package com.sanxingrenge.benben

import android.content.Intent
import android.util.Log
import com.google.gson.Gson

private const val TAG = "NotificationFlowHandler"


//notification click mange krva mate
class NotificationFlowHandler {

    sealed class FlowTracker{
        class FirstTimeSingleVideoCall(val data: FirstNotificationSingleCallData):FlowTracker()
    }
    var flowTracker : FlowTracker? = null

    fun fetchDataFirstTime(intent: Intent) {

        Log.d(TAG, "fetchDataFirstTime: testRetrivedData>>" + intent.getStringExtra("custom"))


        val privatetestModel = Gson().fromJson(
            intent.getStringExtra("custom"),
            FirstNotificationSingleCallCustom::class.java
        )

        if (
            privatetestModel?.message?.user_id != null &&
            privatetestModel.message.channel_name != null &&
            privatetestModel.message.user_name != null &&
            privatetestModel.message.user_image != null &&
            privatetestModel.message.created_at != null &&
            privatetestModel.message.receiver_u_id != null
        ){
            flowTracker = FlowTracker.FirstTimeSingleVideoCall(privatetestModel.message)
        }


        Log.d(TAG, "fetchNormalNotificationData: testFLowAb>40>$privatetestModel.message")



    }

    fun applyNotificationData(intent: Intent) : Intent {

        when(val item = flowTracker){
            is FlowTracker.FirstTimeSingleVideoCall -> {
                intent.putExtra("notificationSingleData",item.data)
            }
            null -> Unit
        }

        return intent
    }

    fun fetchNormalNotificationData(intent: Intent?) {
        val SingleitemData = intent?.getSerializableExtra("notificationSingleData") as? FirstNotificationSingleCallData

        Log.d(TAG, "fetchNormalNotificationData: testFLowAb>61>$SingleitemData")

        SingleitemData?.let{
            flowTracker = FlowTracker.FirstTimeSingleVideoCall(it)
        }


    }

}


private data class FirstNotificationSingleCallCustom(
    val message: FirstNotificationSingleCallData?
)

data class FirstNotificationSingleCallData(
    val channel_name: String?,
    val user_image: List<ImageSingleCallData>?,
    val user_name: String?,
    val user_id: Int?,
    val created_at: String?,
    val reaciver_token: String?,
    val receiver_u_id: String?,

    ):java.io.Serializable

data class ImageSingleCallData(
    val created_at: String?,
    val updated_at: String?,
    val user_id: Int?,
    val id: Int?,
    val url: String?,
    val order: String?,
):java.io.Serializable