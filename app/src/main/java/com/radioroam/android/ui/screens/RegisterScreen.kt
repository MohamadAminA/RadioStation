package com.radioroam.android.ui.screens


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.radioroam.android.data.AppDatabase
import com.radioroam.android.data.model.station.User
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ثبت‌نام در سرزمین نوا", style = MaterialTheme.typography.titleLarge, color =Color(
            0xFF44311B
        )
        )
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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("تکرار رمز عبور",style = MaterialTheme.typography.labelSmall) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (password != confirmPassword) {
                        Toast.makeText(context, "رمزها مطابقت ندارن!", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val existingUser = userDao.getUserByEmail(email)
                    if (existingUser != null) {
                        Toast.makeText(context, "این ایمیل قبلاً ثبت شده", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val newUser = User(email = email, password = password)
                    userDao.insert(newUser)
                    Toast.makeText(context, "ثبت‌نام موفق. لطفاً وارد شوید", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD3A360), // رنگ پس‌زمینه دکمه
                contentColor = Color.White
            )) {
            Text("ثبت ‌نام",style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegesterScreenPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}
