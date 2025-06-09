package com.example.starbucs_clone.data

data class User(
    val email: String = "",
    val password: String = "",
    val nama_depan: String = "",
    val nama_belakang: String = "",
    val noTelp: String = "",
    val DoB: String = "",
    val stars: Int = 0,
    val stars1Year: Int = 0,
    val balance: Int = 0
)

val usersList = listOf(
    User(
        email = "user1@gmail.com",
        password = "Pass1234.",
        nama_depan = "User 1",
        nama_belakang = "XXX",
        noTelp = "08123456789",
        DoB = "20/01/2000"
    ),
    User(
        email = "user2@gmail.com",
        password = "Pass1234.",
        nama_depan = "User 2",
        nama_belakang = "YYY",
        noTelp = "08123456789",
        DoB = "25/12/1995",
        stars = 300,
        stars1Year = 350
    )
)