package com.sanxingrenge.benben.constant

import android.os.Build
import android.os.Environment
import android.text.format.DateUtils
import android.util.Log
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sanxingrenge.benben.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

sealed class ImageBorderOption {
    object NOTHING : ImageBorderOption()
    object NOTCROP : ImageBorderOption()
    object CIRCLE : ImageBorderOption()
    object NORMAL : ImageBorderOption()
    class RadiusEditProfile(val radiusNew: Int = 10000) : ImageBorderOption()
}

object FormatDateUseCase {

    fun Long.getTimeAgoFromLong(): String {

        val ago = DateUtils.getRelativeTimeSpanString(
            this,
            System.currentTimeMillis(),
            DateUtils.DAY_IN_MILLIS
        ).toString()
//MINUTE_IN_MILLIS
        Log.d("TAG", "cccddd: $ago")
        return if(ago=="Today" || ago=="Yesterday") ago
        else getDate(this,"dd-MM-yyyy")?:""

        Log.d("TAG", "cdddccddd: $ago")

        /*  ago = ago.replace("hours", "hrs")
          ago = ago.replace("hour", "hr")
          ago = ago.replace("minutes", "mins")
          ago = ago.replace("minute", "min")*/

    }

    private fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getDifferenceOfYears(
        dateStr1: String,
        timeStr1: String,
        dateStr2: String,
        timeStr2: String
    ): Long {

        Log.d("TAG", "getDifferenceOfYears: testsajhd>>$dateStr1>>$timeStr1>>$dateStr2>>$timeStr2")

        val formatter1 = SimpleDateFormat("dd-MM-yyyy")
        val date1 = formatter1.parse(dateStr1)
        val date2 = formatter1.parse(dateStr2)

        val formatter2 = SimpleDateFormat("hh:mm aa")
        val time1 = formatter2.parse(timeStr1)
        val time2 = formatter2.parse(timeStr2)

        return ((((date2?.time ?: 0) + (time2?.time ?: 0)) - ((date1?.time ?: 0) + (time1?.time
            ?: 0))))
    }


    fun Long.toDateString(): String {
        val df = SimpleDateFormat("dd-MM-yyyy")
        return df.format(this)
    }

    fun Long.toTimeString(): String {
        val df = SimpleDateFormat("hh:mm aa")
        return df.format(this)
    }

    fun ImageView.loadImageWithGlide(
        urlLink: String,
        loadOption: ImageBorderOption = ImageBorderOption.NORMAL
    ) {
        //  logIt("loadImageWithGlide: testAb>>$urlLink")

        if (urlLink == "") return

        when (loadOption) {
            is ImageBorderOption.NOTHING -> {
                Glide.with(context).load(urlLink)
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 8f
                        centerRadius = 40f
                        setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.transparent, null))
                        start()
                    })
                    .into(this)
            }
            is ImageBorderOption.NORMAL -> {
                Glide.with(context).load(urlLink)
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 8f
                        centerRadius = 40f
                        setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.transparent, null))
                        start()
                    })
                    .centerCrop()
                    .into(this)
            }
            is ImageBorderOption.NOTCROP -> {
                Glide.with(context).load(urlLink)
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 8f
                        centerRadius = 40f
                        setColorSchemeColors(ResourcesCompat.getColor(resources,R.color.transparent, null))
                        start()
                    })
                    .into(this)
            }
            is ImageBorderOption.CIRCLE -> {
                Glide.with(context).load(urlLink)
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 8f
                        centerRadius = 40f
                        setColorSchemeColors(ResourcesCompat.getColor(resources,R.color.transparent, null))
                        start()
                    })
                    .circleCrop()
                    .into(this)
            }
            is ImageBorderOption.RadiusEditProfile -> {

                val rad:Int=if(loadOption.radiusNew==10000)resources.getDimension(R.dimen.radiusImage).toInt() else loadOption.radiusNew

                Glide.with(context).load(urlLink)
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 8f
                        centerRadius = 40f
                        setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.transparent, null))
                        start()
                    })
                    .centerCrop()
                    .transform(CenterCrop(), RoundedCorners(rad))
                    .into(this)
            }
        }
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
    }


    fun isUrlVideo(string: String): Boolean {
        return string.contains("videos")
    }



    fun getFilePath(fileName: String, filePath: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val path =
                "$filePath/$fileName"
            path
        } else {
            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            target.path
        }
    }

    fun getTimeAgoFromDate(dateStr: String,showAgo:Boolean=true): String {
        Log.d("TAG", "getTimeAgoFromDate31213:185 "+dateStr)
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val date = formatter.parse(dateStr)
            Log.d("TAG", "getTimeAgoFromDate31213:190 "+date)
            val c = Calendar.getInstance()
            Log.d("TAG", "getTimeAgoFromDate31213:192 "+c)
            c.timeZone = TimeZone.getTimeZone("UTC")

            val ago = DateUtils.getRelativeTimeSpanString(
                date?.time ?: 0,
                c.timeInMillis,
                DateUtils.DAY_IN_MILLIS
            ).toString()

            Log.d("TAG", "getTimeAgoFromDate31213:201 "+date?.time+"--->"+ago)
            return if(ago=="Today" || ago=="Yesterday") ago
            else getDate(date?.time ?: 0,"dd-MM-yyyy")?:""

        }catch (e:Exception){
            Log.d("TAG", "getTimeAgoFromDate: erroe while getting date")
            return ""
        }

    }

    fun getTimeAgoFromDateMSGOnly(dateStr: String): String {
       // val formatter = SimpleDateFormat("dd-MM-yyyy")
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val date = formatter.parse(dateStr)

        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("UTC")

        val ago = DateUtils.getRelativeTimeSpanString(
            date?.time ?: 0,
            c.timeInMillis,
            DateUtils.DAY_IN_MILLIS
        ).toString()

        /*  if (ago.contains("hours") ||
              ago.contains("hour") ||
              ago.contains("minutes") ||
              ago.contains("minute") ||
              ago.contains("second") ||
              ago.contains("seconds")
          ) {
              ago = "Today"
          }*/

        return if (ago == "Today" || ago == "Yesterday") ago
        else getDate(date?.time ?: 0, "dd-MM-yyyy") ?: ""
    }


}