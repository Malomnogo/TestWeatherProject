package com.malomnogo.presentation.core

interface FormatDate {

    fun formatDate(dateEpoch: Long): String

    class Base : FormatDate {
        override fun formatDate(dateEpoch: Long): String {
            val date = java.util.Date(dateEpoch * 1000)
            val format = java.text.SimpleDateFormat("d.M.yyyy", java.util.Locale.getDefault())
            return format.format(date)
        }
    }
}
