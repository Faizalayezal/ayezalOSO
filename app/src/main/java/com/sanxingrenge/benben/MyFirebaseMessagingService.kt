package com.sanxingrenge.benben

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sanxingrenge.benben.constant.Constns
import com.sanxingrenge.benben.constant.Constns.channel_name
import com.sanxingrenge.benben.constant.Constns.image
import com.sanxingrenge.benben.constant.Constns.receiver_token
import com.sanxingrenge.benben.constant.Constns.receiver_u_id
import com.sanxingrenge.benben.constant.Constns.sender_id
import com.sanxingrenge.benben.constant.Constns.sender_name
import com.sanxingrenge.benben.retrofilt.DefaultMainRepo
import com.sanxingrenge.benben.screens.SuperActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: DefaultMainRepo
    var jsonNotificationData: JSONObject? = null
    var requestFrom: MutableLiveData<String> = MutableLiveData()
    var username: MutableLiveData<String> = MutableLiveData()
    val handler = Handler(Looper.getMainLooper())

    override fun onNewToken(token: String) {
        super.onNewToken(token)


    }

    //apda phone ma msg aayvo
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val data = remoteMessage.notification?.title
        val dataReal = (remoteMessage.data)["custom"]

        Log.d(
            "TAG",
            "onMessageReceived:58 -->intentdata" + data
        )
        buildNotification(remoteMessage)

    }

    fun getLauncherClassName(context: Context): String? {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageName = context.packageName
        val resolveInfos = pm.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfos) {
            val activityInfo = resolveInfo.activityInfo
            if (activityInfo.packageName == packageName) {
                return activityInfo.name
            }
        }
        throw RuntimeException("Unable to get the launcher activity name")
    }


    private val notificationCancelTime = 30

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildNotification(remoteMessage: RemoteMessage) {
        val dataReal = (remoteMessage.data)["custom"]

        val notificationChannelId = "1"
        val mBuilder = NotificationCompat.Builder(this)
        //mBuilder.setNumber(10)
        //clicke notification pr kri etle splash activity ma aave


        var intents = Intent(applicationContext, SuperActivity::class.java)
        intents.putExtra("custom", dataReal.toString())
       /* handler.post {
            try {
                val jsonObject = JSONObject(dataReal)
                receiver_u_id.value = jsonObject.getString("receiver_u_id")
                sender_id.value = jsonObject.getString("sender_id")
                channel_name.value = jsonObject.getString("channel_name")
                sender_name.value = jsonObject.getString("sender_name")
                image.value = jsonObject.getString("image")
                receiver_token.value = jsonObject.getString("receiver_token")

                intents.putExtra("sender_id", sender_id.value.toString())
                intents.putExtra("channel_name", channel_name.value.toString())
                intents.putExtra("receiver_u_id", receiver_u_id.value.toString())
                intents.putExtra("sender_name", sender_name.value.toString())
                intents.putExtra("image", image.value.toString())
                intents.putExtra("receiver_token", receiver_token.value.toString())
                Log.d(
                    "TAG",
                    "onMessageReceived:96 " + dataReal
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }*/

        //intents.putExtra("custom", dataReal.toString())


        /* val resultIntent = Intent(applicationContext, SuperActivity::class.java)
         resultIntent.putExtra("custom", dataReal.toString())
         resultIntent.action = System.currentTimeMillis().toString()
         resultIntent.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES)
         resultIntent.flags =
             Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK


         val intent: PendingIntent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
             PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_MUTABLE)
         } else {
             PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT)
         }*/

        intents.action = System.currentTimeMillis().toString()
        intents.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES)
        intents.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK


        val intent: PendingIntent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, intents,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        }


        val title = remoteMessage.notification!!.title
        val message = remoteMessage.notification!!.body

        mBuilder.setSmallIcon(R.drawable.osomatelogo)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(message)
        // mBuilder.setNumber(count?:0)
        val icon = BitmapFactory.decodeResource(resources, R.drawable.osomatelogo)
        // mBuilder.setLargeIcon(icon)
        mBuilder.setSmallIcon(R.drawable.osomatelogo)
        mBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(message))
        mBuilder.setAutoCancel(true)
        mBuilder.priority = Notification.PRIORITY_HIGH
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS)
        mBuilder.setDefaults(Notification.DEFAULT_ALL)


        mBuilder.setTimeoutAfter(notificationCancelTime * 1000L)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mBuilder.setCategory(Notification.CATEGORY_CALL)
        }
        mBuilder.setContentIntent(intent)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //mNotificationManager.notify(1, mBuilder.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                notificationChannelId,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mBuilder.setChannelId(notificationChannelId)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        mNotificationManager.notify(1, mBuilder.build())

    }
}
