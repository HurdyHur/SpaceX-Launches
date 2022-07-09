package com.harry.spacexlaunches.util

import java.sql.Date
import java.text.SimpleDateFormat



fun fromUnixDate(unixDate: Long): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(unixDate * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        ""
    }
}
