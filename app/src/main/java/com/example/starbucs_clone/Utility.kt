package com.example.starbucs_clone

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.starbucs_clone.data.navItemList
import com.example.starbucs_clone.values.color_starbucksGreen
import java.text.NumberFormat
import java.util.Locale

@Composable
fun Navbar(selectedItem: Int, onItemChange: (Int) -> Unit) {
    BottomNavigation(
        modifier = Modifier.height(90.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        navItemList.forEachIndexed { index, item ->
            val isSelected = selectedItem == index
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) color_starbucksGreen else Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (isSelected) color_starbucksGreen else Color.Gray
                    )
                },
                selected = isSelected,
                onClick = { onItemChange(index) },
                selectedContentColor = color_starbucksGreen,
                unselectedContentColor = Color.Gray,
                modifier = Modifier.padding(top=12.dp)
            )
        }
    }
}

fun formatCurrency(nominal: Int): String {
    return NumberFormat.getNumberInstance(Locale.US).format(nominal)
}