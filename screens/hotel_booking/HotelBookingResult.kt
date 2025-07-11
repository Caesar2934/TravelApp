package com.example.travelapp.screens.hotel_booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelapp.data.local.entity.HotelRoom
import com.example.travelapp.viewModel.BookingHistoryViewModel
import com.example.travelapp.viewModel.HotelRoomViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelBookingResult(
    navController: NavController,
    selectedLocation: String,
    viewModel: HotelRoomViewModel = hiltViewModel(),
    onBackClick: () -> Unit = { navController.popBackStack() }
) {
    val coroutineScope = rememberCoroutineScope()
    val rooms by viewModel.searchResults.collectAsState()
    val historyViewModel: BookingHistoryViewModel = hiltViewModel()
    var selectedBottomNavItem by remember { mutableIntStateOf(0) }
    val selectedRoom = remember { mutableStateOf<HotelRoom?>(null) }

    // ✅ Snackbar để hiển thị thông báo
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    // Gọi tìm kiếm theo địa điểm khi mở màn
    LaunchedEffect(selectedLocation) {
        viewModel.searchRoomsByLocation(selectedLocation)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chọn phòng") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1B5E20))
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
        },
        snackbarHost = {
            androidx.compose.material3.SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Kết quả tìm kiếm", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    LazyColumn(modifier = Modifier.height(400.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(rooms) { room ->
                            RoomOptionCard(
                                room = room,
                                isSelected = selectedRoom.value?.id == room.id,
                                onSelect = { selectedRoom.value = room }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            selectedRoom.value?.let {
                                historyViewModel.addRoomToHistory(it)

                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Thanh toán thành công")
                                    delay(1000)
                                    navController.navigate("history?tab=room") {
                                        popUpTo("hotel_result/${selectedLocation}") { inclusive = true }
                                    }

                                }

                                selectedRoom.value = null
                            }
                        },
                        enabled = selectedRoom.value != null,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Thanh toán", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
@Composable
fun RoomOptionCard(
    room: HotelRoom,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
            .selectable(selected = isSelected, onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Bed,
            contentDescription = null,
            tint = Color(0xFF1B5E20),
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(room.name, fontWeight = FontWeight.Bold)
            Text(room.bedType)
            Text(room.size)
            room.features.split(",").forEach {
                Text("• ${it.trim()}", fontSize = 12.sp)
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(text = "${room.price}", fontWeight = FontWeight.Bold)
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1B5E20))
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    navController: NavController
) {
    NavigationBar(containerColor = Color.White, modifier = Modifier.height(80.dp)) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(70.dp)) },
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
            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu", modifier = Modifier.size(70.dp)) },
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

