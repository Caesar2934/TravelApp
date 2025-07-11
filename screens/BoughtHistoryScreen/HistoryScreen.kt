@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.travelapp.screens.BoughtHistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelapp.data.local.entity.BusTicketHistory
import com.example.travelapp.data.local.entity.HotelRoomHistory
import com.example.travelapp.viewModel.BookingHistoryViewModel

@Composable
fun HistoryScreen(
    navController: NavController,
    selectedTab: String = "ticket",
    viewModel: BookingHistoryViewModel = hiltViewModel(),
    onBackClick: () -> Unit = { navController.popBackStack() } // default là back
) {
    var currentTab by remember { mutableStateOf("ticket") }

    LaunchedEffect(selectedTab) {
        currentTab = selectedTab
    }
    var selectedBottomNavItem by remember { mutableStateOf(0) }

    val ticketHistory by viewModel.busHistory.collectAsState()
    val roomHistory by viewModel.roomHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF1B5E20)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedBottomNavItem,
                onItemClick = { selectedBottomNavItem = it },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                "Lịch sử mua",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("ticket", "room").forEach { tab ->
                    val label = if (tab == "ticket") "Vé" else "Phòng"
                    val selected = tab == currentTab
                    Button(
                        onClick = { currentTab = tab },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selected) Color(0xFF1B5E20) else Color.Transparent,
                            contentColor = if (selected) Color.White else Color.Gray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (currentTab == "ticket") {
                TicketHistoryList(ticketHistory)
            } else {
                RoomHistoryList(roomHistory)
            }
        }
    }
}

@Composable
fun TicketHistoryList(tickets: List<BusTicketHistory>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        tickets.forEach { ticket ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(ticket.route, fontWeight = FontWeight.Bold)
                        Text("${ticket.price}đ", fontWeight = FontWeight.Bold)
                    }
                    Text(ticket.time, fontSize = 16.sp)
                    Text("Còn ${ticket.remainingSeats} chỗ", fontSize = 16.sp)
                    Text(ticket.busType, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun RoomHistoryList(rooms: List<HotelRoomHistory>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        rooms.forEach { room ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(room.name, fontWeight = FontWeight.Bold)
                        Text("${room.price}đ", fontWeight = FontWeight.Bold)
                    }
                    Text(room.bedType, fontSize = 13.sp)
                    Text(room.size, fontSize = 13.sp)
                    room.features.split(",").forEach { feature ->
                        Text("• ${feature.trim()}", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
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

