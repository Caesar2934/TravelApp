package com.example.travelapp.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(70.dp)
                )
            },
            selected = selectedItem == 0,
            onClick = {
                onItemClick(0)
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B5E20),
                unselectedIconColor = Color(0xFF1B5E20)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(70.dp)
                )
            },
            selected = selectedItem == 1,
            onClick = {
                onItemClick(1)
                navController.navigate("profile") {
                    popUpTo("profile") { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B5E20),
                unselectedIconColor = Color(0xFF1B5E20)
            )
        )
    }
}