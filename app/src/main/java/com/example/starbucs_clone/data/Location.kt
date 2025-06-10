package com.example.starbucs_clone.data

data class Location(
    val nama: String = "",
    val operational: String = "MON - SUN : 08:00 - 22:00"
)

val locationsList = mutableListOf(
    Location("STARBUCKS GRAND WISATA BEKASI", "MON - SUN : 06:30 - 22:00"),
    Location("STARBUCKS SUMMARECON MALL BEKASI"),
    Location("STARBUCKS MALL KELAPA GADING 2"),
    Location("STARBUCKS MALL KELAPA GADING 3", "MON - SUN : 07:00 - 22:00"),
    Location("STARBUCKS RAWAMANGUN PAUS", "MON - SUN : 07:00 - 23:00"),
    Location("STARBUCKS MALL ARTHA GADING"),
    Location("STARBUCKS BOTANI SQUARE", "MON - SUN : 07:00 - 22:00"),
    Location("STARBUCKS GREEN PRAMUKA"),
    Location("STARBUCKS MARGO CITY", "MON - SUN : 09:00 - 22:00"),
    Location("STARBUCKS SUNTER GREEN LAKE", "MON - SUN : 07:00 - 22:00")
)