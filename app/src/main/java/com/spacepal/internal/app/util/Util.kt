package com.spacepal.internal.app.util

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Base64
import android.util.Patterns
import android.view.View
import com.spacepal.internal.app.Constant
import com.spacepal.internal.app.R
import com.spacepal.internal.app.SpacePalApplication
import com.spacepal.internal.app.model.response.APIError
import com.spacepal.internal.app.model.response.TokenResponse
import retrofit2.Response
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object Util {

    val DATE_FORMAT_1 = "dd MMM yyyy hh:mm a"
    val DATE_FORMAT_2 = "MMM dd"
    val DATE_FORMAT_3 = "hh:mm a"
    private val TAG = "Util"


    val currentYear: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.YEAR)
        }

    @Throws(UnsupportedEncodingException::class)
    fun encodeBase64(s: String): String {
        val data = s.toByteArray(charset("UTF-8"))
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    @Throws(UnsupportedEncodingException::class)
    fun getAuthorizationHeader(tokenObj: TokenResponse?): String? {
        if (tokenObj == null)
            return null
        return if (tokenObj == null) null else tokenObj!!.accessToken
    }


    fun formatDate(date: String): String {
        val format = SimpleDateFormat(DATE_FORMAT_1)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(date)
        return format.format(calendar.time)

    }

    fun formatDateForChart(date: String): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_2)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(date) * 1000
        return simpleDateFormat.format(calendar.time)
    }

    fun formatDateForMessagesHistory(date: String): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_3)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(date) * 1000
        return simpleDateFormat.format(calendar.time)
    }


    fun formatDate(format: String, date: String): String {
        val simpleDateFormat = SimpleDateFormat(format)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.lang.Long.parseLong(date) * 1000
        return simpleDateFormat.format(calendar.time)
    }

    fun getDateFromMilliseconds(date: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        return calendar.time
    }

    fun getDifferenceInDays(startDate: Date, endDate: Date): Int {
        val different = endDate.time - startDate.time

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli

        return elapsedDays.toInt()
    }


    fun getDifference(startDate: Date, endDate: Date): String {
        //milliseconds
        var different = endDate.time - startDate.time

        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different = different % daysInMilli

        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli

        val elapsedSeconds = different / secondsInMilli

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)

        if (elapsedDays > 0) {
            return elapsedDays.toString() + " day(s)"
        } else if (elapsedHours > 0) {
            return elapsedHours.toString() + " hours"
        } else if (elapsedMinutes > 0)
            return elapsedMinutes.toString() + " minutes"
        return ""
    }

    fun isTheSameDays(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterdayDate(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val yesterdayDate = calendar.time

        return isTheSameDays(yesterdayDate, date)
    }

    fun isWeekAgo(date: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal2.time = date
        val isSameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)

        if (isSameMonth) {
            val days = getDifferenceInDays(date, cal1.time)
            return days <= 7
        } else {
            return false
        }
    }


    fun toCurrency(price: Double, currency: String): String {
        val numberFormat = DecimalFormat()
        numberFormat.minimumFractionDigits = 2
        numberFormat.maximumFractionDigits = 2
        val formattedValue = numberFormat.format(price)
        return currency + formattedValue
    }

    fun toCurrency(price: Double, currency: String, minFraction: Int, maxFraction: Int): String {
        val numberFormat = DecimalFormat()
        numberFormat.minimumFractionDigits = minFraction
        numberFormat.maximumFractionDigits = maxFraction
        val formattedValue = numberFormat.format(price)
        return currency + formattedValue
    }


    fun parseError(response: Response<*>): APIError {
        val error: APIError


        try {
            error = SpacePalApplication.instance.gson.fromJson(response.errorBody()?.string() ?: "", APIError::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
            return APIError()
        }

        return error
    }

    fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun containsUnicode(text: String): Boolean {
        val symbols = text.toCharArray()
        for (i in symbols.indices) {
            if (Character.UnicodeBlock.of(symbols[i]) !== Character.UnicodeBlock.BASIC_LATIN) {
                return true
            }
        }
        return false
    }

    fun isValidPassword(password: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher

        //final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()

    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }

    fun capitalizeFirstLetter(original: String?): String? {
        return if (original == null || original.length == 0) {
            original
        } else original.substring(0, 1).toUpperCase() + original.substring(1)
    }


    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
    fun getPriority(priority: Int?): String {
        return when (priority) {
            Constant.PRIORITY_H -> "H"
            Constant.PRIORITY_M -> "M"
            else -> "L"
        }
    }

    fun setPriorityDrawable(priority: Int?,view: View) {
        when (priority) {
            Constant.PRIORITY_H -> view.background = ContextCompat.getDrawable(view.context,R.drawable.status_ring_high)
            Constant.PRIORITY_M -> view.background=(ContextCompat.getDrawable(view.context,R.drawable.status_ring_medium))
            else -> view.background = ContextCompat.getDrawable(view.context,R.drawable.status_ring_low)
        }
    }

    fun getReference(appointmentId: String?): String {
       var reference = appointmentId?.split("-");
        return reference!![0]
    }

}

