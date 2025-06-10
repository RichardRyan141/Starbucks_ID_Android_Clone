package com.example.starbucs_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starbucs_clone.data.PurchaseDetail
import com.example.starbucs_clone.data.syrupList
import com.example.starbucs_clone.ui.theme.Starbucs_CloneTheme
import com.example.starbucs_clone.values.color_starbucksGreen

class OrderMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Starbucs_CloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OrderMenuScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderMenuPreview() {
    Starbucs_CloneTheme {
        OrderMenuScreen(modifier= Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMenuScreen(modifier: Modifier, menuId: Int = 0) {
    var qty by remember { mutableIntStateOf(1) }
    var selectedSize by remember {mutableStateOf("Grande")}
    var menuDetail = MenuSessionManager.getMenuDetail(menuId)

    var expandSize by remember { mutableStateOf(false) }
    var expandEspresso by remember { mutableStateOf(false) }
    var expandSyrups by remember { mutableStateOf(false) }
    var expandLiquid by remember { mutableStateOf(false) }
    var expandIceCream by remember { mutableStateOf(false) }
    var expandColdCream by remember { mutableStateOf(false) }

    val sizes = listOf("Tall", "Grande", "Venti")
    val sizePrices = listOf("Free", "+ Rp. 4,000", "+ Rp. 6,000")

    var espresso by remember { mutableIntStateOf(0) }
    var whipCream by remember { mutableStateOf(false) }
    var iceCream by remember { mutableStateOf(false) }
    var plainFoam by remember { mutableStateOf(false) }
    var saltedFoam by remember { mutableStateOf(false) }
    var notesText by remember { mutableStateOf("") }

    val syrupQuantities = remember { mutableStateListOf(*IntArray(syrupList.size) { 0 }.toTypedArray()) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF1EBEB),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = menuDetail.nama,
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                fun getSelectedSyrupIds(): List<Int> {
                    val selected = mutableListOf<Int>()

                    for ((index, qty) in syrupQuantities.withIndex()) {
                        repeat(qty) {
                            selected.add(index)
                            if (selected.size == 2) return selected
                        }
                    }

                    while (selected.size < 2) {
                        selected.add(-1)
                    }

                    return selected
                }

                val (syrup1, syrup2) = getSelectedSyrupIds()

                val purchase = PurchaseDetail(
                    menu_id = menuId,
                    size = selectedSize,
                    extra_espresso = espresso,
                    extra_whipCream = whipCream,
                    extra_iceCream = iceCream,
                    extra_coldFoam = plainFoam,
                    extra_saltedFoam = saltedFoam,
                    extra_syrup1_id = syrup1,
                    extra_syrup2_id = syrup2
                )

                PurchaseSessionManager.addItem(purchase)
            }, containerColor = color_starbucksGreen) {
                Text(text = "Add", color = Color.White, fontSize = 24.sp, modifier = Modifier.padding(vertical=8.dp, horizontal=16.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom=80.dp)) {
                    Column(modifier = Modifier.padding(horizontal=16.dp, vertical=16.dp)) {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .padding(bottom = 20.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Image(
                                painter = painterResource(id = menuDetail.imageID),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = menuDetail.nama,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.padding(bottom = 32.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Rp. ${formatCurrency(menuDetail.nominal)}",
                                color = color_starbucksGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                modifier = Modifier.weight(0.7f)
                            )
                            Row(modifier = Modifier.weight(0.3f)) {
                                Icon(
                                    imageVector = Icons.Default.RemoveCircleOutline,
                                    contentDescription = "Decrease Quantity",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable {
                                            if (qty > 1) qty--
                                        },
                                    tint = if (qty > 1) Color.Black else Color.Gray
                                )
                                Text(
                                    text = qty.toString(),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.AddCircleOutline,
                                    contentDescription = "Increase Quantity",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable {
                                            qty++
                                        },
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = Color.LightGray
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Size
                        Column(modifier=Modifier.padding(vertical=8.dp)) {
                            if (expandSize) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "Size",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandSize = !expandSize
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.size_selected),
                                            contentDescription = null,
                                            modifier = Modifier.size(32.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(
                                            text = selectedSize,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 24.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Row {
                                        Text(
                                            text = "Choose 1 item",
                                            color = color_starbucksGreen,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Icon(
                                            imageVector = if (expandSize) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle Size"
                                        )
                                    }
                                }
                                if (expandSize) {
                                    sizes.zip(sizePrices).forEach { (size, price) ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .selectable(
                                                    selected = (size == selectedSize),
                                                    onClick = {
                                                        selectedSize = size
                                                    }
                                                )
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                                Row(modifier = Modifier.weight(0.6f)) {
                                                    Image(
                                                        painter = painterResource(
                                                            id = if (size == selectedSize) R.drawable.size_selected else R.drawable.size
                                                        ),
                                                        contentDescription = if (size == selectedSize) "Selected" else "Not selected",
                                                        modifier = Modifier.size(32.dp),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                    Text(
                                                        text = size,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 24.sp,
                                                        color = Color.Gray
                                                    )
                                                }
                                                Row(modifier = Modifier.weight(0.4f)) {
                                                    Text(
                                                        text = price,
                                                        fontSize = 24.sp,
                                                        color = color_starbucksGreen,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (expandSize) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }

                        // Espresso
                        Column(modifier=Modifier.padding(vertical=8.dp)) {
                            if (expandEspresso) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandEspresso = !expandEspresso
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Add - Espresso Shot",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Row {
                                        Text(
                                            text = "Opt. (max 3)",
                                            color = color_starbucksGreen,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Icon(
                                            imageVector = if (expandEspresso) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle Espresso"
                                        )
                                    }
                                }
                                if (expandEspresso) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(0.6f)) {
                                            Text(
                                                text = "Additional Shot Espresso",
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = "+ Rp. 6,000",
                                                fontSize = 20.sp
                                            )
                                        }
                                        Row(modifier = Modifier.weight(0.2f)) {
                                            Icon(
                                                imageVector = Icons.Default.RemoveCircleOutline,
                                                contentDescription = "Decrease Espresso",
                                                modifier = Modifier
                                                    .size(26.dp)
                                                    .clickable {
                                                        if (espresso > 1) espresso--
                                                    },
                                                tint = if (espresso > 1) Color.Black else Color.Gray
                                            )
                                            Text(
                                                text = espresso.toString(),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 8.dp)
                                            )
                                            Icon(
                                                imageVector = Icons.Default.AddCircleOutline,
                                                contentDescription = "Increase Espresso",
                                                modifier = Modifier
                                                    .size(26.dp)
                                                    .clickable {
                                                        if (espresso < 3) espresso++
                                                    },
                                                tint = if (espresso < 3) Color.Black else Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                            if (expandEspresso) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }

                        // Syrup & Sauce
                        Column(modifier=Modifier.padding(vertical=8.dp)) {
                            if (expandSyrups) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandSyrups = !expandSyrups
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Add - Syrup & Sauce",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Row {
                                        Text(
                                            text = "Opt. (max 2)",
                                            color = color_starbucksGreen,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Icon(
                                            imageVector = if (expandSyrups) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle Syrup"
                                        )
                                    }
                                }
                                if (expandSyrups) {
                                    syrupList.forEachIndexed { index, syrup ->
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                                        ) {
                                            Column(modifier = Modifier.weight(0.6f)) {
                                                Text(text = "Additional $syrup", fontSize = 20.sp)
                                                Text(text = "+ Rp. 6,000", fontSize = 20.sp)
                                            }

                                            Row(modifier = Modifier.weight(0.2f)) {
                                                Icon(
                                                    imageVector = Icons.Default.RemoveCircleOutline,
                                                    contentDescription = "Decrease syrup",
                                                    modifier = Modifier
                                                        .size(26.dp)
                                                        .clickable {
                                                            if (syrupQuantities[index] > 0) {
                                                                syrupQuantities[index] = syrupQuantities[index] - 1
                                                            }
                                                        },
                                                    tint = if (syrupQuantities[index] > 0) Color.Black else Color.Gray
                                                )

                                                Text(
                                                    text = syrupQuantities[index].toString(),
                                                    fontSize = 22.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    modifier = Modifier.padding(horizontal = 8.dp)
                                                )

                                                Icon(
                                                    imageVector = Icons.Default.AddCircleOutline,
                                                    contentDescription = "Increase syrup",
                                                    modifier = Modifier
                                                        .size(26.dp)
                                                        .clickable {
                                                            if (syrupQuantities[index] < 2 && syrupQuantities.sum() < 2) {
                                                                syrupQuantities[index] = syrupQuantities[index] + 1
                                                            }
                                                        },
                                                    tint = if (syrupQuantities[index] < 2 && syrupQuantities.sum() < 2) Color.Black else Color.Gray
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            if (expandSyrups) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }

                        // Liquid
                        Column(modifier=Modifier.padding(vertical=8.dp)) {
                            if (expandLiquid) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandLiquid = !expandLiquid
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Add - Liquid",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Row {
                                        Text(
                                            text = "Opt. (max 1)",
                                            color = color_starbucksGreen,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Icon(
                                            imageVector = if (expandLiquid) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle Espresso"
                                        )
                                    }
                                }
                                if (expandLiquid) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(0.6f)) {
                                            Text(
                                                text = "Additional Liquid Whip Cream",
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = "+ Rp. 10,000",
                                                fontSize = 20.sp
                                            )
                                        }
                                        Row(modifier = Modifier.weight(0.1f)) {
                                            Checkbox(
                                                checked = whipCream,
                                                onCheckedChange = { whipCream = it },
                                                modifier = Modifier.scale(1.5f)
                                            )
                                        }
                                    }
                                }
                            }
                            if (expandLiquid) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }

                        // Ice Cream
                        Column(modifier=Modifier.padding(vertical=8.dp)) {
                            if (expandIceCream) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandIceCream = !expandIceCream
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Add - Ice Cream",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Row {
                                        Text(
                                            text = "Opt. (max 1)",
                                            color = color_starbucksGreen,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Icon(
                                            imageVector = if (expandIceCream) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle Espresso"
                                        )
                                    }
                                }
                                if (expandIceCream) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(0.6f)) {
                                            Text(
                                                text = "Additional Vanilla Ice Cream",
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = "+ Rp. 19,000",
                                                fontSize = 20.sp
                                            )
                                        }
                                        Row(modifier = Modifier.weight(0.1f)) {
                                            Checkbox(
                                                checked = iceCream,
                                                onCheckedChange = { iceCream = it },
                                                modifier = Modifier.scale(1.5f)
                                            )
                                        }
                                    }
                                }
                            }
                            if (expandIceCream) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }

                        // Cold Foam
                        Column(modifier=Modifier.padding(vertical=8.dp)) {
                            if (expandColdCream) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandColdCream = !expandColdCream
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Add - Cold Foam",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Row {
                                        Text(
                                            text = "Opt. (max 1)",
                                            color = color_starbucksGreen,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Icon(
                                            imageVector = if (expandLiquid) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Toggle Espresso"
                                        )
                                    }
                                }
                                if (expandColdCream) {
                                    Column {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(vertical=8.dp)
                                        ) {
                                            Column(modifier = Modifier.weight(0.6f)) {
                                                Text(
                                                    text = "Additional Plain Cold Foam",
                                                    fontSize = 20.sp
                                                )
                                                Text(
                                                    text = "+ Rp. 6,000",
                                                    fontSize = 20.sp
                                                )
                                            }
                                            Row(modifier = Modifier.weight(0.1f)) {
                                                Checkbox(
                                                    checked = plainFoam,
                                                    onCheckedChange = { if((plainFoam) || (!plainFoam && !saltedFoam)) plainFoam = it },
                                                    modifier = Modifier.scale(1.5f)
                                                )
                                            }
                                        }
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(vertical=8.dp)
                                        ) {
                                            Column(modifier = Modifier.weight(0.6f)) {
                                                Text(
                                                    text = "Additional Salted Foam",
                                                    fontSize = 20.sp
                                                )
                                                Text(
                                                    text = "+ Rp. 10,000",
                                                    fontSize = 20.sp
                                                )
                                            }
                                            Row(modifier = Modifier.weight(0.1f)) {
                                                Checkbox(
                                                    checked = saltedFoam,
                                                    onCheckedChange = { if((saltedFoam) || (!plainFoam && !saltedFoam)) saltedFoam = it },
                                                    modifier = Modifier.scale(1.5f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            if (expandColdCream) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Notes",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                        ) {
                            OutlinedTextField(
                                value = notesText,
                                onValueChange = { notesText = it },
                                modifier = Modifier.fillMaxSize(),
                                placeholder = { Text("Example: No Whipped Cream") },
                                singleLine = false,
                                maxLines = 5,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                qty = 1
                                selectedSize = "Grande"
                                espresso = 0
                                whipCream = false
                                iceCream = false
                                plainFoam = false
                                saltedFoam = false
                                notesText = ""
                                syrupQuantities.replaceAll { 0 }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 2.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Text(
                            text = "Reset",
                            fontSize = 32.sp,
                            color = color_starbucksGreen,
                            fontWeight = FontWeight.Bold,
                        )
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 2.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

