package com.example.starbucs_clone

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starbucs_clone.ui.theme.Starbucs_CloneTheme
import java.util.Calendar

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Starbucs_CloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Starbucs_CloneTheme {
        RegisterScreen(modifier= Modifier)
    }
}
@Composable
fun RegisterScreen(modifier: Modifier) {
    val context = LocalContext.current
    var nama_depan by remember { mutableStateOf("") }
    var nama_belakang by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var noTelp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var DoB by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar() }, // <- This makes the TopBar sticky
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1EBEB))
        ) {
            item {
                Fields(
                    nama_depan, { nama_depan = it },
                    nama_belakang, { nama_belakang = it },
                    email, { email = it },
                    noTelp, { noTelp = it },
                    password, { password = it },
                    DoB, { DoB = it }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Register",
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
}

@Composable
fun Fields(
    nama_depan: String, onNamaDepanChange: (String) -> Unit,
    nama_belakang: String, onNamaBelakangChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    noTelp: String, onNoTelpChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    DoB: String, onDobChange: (String) -> Unit
) {
    var context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember{ mutableStateOf(false) }
    var tosChecked by remember { mutableStateOf(false) }
    var privacyChecked by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val dobPickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val today = Calendar.getInstance()

            if (selectedCalendar.after(today)) {
                Toast.makeText(context, "Tanggal tidak boleh di masa depan", Toast.LENGTH_SHORT).show()
            } else {
                onDobChange("$dayOfMonth/${month + 1}/$year")
            }
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isFormValid(): Boolean {
        val emailValid = isValidEmail(email)
        val passwordValid = password.length >= 8 && password.any { it.isDigit() }
        val fieldsFilled = nama_depan.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        val passwordsMatch = password == confirmPassword
        val phoneValid = noTelp.all { it.isDigit() } && noTelp.length >= 10
        return fieldsFilled && emailValid && phoneValid && passwordsMatch && passwordValid && tosChecked && privacyChecked
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email (*)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
        Column {
            if(!email.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "Harus terisi",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
            if(!isValidEmail(email)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "Email tidak valid",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password (*)") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible }
                        .padding(end = 8.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
        Column {
            if ((password.length < 8) or (password.length > 25)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "8 - 25 karakter",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
            if (!password.any { it.isDigit() }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "Minimal 1 angka numerik",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
            if (!password.any { it.isUpperCase() }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "Minimal 1 huruf kapital",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
            if (!password.any { it.isLowerCase() }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "Minimal 1 huruf non-kapital",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
            if (!password.any { !it.isLetterOrDigit() }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                    )
                    Text(
                        text = "Minimal 1 karakter spesial",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Konfirmasi Password (*)") },
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { confirmPasswordVisible = !confirmPasswordVisible }
                        .padding(end = 8.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
        if (password != confirmPassword) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                )
                Text(
                    text = "Password tidak cocok",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = nama_depan,
            onValueChange = onNamaDepanChange,
            label = { Text("Nama Depan (*)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
        if (nama_depan.isBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                )
                Text(
                    text = "Harus terisi",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nama_belakang,
            onValueChange = onNamaBelakangChange,
            label = { Text("Nama Belakang") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = noTelp,
            onValueChange = onNoTelpChange,
            label = { Text("Nomor Telepon (*)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
        if (noTelp.isBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                )
                Text(
                    text = "Harus terisi",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
//                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            TextButton(onClick = { dobPickerDialog.show() }) {
                Text(
                    text = if (DoB.isEmpty()) "Pilih Tanggal Lahir" else "Tanggal Lahir: $DoB",
                    fontSize = 18.sp
                )
            }
        }
        if (DoB.isBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(top = 4.dp, end = 32.dp).size(32.dp)
                )
                Text(
                    text = "Harus terisi",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = tosChecked,
                onCheckedChange = { tosChecked = it }
            )
            Text("Saya menyetujui Ketentuan Layanan")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = privacyChecked,
                onCheckedChange = { privacyChecked = it }
            )
            Text("Saya menyetujui Kebijakan Privasi")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                //                registerUser()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid()
        ) {
            Text(
                text = "Register",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sudah punya akun?",
                fontSize = 18.sp
            )
            Text(
                text = "Login",
                color = Color(0xFF00704A),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}