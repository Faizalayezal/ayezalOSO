package com.sanxingrenge.benben.constant

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.net.URI

class ImagePickerHelper(private val fileUri: Uri, val activity: Activity) {
    private var bitmapTop:Bitmap?=null

    fun getBitmap():Bitmap?{
        try {
            var bitmap =MediaStore.Images.Media.getBitmap(
                activity.contentResolver,
                fileUri
            )

            try {
                val juri = URI(fileUri.toString())

                val ei = ExifInterface(File(juri).absolutePath)
                val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

                bitmap =
                    when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                        ExifInterface.ORIENTATION_NORMAL -> bitmap
                        else -> bitmap
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            bitmapTop=bitmap
            return bitmapTop
        } catch (e: Exception) {
            //handle exception
            e.printStackTrace()
        }

        return bitmapTop
    }
}
fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}