package com.example.travelapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travelapp.viewModel.HomeViewModel
import com.example.travelapp.viewModel.HotelRoomViewModel

@Composable
fun IntroScreen(
    destinationId: Int,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    hotelRoomViewModel: HotelRoomViewModel = hiltViewModel()
) {
    val destinations by viewModel.destinations.collectAsState()
    val destination = destinations.find { it.id == destinationId }

    if (destination == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Không tìm thấy địa điểm")
        }
        return
    }

    var selectedBottomNavItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.height(100.dp)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(70.dp)
                        )
                    },
                    selected = selectedBottomNavItem == 0,
                    onClick = {
                        selectedBottomNavItem = 0
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
                    selected = selectedBottomNavItem == 1,
                    onClick = {
                        selectedBottomNavItem = 1
                        navController.navigate("profile")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        unselectedIconColor = Color(0xFF1B5E20)
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF1B5E20),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Text("Giới thiệu", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.Transparent, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        tint = Color(0xFF1B5E20),
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (-10).dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                destination.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFDFF0D8),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "📍 ${destination.name}",
                    color = Color(0xFF388E3C),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Text(
                destination.description,
                fontSize = 18.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                val iconBackgroundColor = Color(0xFF388E3C).copy(alpha = 0.1f)
                val iconTint = Color(0xFF388E3C)
                val iconSize = 44.dp
                val iconBoxSize = 64.dp

                @Composable
                fun IconItem(icon: ImageVector, label: String, onClick: () -> Unit) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { onClick() }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFF388E3C).copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = Color(0xFF388E3C),
                                modifier = Modifier.size(44.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }

                IconItem(Icons.Default.DirectionsCar, "Di chuyển") {
                    navController.navigate("bus_booking/${destination.name}")
                }

                IconItem(Icons.Default.Hotel, "Khách sạn") {
                    hotelRoomViewModel.insertSampleRoomsForAllProvinces()
                    navController.navigate("hotel_booking/${destination.name}")
                }

                IconItem(Icons.Default.Schedule, "Lịch trình") {
                }

                IconItem(Icons.Default.Star, "Đánh giá") {
                    navController.navigate("review")
                }
            }
        }
    }
}
