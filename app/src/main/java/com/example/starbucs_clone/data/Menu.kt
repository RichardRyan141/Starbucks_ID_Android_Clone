package com.example.starbucs_clone.data

import com.example.starbucs_clone.R

data class Menu(
    val nama: String = "",
    val deskripsi: String = "",
    val imageID: Int = 0,
    val nominal: Int = 0,
    val kategori: String = "",
    val subKategori: String = ""
)

val menusList = mutableListOf(
    Menu("Vanilla Sweet Creme Cold Brew", "", R.drawable.product_placeholder, 49000, "Beverage", "Brewed Coffees"),
    Menu("Cold Brew Coffee", "Available iced only", R.drawable.product_placeholder, 45000, "Beverage", "Brewed Coffees"),
    Menu("Smoked Beef Mushroom & Cheese Panini", "All time favorite Smoked Beef & Cheese pressed sandwich", R.drawable.product_placeholder, 45000, "Food", "Sandwich"),
    Menu("Pandan Unti Soft Bun", "", R.drawable.product_placeholder, 25000, "Food", "Baked"),
    Menu("New York Cheese Cake", "", R.drawable.product_placeholder, 41000, "Food", "Cakes"),
    Menu("Caramel Macchiato", "", R.drawable.product_placeholder, 61000, "Beverage", "Espresso Beverages"),
    Menu("Caffe Latte", "", R.drawable.product_placeholder, 48000, "Beverage", "Espresso Beverages"),
    Menu("Americano", "", R.drawable.product_placeholder, 38000, "Beverage", "Espresso Beverages"),
    Menu("Coffee Double Shots Iced Shaken", "", R.drawable.product_placeholder, 52000, "Beverage", "Espresso Beverages"),
    Menu("Capucino", "", R.drawable.product_placeholder, 48000, "Beverage", "Espresso Beverages")
)