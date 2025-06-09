package com.example.starbucs_clone.data

data class Location(
    val nama: String = "",
    val operational: String = "MON - SUN : 08:00 - 22:00"
)

val locationsList = mutableListOf(
    Location("STARBUCKS GRAND WISATA BEKASI", "MON - SUN : 06:30 - 22:00"),
    Location("STARBUCKS SUMMARECON MALL BEKASI"),
    Location("STARBUCKS MALL KELAPA GADING 2")
)