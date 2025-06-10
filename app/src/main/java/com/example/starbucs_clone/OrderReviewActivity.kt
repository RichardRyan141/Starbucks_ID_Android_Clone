package com.example.starbucs_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starbucs_clone.data.Menu
import com.example.starbucs_clone.data.PurchaseDetail
import com.example.starbucs_clone.data.locationsList
import com.example.starbucs_clone.ui.theme.Starbucs_CloneTheme
import com.example.starbucs_clone.values.color_starbucksGreen
import java.text.NumberFormat
import java.util.Locale

class OrderReviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Starbucs_CloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OrderReviewScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderReviewPreview() {
    Starbucs_CloneTheme {
        OrderReviewScreen(modifier= Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderReviewScreen(modifier: Modifier) {
    val purchaseList = PurchaseSessionManager.getItems()
    val totalPrice = getTotalPrice(purchaseList)
    val user = UserSessionManager.getLoggedInUser()!!
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF1EBEB),
        topBar = {
                Column(modifier = Modifier.background(Color.Black)) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Order Review (${PurchaseSessionManager.getItemCount()})",
                                color = Color.White,
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
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black
                        )
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Pickup Store :",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .drawBehind {
                                    val strokeWidth = 2.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color.White,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                                .clickable {
                                }
                        ) {
                            Text(
                                text = locationsList[PurchaseSessionManager.selectedLocationId].nama,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ExpandMore,
                                contentDescription = "Expand",
                                tint = Color.White
                            )
                        }
                    }
                }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom=50.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(vertical=12.dp, horizontal=16.dp).fillMaxWidth()) {
                    purchaseList.forEach { purchase ->
                        val menu = MenuSessionManager.getMenuDetail(purchase.menu_id)
                        PurchaseCard(purchase, menu)
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = Color.LightGray
                    )
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = color_starbucksGreen
                        ),
                        border = BorderStroke(2.dp, color_starbucksGreen),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        ),
                        shape = RoundedCornerShape(35.dp)
                    ) {
                        Text(
                            text = "Add more items",
                            color = color_starbucksGreen,
                            fontSize = 24.sp
                        )
                    }
                    Column(modifier = Modifier.padding(vertical=24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Subtotal : ",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = buildString {
                                    repeat(90) { append('.') }
                                },
                                modifier = Modifier.weight(1f),
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Clip
                            )

                            Text(
                                text = "Rp. ${formatCurrency(totalPrice * 10 / 11)}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tax (10%) : ",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = buildString {
                                    repeat(90) { append('.') }
                                },
                                modifier = Modifier.weight(1f),
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Clip
                            )

                            Text(
                                text = "Rp. ${formatCurrency(totalPrice / 11)}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical=12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total : ",
                                fontWeight = FontWeight.Bold,
                                color = color_starbucksGreen,
                                fontSize = 24.sp
                            )
                            Text(
                                text = buildString {
                                    repeat(90) { append('.') }
                                },
                                modifier = Modifier.weight(1f),
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Clip
                            )

                            Text(
                                text = "Rp. ${formatCurrency(totalPrice)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = color_starbucksGreen
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                        thickness = 2.dp,
                        color = Color.LightGray
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top=12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Card balance",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                        Text(
                            text = "Rp. ${formatCurrency(user.balance)}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top=12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { PurchaseSessionManager.checkOut(totalPrice, "Card") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = color_starbucksGreen
                            ),
                            border = BorderStroke(2.dp, color_starbucksGreen),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            ),
                            shape = RoundedCornerShape(35.dp),
                            enabled = user.balance >= totalPrice,
                        ) {
                            Text(
                                text = "Pay with card" + if(user.balance < totalPrice) "\n(insufficient balance)" else "",
                                color = if (user.balance >= totalPrice) color_starbucksGreen else Color.Gray,
                                fontSize = 16.sp,
                                maxLines = 2,
                                softWrap = true,
                                textAlign = TextAlign.Center
                            )

                        }
                        Button(
                            onClick = { PurchaseSessionManager.checkOut(totalPrice, "Cash") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = color_starbucksGreen
                            ),
                            border = BorderStroke(2.dp, color_starbucksGreen),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            ),
                            shape = RoundedCornerShape(35.dp),
                            enabled = true
                        ) {
                            Text(
                                text = "Pay with cash",
                                color = color_starbucksGreen,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PurchaseCard(purchase: PurchaseDetail, menu: Menu) {
    val totalPrice = calculateSubTotalPrice(purchase, menu.nominal)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(menu.imageID),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("${purchase.qty} x ${menu.nama}", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Total",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Rp. ${formatCurrency(totalPrice)}",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Base Price",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Rp. ${formatCurrency(menu.nominal * purchase.qty)}",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = if(purchase.isHot) "${purchase.qty} x Hot" else "${purchase.qty} x Cold",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Rp. 0",
                    )
                }
                if(menu.kategori == "Beverage") {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        val sizePrice = if(purchase.size == "Tall") 0 else if(purchase.size == "Grande") 4000 else 6000
                        Text(
                            text = "${purchase.qty} x ${purchase.size}",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Rp. ${formatCurrency(purchase.qty * sizePrice)}",
                        )
                    }
                    if(purchase.extra_espresso > 0) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            val espressoPrice = 6000 * purchase.extra_espresso
                            Text(
                                text = "${purchase.qty * purchase.extra_espresso} x Add. Espresso",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${
                                    formatCurrency(purchase.qty * espressoPrice)
                                }",
                            )
                        }
                    }
                    if(purchase.extra_syrup1_id != -1) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "${purchase.qty} x Add. ${PurchaseSessionManager.getSyrupDetail(purchase.extra_syrup1_id)}",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${formatCurrency(purchase.qty * 6000)}",
                            )
                        }
                    }
                    if(purchase.extra_syrup2_id != -1) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "${purchase.qty} x Add. ${PurchaseSessionManager.getSyrupDetail(purchase.extra_syrup2_id)}",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${formatCurrency(purchase.qty * 6000)}",
                            )
                        }
                    }
                    if(purchase.extra_whipCream) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "${purchase.qty} x Add. liquid whip cream",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${formatCurrency(purchase.qty * 10000)}",
                            )
                        }
                    }
                    if(purchase.extra_iceCream) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "${purchase.qty} x Add. vanilla ice cream",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${formatCurrency(purchase.qty * 19000)}",
                            )
                        }
                    }
                    if(purchase.extra_coldFoam) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "${purchase.qty} x Add. Plain cold foam",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${formatCurrency(purchase.qty * 6000)}",
                            )
                        }
                    }
                    if(purchase.extra_saltedFoam) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "${purchase.qty} x Add. Salted foam",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rp. ${formatCurrency(purchase.qty * 10000)}",
                            )
                        }
                    }
                }

                if (purchase.notes != "") {
                    Text(
                        text = "Notes",
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = purchase.notes
                    )
                }
            }
        }
    }
}

fun getTotalPrice(purchaseList: List<PurchaseDetail>): Int {
    var total = 0
    purchaseList.forEach { purchase ->
        val menu = MenuSessionManager.getMenuDetail(purchase.menu_id)
        total = total + calculateSubTotalPrice(purchase, menu.nominal)
    }
    return total
}

fun calculateSubTotalPrice(purchase: PurchaseDetail, basePrice: Int): Int {
    val sizePrice = if(purchase.size == "Tall") 0 else if(purchase.size == "Grande") 4000 else 6000
    val espressoPrice = 6000 * purchase.extra_espresso
    val syrupPrice = listOf(purchase.extra_syrup1_id, purchase.extra_syrup2_id).count { it != -1 } * 6000
    val whipPrice = listOf(purchase.extra_whipCream).count { it } * 10000
    val icePrice = listOf(purchase.extra_iceCream).count { it } * 19000
    val coldFoamPrice = listOf(purchase.extra_coldFoam).count { it } * 6000
    val saltedFoamPrice = listOf(purchase.extra_saltedFoam).count { it } * 10000
    return purchase.qty * (basePrice + sizePrice + espressoPrice + syrupPrice + whipPrice + icePrice + coldFoamPrice + saltedFoamPrice)
}
