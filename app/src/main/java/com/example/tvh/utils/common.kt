package com.example.tvh.utils

import org.ocpsoft.prettytime.PrettyTime
import java.util.*

object Utils {
    object DateTime {
        private val prettyTime = PrettyTime()
        fun getPrettyTime(ts: String): String {
            return prettyTime.format(Date(ts.toLong())) ?: ""
        }
    }
}

