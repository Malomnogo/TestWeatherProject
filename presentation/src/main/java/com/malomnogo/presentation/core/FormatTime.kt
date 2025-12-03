package com.malomnogo.presentation.core

interface FormatTime {

    fun formatTime(timeEpoch: Long): String

    class Base : FormatTime {

        override fun formatTime(timeEpoch: Long): String {
            val date = java.util.Date(timeEpoch * 1000)
            val format = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            return format.format(date)
        }
    }
}