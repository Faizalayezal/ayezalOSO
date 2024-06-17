package com.sanxingrenge.benben.constant

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.format.DateUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FilenameUtils
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.FormatDateUseCase.getTimeAgoFromLong
import com.sanxingrenge.benben.dataModel.UserDataObject
import com.sanxingrenge.benben.responseModel.AnimalSign
import com.sanxingrenge.benben.screens.loggedInModule.StoreId
import com.sanxingrenge.benben.viewModel.GetMessageListViewModel
import com.sanxingrenge.benben.viewModel.OnlineStatusObj
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.TimeZone

interface OnEditFlowListener {
    fun onEditSelected(data: Boolean)
}

@RequiresApi(Build.VERSION_CODES.O)
object Constns {

    var EditListener: OnEditFlowListener? = null
    var currantIdOther = ""
    var otherUserImage = ""

    var lastmessage = ""
    var emailSee = ""
    var pwdSee = ""
    var ImageShowEdit: String? = null
    var IsFilter: Boolean? = false
    var chatImageData = ""
    var backemail = ""
    var chatToProfile = ""
    var backepersonality = ""
    var userDataObject: UserDataObject? = null
    var audioFile: File? = null
    var playerAudio: MediaPlayer? = null
    var flow = ""
    var reciveuid = ""
    var listMessage: List<ChatMessageObject> = listOf()

    // var listMessage: ArrayList<ChatMessageObject> = arrayListOf()
    //val listMessage: MutableList<ChatMessageObject> = ArrayList()
    var onComplete: Boolean? = null
    var counter1: Int = 0
    var counter3: Int = 0
    var GoogleId = ""
    var FaceBookId = ""


    val producatId = MutableLiveData<String>()


    val image1 = MutableLiveData<String>()
    val image2 = MutableLiveData<String>()
    val image3 = MutableLiveData<String>()
    val image4 = MutableLiveData<String>()
    val image5 = MutableLiveData<String>()
    val image6 = MutableLiveData<String>()
    var store: StoreId? = null

    val startDate = LocalDate.now()

    val lastMonth = startDate.plusMonths(1)
    val que = startDate.plusMonths(4)
    val years = startDate.plusMonths(12)

    var userImage1 = MutableLiveData<String>()
    var userImage2 = MutableLiveData<String>()
    var userImage3 = MutableLiveData<String>()
    var userImage4 = MutableLiveData<String>()


    var receiver_u_id = MutableLiveData<String>()
    var sender_id = MutableLiveData<String>()
    var channel_name = MutableLiveData<String>()
    var sender_name = MutableLiveData<String>()
    var image = MutableLiveData<String>()
    var receiver_token = MutableLiveData<String>()

    var userPos1: Int = 0
    var userPos1Register: Int = 0
    var ImageIdRemove = ""

    var userIdOther = ""

    var otherUserImageSingleCAll = ""
    var otherUserNameSingleCAll = ""

    var emailLoginStore:String?=""
    var SocialLoginStatus = false
    var SocialLoginMessage = ""
    var emailLoginUserExist = 2
    var FBid = ""
    var FBStatus = true
    var isFirstTime = true
    var FBName = ""
    var ApiTime = true




    val onlineListener = object : GetMessageListViewModel.OnOnlineStatusListener {
        @SuppressLint("SetTextI18n")
        override fun onStatusChanged(data: OnlineStatusObj) {
            val lastTime = System.currentTimeMillis()
            Log.d("TAG", "onStatusChanged: testvaluechanged>>" + data.status + ">>" + data.lastSeen)
            Log.d("TAG", "onStatusChanged: " + data)

            if (data.status == "1") {
                lastmessage = "Online"
            } else {
                lastmessage =
                    "Last seen ${(data.lastSeen ?: "").toLong().getTimeAgoFromLong()}"
                //  binding?.txtLastSeenStatus?.text ="Last seen ${(data.lastSeen ?: "0")+lastTime})}"
            }

        }
    }


    fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    fun generateChatNode(currentUserId: Int, otherUserId: Int): String =
        if (currentUserId < otherUserId) "$currentUserId-$otherUserId"
        else "$otherUserId-$currentUserId"


