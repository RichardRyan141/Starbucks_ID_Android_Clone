package com.example.starbucs_clone

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starbucs_clone.ui.theme.Starbucs_CloneTheme
import com.example.starbucs_clone.values.color_starbucksGreen
import java.text.NumberFormat
import java.util.Locale

class TopUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Starbucs_CloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopUpScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpPreview() {
    Starbucs_CloneTheme {
        TopUpScreen(modifier= Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpScreen(modifier: Modifier) {
    SessionManager.loggedInUserId = 1
    var user by remember { mutableStateOf(SessionManager.getLoggedInUser()!!) }

    var selectedItem by remember { mutableStateOf(1) }
    var topUpBalance by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF1EBEB),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TOP UP",
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Navbar(selectedItem, {selectedItem = it})
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 32.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.card_preview),
                    contentDescription = "SB Card preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }

            Text(
                text = user.nama_depan + " " + user.nama_belakang,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            )
            Text(
                text = "Rp. ${NumberFormat.getNumberInstance(Locale.US).format(user.balance)}",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            )
            Text(
                text = "Select Top Up Amount",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    val buttonModifier = Modifier.weight(1f)

                    Button(
                        onClick = { topUpBalance = 50000 },
                        modifier = buttonModifier,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Rp. 50k",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = { topUpBalance = 100000 },
                        modifier = buttonModifier,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Rp. 100k",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = { topUpBalance = 200000 },
                        modifier = buttonModifier,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Rp. 200k",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = { topUpBalance = 300000 },
                        modifier = buttonModifier,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Rp. 300k",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            if(topUpBalance != 0) {
                Text(
                    text = "Rp. ${NumberFormat.getNumberInstance(Locale.US).format(topUpBalance)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    user = user.copy(balance = user.balance + topUpBalance)
                    topUpBalance = 0

                    SessionManager.updateLoggedInUser(user)
                },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color_starbucksGreen),
                modifier = Modifier
                    .padding(top=8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "TOP UP",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }
}