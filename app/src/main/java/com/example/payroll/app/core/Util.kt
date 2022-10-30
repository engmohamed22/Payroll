package com.example.payroll.app.core

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String {
    val parser = SimpleDateFormat("mm/yyyy", Locale.ENGLISH)
    val formatter = SimpleDateFormat("MMMM ,yyyy", Locale.forLanguageTag("Ar"))
    return formatter.format(parser.parse(date))
}