    fun scaleBitmap(input: Bitmap, maxBytes: Long): Bitmap? {

        val currentWidth = input.width
        val currentHeight = input.height
        val currentPixels = currentWidth * currentHeight
        // Get the amount of max pixels:
        // 1 pixel = 4 bytes (R, G, B, A)
        val maxPixels = maxBytes / 4 // Floored
        if (currentPixels <= maxPixels) {
            // Already correct size:
            return input
        }
        // Scaling factor when maintaining aspect ratio is the square root since x and y have a relation:
        val scaleFactor = Math.sqrt(maxPixels / currentPixels.toDouble())
        val newWidthPx = Math.floor(currentWidth * scaleFactor).toInt()
        val newHeightPx = Math.floor(currentHeight * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(input, newWidthPx, newHeightPx, true)
    }

    fun getImageUri(inImage: Bitmap, activity: Activity): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) saveImageInQ(inImage, activity)
        else saveTheImageLegacy(inImage)
    }

    fun saveTheImageLegacy(bitmap: Bitmap): Uri? {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, "" + System.currentTimeMillis() + "file")
        val fos = FileOutputStream(image)
        fos.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

        return Uri.fromFile(image)
    }

    @SuppressLint("InlinedApi")
    fun saveImageInQ(bitmap: Bitmap, activity: Activity): Uri? {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream?
        var imageUri: Uri?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        //use application context to get contentResolver
        val contentResolver = activity.contentResolver

        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }

        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        imageUri?.let { contentResolver.update(it, contentValues, null, null) }

        return imageUri
    }


    private fun getMonth(date: String): Int {
        return date.split("-")[0].toInt() - 1
    }

    private fun getDay(date: String): Int {
        return date.split("-")[1].toInt()
    }


    fun calculateAnimalSign(year: Int): AnimalSign {
        val animalSigns = listOf(
            AnimalSign("Rat", 2020),
            AnimalSign("Ox", 2021),
            AnimalSign("Tiger", 2022),
            AnimalSign("Rabbit", 2023),
            AnimalSign("Dragon", 2024),
            AnimalSign("Snake", 2025),
            AnimalSign("Horse", 2026),
            AnimalSign("Goat", 2027),
            AnimalSign("Monkey", 2028),
            AnimalSign("Rooster", 2029),
            AnimalSign("Dog", 2030),
            AnimalSign("Pig", 2031)
        )
        var index = (year - 1900) % 12

        Log.d("TAG", "calculateAnimalSign187: " + index)
        if (index == -4) {
            index = 9
        }
        return animalSigns[index]

    }

    fun getZodiacList(day: Int, month: Int): String {
        var ZodicJings = ""

        if (month == 0) {
            if (day < 20) {
                ZodicJings = "Capricorn"
            } else {
                ZodicJings = "Aquarius"

            }
        } else if (month == 1) {
            if (day < 20) {
                ZodicJings = "Aquarius"
            } else {
                ZodicJings = "Pisces"

            }
        } else if (month == 2) {
            if (day < 20) {
                ZodicJings = "Pisces"
            } else {
                ZodicJings = "Aries"

            }
        } else if (month == 3) {
            if (day < 20) {
                ZodicJings = "Aries"
            } else {
                ZodicJings = "Taurus"

            }
        } else if (month == 4) {
            if (day < 20) {
                ZodicJings = "Taurus"
            } else {
                ZodicJings = "Gemini"

            }
        } else if (month == 5) {
            if (day < 20) {
                ZodicJings = "Gemini"
            } else {
                ZodicJings = "Cancer"

            }
        } else if (month == 6) {
            if (day < 20) {
                ZodicJings = "Cancer"
            } else {
                ZodicJings = "Leo"

            }
        } else if (month == 7) {
            if (day < 20) {
                ZodicJings = "Leo"
            } else {
                ZodicJings = "Virgo"

            }
        } else if (month == 8) {
            if (day < 20) {
                ZodicJings = "Virgo"
            } else {
                ZodicJings = "Libra"

            }
        } else if (month == 9) {
            if (day < 20) {
                ZodicJings = "Libra"
            } else {
                ZodicJings = "Scorpio"

            }
        } else if (month == 10) {
            if (day < 20) {
                ZodicJings = "Scorpio"
            } else {
                ZodicJings = "Sagittarius"

            }
        } else if (month == 11) {
            if (day < 20) {
                ZodicJings = "Sagittarius"
            } else {
                ZodicJings = "Capricorn"

            }
        }


        return ZodicJings

    }


    fun getImageSizeInMB(file: File): Double {
        Log.d("TAG", "getImageSizeInMB:234 " + file.length())
        val fileSizeInBytes = file.length()
        Log.d("TAG", "getImageSizeInMB:236 " + fileSizeInBytes)
        val fileSizeInKB = fileSizeInBytes / 1024.0
        Log.d("TAG", "getImageSizeInMB:238 " + fileSizeInKB)
        val fileSizeInMB = fileSizeInKB / 1024.0
        Log.d("TAG", "getImageSizeInMB: " + fileSizeInMB)
        return fileSizeInMB
    }

    fun getTimeAgoFromDateOnly(dateStr: String): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
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

    private fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getFileName(string: String): String {
        try {
            val newName =
                FilenameUtils.getName(string)
                    .replace("audios/", "")
                    .replace("audios%2F", "")
            return newName.split("?")[0]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun convertDateFormat(inputDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("MM-dd-yyyy")
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return "" // Handle the exception as needed
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun attachMovableView(): View.OnTouchListener {
        return View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height / 2 - 100
                view.x = motionEvent.rawX - view.width / 2
            }

            true

        })
    }

    fun ImageView.setDrawable(int: Int?, loadOption: ImageBorderOption = ImageBorderOption.NORMAL) {
        if (int == null) setImageDrawable(null)

        when (loadOption) {
            is ImageBorderOption.NORMAL -> {
                Glide.with(context).load(int)
                    .into(this)
            }

            is ImageBorderOption.CIRCLE -> {
                Glide.with(context).load(int)
                    .circleCrop()
                    .into(this)
            }

            is ImageBorderOption.RadiusEditProfile -> {
                val rad: Int =
                    if (loadOption.radiusNew == 10000) resources.getDimension(R.dimen.radiusImage)
                        .toInt() else loadOption.radiusNew

                Glide.with(context).load(int)
                    .centerCrop()
                    .transform(CenterCrop(), RoundedCorners(rad))
                    .into(this)
            }

            else -> {}
        }
    }

    fun SnackbarFuncation(activity:Activity, text:String, context: Context, view:View, color:Int){
        Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_LONG
        ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).setDuration(5000).setActionTextColor(
            Color.GREEN).setBackgroundTint(color).setAction("Settings") {
            // Responds to click on the action
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            context.startActivity(intent)
            activity.finish()
        }.show()
    }




}