package com.mpumi.weatherforecastapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormat {

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateAsString(stringDate: String?, pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a")
        val date = LocalDateTime.parse(stringDate, formatter)
        if (date != null)
            return simpleDateFormat.format(date)
        else
            throw NullPointerException("Date can't be null")
    }
}