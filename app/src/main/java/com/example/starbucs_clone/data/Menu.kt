package com.example.starbucs_clone.data

import com.example.starbucs_clone.R

data class Menu(
    val nama: String = "",
    val deskripsi: String = "",
    val imageID: Int = 0,
    val nominal: Int = 0,
    val kategori: String = "",
)

val menusList = mutableListOf(
    Menu("Vanilla Sweet Creme Cold Brew", "", R.drawable.promo1, 49000, "Beverage"),
    Menu("Cold Brew Coffee", "Available iced only", R.drawable.promo1, 45000, "Beverage"),
    Menu("Smoked Beef Mushroom & Cheese Panini", "All time favorite Smoked Beef & Cheese pressed sandwich", R.drawable.promo2, 45000, "Food"),
    Menu("Pandan Unti Soft Bun", "", R.drawable.promo2, 25000, "Food"),
    Menu("New York Cheese Cake", "", R.drawable.promo2, 41000, "Food")
)