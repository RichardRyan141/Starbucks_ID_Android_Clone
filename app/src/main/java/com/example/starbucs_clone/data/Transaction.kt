package com.example.starbucs_clone.data

data class Transaction(
    val user_id: Int = 0,
    val sumber: String = "",
    val tujuan: String = "",
    val nominal: Int = 0,
    val waktu: String = ""
)

val transactionsList = mutableListOf(
    Transaction(0,"Cash","Lokasi 1", 100000, "31/01/2001 15:08"),
    Transaction(1,"TopUp", "Card", 1000000, "01/12/2002 10:09"),
    Transaction(1, "Card", "Lokasi 2", 45000, "07/12/2002 13:10")
)