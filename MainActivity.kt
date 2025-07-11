package com.example.travelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelapp.screens.AcessScreen.LoginScreen
import com.example.travelapp.screens.AcessScreen.OnboardingScreen
import com.example.travelapp.screens.AcessScreen.RegisterScreen
import com.example.travelapp.screens.BoughtHistoryScreen.HistoryScreen
import com.example.travelapp.screens.HomeScreen
import com.example.travelapp.screens.IntroScreen
import com.example.travelapp.screens.Review.ReviewScreen
import com.example.travelapp.screens.UserProfileScreen
import com.example.travelapp.screens.bus_ticket.BusBookingScreen
import com.example.travelapp.screens.bus_ticket.BusResultScreen
import com.example.travelapp.screens.hotel_booking.HotelBookingResult
import com.example.travelapp.screens.hotel_booking.HotelBookingScreen
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            NomadGoApp()
        }
    }
}

@Composable
fun NomadGoApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnboardingScreen {
                navController.navigate("login")
            }
        }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("bus_booking") { BusBookingScreen(navController) }

        composable("hotel_result/{location}") { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            HotelBookingResult(
                navController = navController,
                selectedLocation = location
            )
        }

        composable(
            "history?tab={tab}",
            arguments = listOf(navArgument("tab") { defaultValue = "ticket" })
        ) { backStackEntry ->
            val selectedTab = backStackEntry.arguments?.getString("tab") ?: "ticket"
            HistoryScreen(navController = navController, selectedTab = selectedTab)
        }


        composable("intro/{destinationId}") { backStackEntry ->
            val destinationId = backStackEntry.arguments?.getString("destinationId")?.toIntOrNull()
            if (destinationId != null) {
                IntroScreen(destinationId = destinationId, navController = navController)
            }
        }
        composable("bus_booking/{province}") { backStackEntry ->
            val province = backStackEntry.arguments?.getString("province") ?: ""
            BusBookingScreen(navController = navController, selectedProvince = province)
        }

        composable("bus_result/{province}") { backStackEntry ->
            val province = backStackEntry.arguments?.getString("province") ?: ""
            BusResultScreen(navController = navController, selectedProvince = province)
        }


        composable("hotel_booking/{location}") { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            HotelBookingScreen(navController = navController, selectedProvince = location)
        }

        composable("profile") {
            UserProfileScreen(navController = navController)
        }


        composable("review") {
            ReviewScreen(navController)
        }
    }
}
