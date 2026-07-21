package com.example.mobilesurapp.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {

    object Login : Screen("login")

    object BiometricLogin : Screen("biometricLogin")

    object Dashboard : Screen("dashboard")

    object Attendance : Screen("attendance")

    object Employee : Screen("employee")

    object AddEmployee : Screen("addEmployee")

    object Profile : Screen("profile")

    object EditProfile :
        Screen("editProfile/{adminId}/{name}/{email}") {

        fun createRoute(
            adminId: String,
            name: String,
            email: String
        ): String {

            val safeName = URLEncoder.encode(
                name,
                StandardCharsets.UTF_8.toString()
            )

            val safeEmail = URLEncoder.encode(
                email,
                StandardCharsets.UTF_8.toString()
            )

            return "editProfile/$adminId/$safeName/$safeEmail"
        }
    }

    object ReAuth :
        Screen("reauth/{targetDestination}") {

        fun createRoute(
            destination: String
        ) = "reauth/$destination"

    }
}