package com.example.mobilesurapp.UIApp.login

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilesurapp.UIApp.components.background.LoginBackground
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (String) -> Unit,
    onNavigateToBiometricLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoggingIn by viewModel.isLoggingIn.collectAsState()
    val loginError by viewModel.loginError.collectAsState()

    LaunchedEffect(isLoggingIn) {
        Log.d("LoginScreen", "isLoggingIn = $isLoggingIn")
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val passwordIcon =
        if (isPasswordVisible)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility

    val passwordDescription =
        if (isPasswordVisible)
            "Hide Password"
        else
            "Show Password"

    LaunchedEffect(loginError) {
        loginError?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        LoginBackground {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(48.dp))

                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF4F46E5),
                        modifier = Modifier.height(72.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 430.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.96f)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(28.dp)
                        ) {

                            Text(
                                text = "Welcome Back! 👋",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Sign in to your administrator account",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            OutlinedTextField(
                                value = email,
                                onValueChange = viewModel::onEmailChange,
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(18.dp),

                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Email,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text("Email Address")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    autoCorrectEnabled = false
                                )
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            OutlinedTextField(
                                value = password,
                                onValueChange = viewModel::onPasswordChange,
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(18.dp),

                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text("Password")
                                },
                                visualTransformation =
                                    if (isPasswordVisible)
                                        VisualTransformation.None
                                    else
                                        PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    autoCorrectEnabled = false
                                ),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            isPasswordVisible = !isPasswordVisible
                                        }
                                    ) {
                                        Icon(
                                            imageVector = passwordIcon,
                                            contentDescription = passwordDescription
                                        )
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = {
                                    viewModel.login(
                                        onSuccess = { adminId ->
                                            onLoginSuccess(adminId)
                                        }
                                    )
                                },
                                enabled = !isLoggingIn,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF5B3DF5)
                                )
                            ) {

                                if (isLoggingIn) {

                                    CircularProgressIndicator(
                                        modifier = Modifier.size(22.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = "Signing In..."
                                    )

                                } else {

                                    Text(
                                        text = "Sign In",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                }

                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                HorizontalDivider(
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = " OR ",
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = Color.Gray
                                )

                                HorizontalDivider(
                                    modifier = Modifier.weight(1f)
                                )

                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedButton(
                                onClick = {
                                    onNavigateToRegister()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {

                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    "Create Administrator",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "🔒 Your information is securely stored.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Mobile Attendance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                }

            }

        }

    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Login Screen"
)
@Composable
private fun LoginPreview() {

    MobileSurAppTheme {

        LoginScreen(
            onLoginSuccess = {},
            onNavigateToBiometricLogin = {},
            onNavigateToRegister = {}
        )

    }

}