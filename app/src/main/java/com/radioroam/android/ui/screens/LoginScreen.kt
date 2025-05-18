package com.radioroam.android.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.radioroam.android.data.AppDatabase
import com.radioroam.android.data.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("با نوا، صدای زندگی رو بشنو", style = MaterialTheme.typography.titleLarge,)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("نام کاربری",style = MaterialTheme.typography.labelSmall) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("رمز عبور",style = MaterialTheme.typography.labelSmall) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val user = userDao.login(email, password)
                    if (user != null) {
                        Toast
                            .makeText(context, "وقت شنیدنه، پلی رو بزن!", Toast.LENGTH_SHORT)
                            .show()
                        val prefs = UserPreferences(context)
                        prefs.saveEmail(email)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        Toast
                            .makeText(context, "ایمیل یا رمز اشتباه است", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("برو بریم...",style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            navController.navigate("register")
        }) {
            Text("هنوز ثبت‌نام نکردی؟ ثبت‌نام کن",style = MaterialTheme.typography.headlineSmall)
        }
    }
}

