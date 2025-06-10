package com.example.starbucs_clone

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Density
import com.example.starbucs_clone.data.News
import com.example.starbucs_clone.data.User
import com.example.starbucs_clone.data.newsList
import com.example.starbucs_clone.ui.theme.Starbucs_CloneTheme
import com.example.starbucs_clone.values.color_gold
import com.example.starbucs_clone.values.color_starbucksGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Starbucs_CloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

val promoImageIds = listOf(
    R.drawable.promo1,
    R.drawable.promo2,
    R.drawable.promo3,
)

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Starbucs_CloneTheme {
        HomeScreen(modifier= Modifier)
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val user = UserSessionManager.getLoggedInUser()
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize().padding(top=32.dp),
        containerColor = Color(0xFFF1EBEB),
        topBar = {
            TopHomeBar(user)
        },
        bottomBar = {
            Navbar(selectedItem, {selectedItem = it})
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 36.dp)
        ) {
            item {
                if (user == null) {
                    RewardCard()
                } else {
                    RewardSection(user)
                }
            }
            item { ProgramAndPromotion() }
            item { NewsSection() }
        }
    }
}

@Composable
fun TopHomeBar(user: User?) {
    val context = LocalContext.current
    if(user != null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
                .padding(start = 16.dp, end=16.dp, bottom=16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.Bottom) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    ) {
                        Text(
                            text = "Good Morning,",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text(
                            text = user.nama_depan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                    Text(
                        text = if (user.stars1Year > 300) "Gold Level" else "Green Level",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (user.stars1Year > 300) color_gold else color_starbucksGreen
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_logo),
                            contentDescription = "Profile icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(64.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "Profile",
                            fontSize = 20.sp
                        )
                    }
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            UserSessionManager.logout()
                            (context as? Activity)?.recreate()
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = "Sign in icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(48.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "Logout",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
                .padding(start = 16.dp)
        ) {
            Column {
                Text(
                    text = "Good Morning,",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "It's a great day for coffee ☕",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_logo),
                        contentDescription = "Sign in icon",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(64.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Sign In",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RewardCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00704A))
            .padding(vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(vertical=8.dp, horizontal=16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFFFFFF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "STARBUCKS",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 20.sp,
                    color = Color(0xFF00704A)
                )
                Text(
                    text = "Rewards",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 18.sp,
                    color = Color(0xFF00704A)
                )
                Spacer(modifier=Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    ) {
                        Text(
                            text = "Join Starbucks Rewards, unlock star reward benefits",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Learn more >",
                            fontWeight = FontWeight.Light,
                            color = Color(0xFF0BA86B)
                        )
                    }

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00704A)),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Join Now",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun StarsProgressBar(
    currentStars: Int,
    milestones: List<Int> = listOf(0, 30, 60, 120, 240, 400),
    modifier: Modifier = Modifier,
    barHeight: Dp = 8.dp,
    milestoneSize: Dp = 16.dp,
    progressColor: Color = color_starbucksGreen,
    backgroundColor: Color = Color.LightGray,
    labelColor: Color = Color.DarkGray,
    labelFontSize: TextUnit = 16.sp
) {
    val maxStars = milestones.maxOrNull() ?: 400

    // Remember the width of the progress bar container (in px)
    var progressBarWidthPx by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(barHeight + milestoneSize) // Extra height for milestones
                .onSizeChanged {
                    progressBarWidthPx = it.width
                }
        ) {
            // Background line (gray)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight)
                    .background(color = backgroundColor, shape = RoundedCornerShape(barHeight / 2))
                    .align(Alignment.CenterStart)
            )

            val progressFraction = (currentStars.coerceIn(0, maxStars).toFloat() / maxStars.toFloat())

            Box(
                modifier = Modifier
                    .fillMaxWidth(progressFraction)
                    .height(barHeight)
                    .background(color = progressColor, shape = RoundedCornerShape(barHeight / 2))
                    .align(Alignment.CenterStart)
            )

            // Milestone dots
            milestones.forEach { milestone ->
                val fraction = milestone.toFloat() / maxStars.toFloat()
                // Calculate offset based on measured width
                val offsetPx = fraction * progressBarWidthPx

                Box(
                    modifier = Modifier
                        .size(milestoneSize)
                        .align(Alignment.CenterStart)
                        .offset { IntOffset(offsetPx.toInt() - milestoneSize.toPx(density).toInt() / 2, 0) }
                        .background(
                            color = if (currentStars >= milestone) progressColor else backgroundColor,
                            shape = CircleShape
                        )
                        .border(1.dp, Color.DarkGray, CircleShape)
                )
            }
        }

        // Labels row below the milestones:
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(labelFontSize.toDp() + 8.dp)
        ) {
            milestones.forEach { milestone ->
                val fraction = milestone.toFloat() / maxStars.toFloat()
                val offsetPx = fraction * progressBarWidthPx

                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetPx.toInt() - 20.dp.toPx(density).toInt(), 0) }
                        .width(40.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = milestone.toString(),
                        fontSize = labelFontSize,
                        color = labelColor,
                        maxLines = 1,
                        softWrap = false,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

fun Dp.toPx(density: Density): Float = with(density) { this@toPx.toPx() }

@Composable
fun TextUnit.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toPx().toDp() }
}

@Composable
fun RewardSection(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical=24.dp, horizontal=16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = user.stars.toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 64.sp
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = if(user.stars1Year > 300) color_gold else color_starbucksGreen,
                    modifier = Modifier.size(48.dp)
                )
            }
            Text(
                text = "Rewards details >",
                fontSize = 20.sp,
            )
        }
        Text(
            text = "Star balance",
            fontSize = 16.sp
        )
        StarsProgressBar(
            currentStars = user.stars,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier=Modifier.padding(top=12.dp)){
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(2.dp, color_starbucksGreen),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            ) {
                Text(
                    text = "Details",
                    fontSize = 20.sp,
                    color = color_starbucksGreen,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            ) {
                Text(
                    text = "Redeem ⭐",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }
        }
    }
}

@Composable
fun ProgramAndPromotion() {
    val pagerState = rememberPagerState(pageCount = { promoImageIds.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical=24.dp, horizontal=16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Programs and Promotion",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF024F34)
                )
                Text(
                    text = "See All",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color(0xFF06AF71)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    pageSpacing = 0.dp,
                ) { page ->
                    Image(
                        painter = painterResource(id = promoImageIds[page]),
                        contentDescription = "Promotion Image $page",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxHeight(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(promoImageIds.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index) Color.Black
                                    else Color.LightGray
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(news: News) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = news.image),
                contentDescription = news.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = news.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = news.captionTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = news.text,
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun NewsSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Starbuck News",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF024F34)
            )
            newsList.forEach { newsItem ->
                NewsCard(news = newsItem)
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.padding(horizontal = 8.dp, vertical=8.dp).fillMaxWidth()
            ) {
                Text(
                    text = "See All News",
                    color = Color(0xFF00704A),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

        }
    }
}