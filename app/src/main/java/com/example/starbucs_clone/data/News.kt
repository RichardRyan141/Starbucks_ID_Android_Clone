package com.example.starbucs_clone.data

import com.example.starbucs_clone.R

data class News(
    val image: Int,
    val title: String,
    val captionTitle: String,
    val text: String
)

val newsList = listOf(
    News(
        image = R.drawable.news_logo,
        title = "Tanggapan Starbucks mengenai konflik di Gaza",
        captionTitle = "TRUTH MATTERS",
        text = "Meskipun akar kami berada di Amerika Serikat, kami adalah perusahaan global dengan gerai yang tersebar di 86 pasar, termasuk lebih dari"
    ),
    News(
        image = R.drawable.news_logo,
        title = "Starbucks Indonesia - donasi bersama untuk bantuan kemanusiaan di Gaza",
        captionTitle = "Donasi untuk Gaza",
        text = "Starbucks Foundation dan mitra pemegang lisensi Starbucks, PT Sari COffee Indonesia, mengumumka donasi bersama sebesar Rp5 miliar (sekitar US$314.000) kepada"
    ),
    News(
        image = R.drawable.news_logo,
        title = "Get the best of Starbucks Rewards right now",
        captionTitle = "Starbucks Features",
        text = "Did you know the Starbucks App features?."
    )
)
