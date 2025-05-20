package com.radioroam.android.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.radioroam.android.R
import com.radioroam.android.data.AppDatabase
import com.radioroam.android.data.UserPreferences
import com.radioroam.android.ui.navigation.Screen
import com.radioroam.android.ui.theme.AppTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // بک‌گراند
            Image(
                painter = painterResource(id = R.drawable.login), // ← مطمئن شو login در پوشه drawable هست
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // لایه تیره شفاف
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
            )

            // فرم ورود
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("با نوا، صدای زندگی رو بشنو", style = MaterialTheme.typography.titleLarge,color = Color.White , fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("نام کاربری", color = Color.White,style = MaterialTheme.typography.headlineSmall) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("رمز عبور", color = Color.White,style = MaterialTheme.typography.headlineSmall) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {coroutineScope.launch {
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
                    } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 80.dp),  colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD3A360), // رنگ پس‌زمینه دکمه
                        contentColor = Color.White         // رنگ متن دکمه
                    )
                ) {
                    Text("برو بریم ...",style = MaterialTheme.typography.headlineMedium)
                }

                TextButton(
                    onClick = {
                        navController.navigate(Screen.Register.title)
                    }
                ) {
                    Text("هنوز ثبت‌نام نکردی؟ ثبت‌نام کن",style = MaterialTheme.typography.labelSmall,color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}
