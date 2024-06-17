import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.sanxingrenge.benben.R

private const val TAG = "LocationPickerHelper"

class LocationPickerHelper {

    var activity: Activity? = null

    fun initialize(activity: Activity) {
        this.activity = activity
        val apiKey = activity.getString(R.string.google_search_api_key)
        Log.d(TAG, "initialize:23 "+Places.isInitialized())
        if (!Places.isInitialized()) {
            Places.initialize(activity, apiKey)
        }
        Places.createClient(activity)
    }

    fun openLocationPicker(resultLauncher: ActivityResultLauncher<Intent>) {
        if (activity == null) {
            return
        }

        val fields: List<Place.Field> = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val placeFilter = Place.Type.MOSQUE
        val intent: Intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        ).build(activity!!)
        //.setCountry("NG") //NIGERIA
        resultLauncher.launch(intent)
    }

    fun getDataFromResult(
        result: ActivityResult?, setResult: (
            address: String,
            lat: String,
            long: String,
        ) -> Unit
    ) {
        val data: Intent? = result?.data

        data?.let {
            val place = Autocomplete.getPlaceFromIntent(data)

            //var address = place.address ?: ""
            var address = place.name ?: ""
            address = removeLocationCodeFromAddress(address)

            val latStr = (place.latLng?.latitude ?: 0.0).toString()
            val longStr = (place.latLng?.longitude ?: 0.0).toString()

            LatLongTrimmerHelper().trim(latStr, longStr) { newLat, newLong ->
                setResult(
                    address,
                    newLat,
                    newLong
                )

                Log.d(TAG, "Place: 166>>$newLat, $newLong,$address")


            }

        }
    }

    private fun removeLocationCodeFromAddress(string: String): String {
        var address = string
        //removing 7RR2+9RC place code from address
        val list = address.split(",")

        list.forEach { s ->
            Log.d(
                TAG,
                "getDataFromResult: testlogic>>" + s.getOrNull(4) + ">>" + s.getOrNull(5) + ">>" + s
            )

            if ((s.getOrNull(4) ?: "").toString() == "+") {
                address = address.replace(s, "")
            } else if ((s.getOrNull(5) ?: "").toString() == "+") {
                address = address.replace(s, "")
            }

        }
        address = address.replaceFirst(",,", "")
        if (address.getOrNull(0).toString() == ",") {
            address = address.replaceFirst(", ", "")
        }
        return address
    }
}

class LatLongTrimmerHelper {
    fun trim(lat: String, long: String, result: (newLat: String, newLong: String) -> Unit) {
        var latStr = lat
        var longStr = long

        val strOne = latStr.split(".").lastOrNull() ?: ""
        val lengthOne = strOne.length
        latStr = latStr.replace(strOne, "")

        val strTwo = longStr.split(".").lastOrNull() ?: ""
        val lengthTwo = strTwo.length
        longStr = longStr.replace(strTwo, "")

        if (lengthOne == 0) latStr += "000000"
        else if (lengthOne == 1) latStr += strOne + "00000"
        else if (lengthOne == 2) latStr += strOne + "0000"
        else if (lengthOne == 3) latStr += strOne + "000"
        else if (lengthOne == 4) latStr += strOne + "00"
        else if (lengthOne == 5) latStr += strOne + "0"
        else if (lengthOne == 6) latStr += strOne
        else if (lengthOne > 6) latStr += strOne.take(6)

        if (lengthTwo == 0) longStr += "000000"
        else if (lengthTwo == 1) longStr += strTwo + "00000"
        else if (lengthTwo == 2) longStr += strTwo + "0000"
        else if (lengthTwo == 3) longStr += strTwo + "000"
        else if (lengthTwo == 4) longStr += strTwo + "00"
        else if (lengthTwo == 5) longStr += strTwo + "0"
        else if (lengthTwo == 6) longStr += strTwo
        else if (lengthTwo > 6) longStr += strTwo.take(6)

        result(latStr, longStr)
    }
}