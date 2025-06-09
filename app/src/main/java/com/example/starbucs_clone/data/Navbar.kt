package com.example.starbucs_clone.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(val label: String, val icon: ImageVector)

var navItemList = listOf(
    NavItem("Home", Icons.Default.Home),
    NavItem("Card", Icons.Default.CreditCard),
    NavItem("Order", Icons.Default.ShoppingCart),
    NavItem("Reward", Icons.Default.Star)
)