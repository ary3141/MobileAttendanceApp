package com.example.mobilesurapp.UIApp.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilesurapp.UIApp.login.LoginStateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    loginStateViewModel: LoginStateViewModel,
    onNavigateToEditProfile: (String, String, String) -> Unit
) {

    val adminProfile by viewModel.adminProfile.collectAsState()
    val loggedInAdminId by loginStateViewModel.currentAdminId.collectAsState()

    LaunchedEffect(loggedInAdminId) {
        loggedInAdminId?.let {
            viewModel.setAdminId(it)
        }
    }

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text(
                        "Settings",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF5B3FD8)
                )

            )

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            ProfileHeaderCard(
                name = adminProfile?.name ?: "",
                email = adminProfile?.email ?: "",
                onClick = {
                    adminProfile?.let {
                        onNavigateToEditProfile(
                            it.id,
                            it.name,
                            it.email
                        )
                    }
                }
            )

            Text(
                "Account",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            SettingsCard {

                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Change Password"
                ) { }

                HorizontalDivider()

                SettingsItem(
                    icon = Icons.Default.Notifications,
                    title = "Notification Settings"
                ) { }

                HorizontalDivider()

                SettingsItem(
                    icon = Icons.Default.Security,
                    title = "Security & PIN"
                ) { }

            }

            Text(
                "General",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            SettingsCard {

                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "System Information"
                ) { }

                HorizontalDivider()

                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.Help,
                    title = "Help & Support"
                ) { }

                HorizontalDivider()

                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "About MobileSur",
                    trailing = "v1.0.0"
                ) { }

            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(

                onClick = {

                    loginStateViewModel.logout()

                    navController.navigate("login") {
                        popUpTo("camera") {
                            inclusive = true
                        }
                    }

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                )

            ) {

                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text("Log Out")

            }

        }

    }

}

@Composable
fun ProfileHeaderCard(
    name: String,
    email: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                Icons.Default.AccountCircle,
                null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

            }

            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                null
            )

        }

    }

}

@Composable
fun SettingsCard(
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column {
            content()
        }
    }

}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    trailing: String? = null,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            icon,
            null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )

        if (trailing != null) {

            Text(
                trailing,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.size(8.dp))

        }

        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            null,
            tint = Color.Gray
        )

    }

}