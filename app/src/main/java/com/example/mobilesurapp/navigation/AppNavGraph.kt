package com.example.mobilesurapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import com.example.mobilesurapp.UIApp.login.LoginScreen
import com.example.mobilesurapp.UIApp.login.LoginStateViewModel
import com.example.mobilesurapp.UIApp.login.ReAuthScreen

import com.example.mobilesurapp.UIApp.dashboard.DashboardScreen

import com.example.mobilesurapp.UIApp.attendance.AttendanceRoute

import com.example.mobilesurapp.UIApp.employee.EmployeeRoute
import com.example.mobilesurapp.UIApp.add_employee.AddEmployeeRoute

import com.example.mobilesurapp.UIApp.profile.ProfileScreen
import com.example.mobilesurapp.UIApp.edit_profile.EditProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    loginStateViewModel: LoginStateViewModel,
    startDestination: String
) {

    val activityViewModelStoreOwner =
        checkNotNull(
            LocalContext.current as? androidx.lifecycle.ViewModelStoreOwner
        ) {
            "AppNavGraph harus berada dalam konteks ViewModelStoreOwner"
        }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /*
         * -------------------------
         * LOGIN
         * -------------------------
         */

        composable("login") {

            LoginScreen(

                onLoginSuccess = { adminId ->

                    loginStateViewModel.setLoggedInAdmin(adminId)

                    navController.navigate("dashboard") {

                        popUpTo("login") {
                            inclusive = true
                        }

                    }

                },

                // Temporary
                onNavigateToBiometricLogin = {
                    navController.navigate("biometric_placeholder")
                }

            )

        }

        /*
         * -------------------------
         * DASHBOARD
         * -------------------------
         */

        composable("dashboard") {

            DashboardScreen(

                onAttendanceClick = {

                    navController.navigate("camera")

                },

                onEmployeeClick = {

                    navController.navigate("employee")

                },

                onSettingsClick = {

                    navController.navigate("profile")

                }

            )

        }

        /*
         * -------------------------
         * ATTENDANCE
         * -------------------------
         */

        composable("camera") {

            AttendanceRoute(

                onExitClick = {

                    navController.popBackStack()

                }

            )

        }

        /*
         * -------------------------
         * EMPLOYEE
         * -------------------------
         */

        composable("employee") {

            EmployeeRoute(

                onBackClick = {

                    navController.popBackStack()

                },

                onAddEmployeeClick = {

                    navController.navigate("addEmployee")

                },

                onEmployeeClick = { employeeId ->

                    // TODO
                    // Employee Detail

                }

            )

        }
        /*
 * -------------------------
 * ADD EMPLOYEE
 * -------------------------
 */

        composable("addEmployee") {

            AddEmployeeRoute(

                onBackClick = {

                    navController.popBackStack()

                }

            )

        }

        /*
         * -------------------------
         * PROFILE
         * -------------------------
         */

        composable("profile") {

            ProfileScreen(

                navController = navController,

                loginStateViewModel = loginStateViewModel,

                onNavigateToEditProfile = { adminId, name, email ->

                    val safeName =
                        URLEncoder.encode(
                            name,
                            StandardCharsets.UTF_8.toString()
                        )

                    val safeEmail =
                        URLEncoder.encode(
                            email,
                            StandardCharsets.UTF_8.toString()
                        )

                    navController.navigate(
                        "UpdateProfile/$adminId/$safeName/$safeEmail"
                    )

                }

            )

        }

        /*
         * -------------------------
         * EDIT PROFILE
         * -------------------------
         */

        composable(

            route = "UpdateProfile/{adminId}/{name}/{email}",

            arguments = listOf(

                navArgument("adminId") {
                    type = NavType.StringType
                },

                navArgument("name") {
                    type = NavType.StringType
                },

                navArgument("email") {
                    type = NavType.StringType
                }

            )

        ) { backStackEntry ->

            val adminId =
                backStackEntry.arguments?.getString("adminId") ?: ""

            val name =
                backStackEntry.arguments?.getString("name") ?: ""

            val email =
                backStackEntry.arguments?.getString("email") ?: ""

            val decodedName =
                java.net.URLDecoder.decode(
                    name,
                    StandardCharsets.UTF_8.toString()
                )

            val decodedEmail =
                java.net.URLDecoder.decode(
                    email,
                    StandardCharsets.UTF_8.toString()
                )

            EditProfileScreen(

                adminId = adminId,

                initialName = decodedName,

                initialEmail = decodedEmail,

                onBackClick = {

                    navController.popBackStack()

                }

            )

        }

        /*
         * -------------------------
         * REAUTH
         * -------------------------
         */

        composable("reauth") {

            ReAuthScreen(

                onLoginSuccess = { adminId ->

                    loginStateViewModel.setLoggedInAdmin(adminId)

                    navController.popBackStack()

                },

                onNavigateBack = {

                    navController.popBackStack()

                }

            )

        }

        /*
         * -------------------------
         * TEMP PLACEHOLDERS
         * -------------------------
         */

        composable("biometric_placeholder") {

            androidx.compose.material3.Surface {

                androidx.compose.material3.Text(
                    text = "Biometric Login Coming Soon"
                )

            }

        }

        composable("addFace") {

            androidx.compose.material3.Surface {

                androidx.compose.material3.Text(
                    text = "Add Face has been replaced by Add Employee"
                )

            }

        }

    }

}