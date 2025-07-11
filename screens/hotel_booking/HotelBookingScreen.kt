package com.example.travelapp.screens.hotel_booking

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelBookingScreen(
    navController: NavHostController,
    selectedProvince: String, // ✅ THÊM: nhận tỉnh từ NavHost
    onBackClick: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    var selectedBottomNavItem by remember { mutableIntStateOf(0) }

    // Các danh sách còn lại giữ nguyên
    val roomTypeList = listOf("Tiêu chuẩn", "Cao cấp", "Suite")
    val guestCountList = (1..5).map { it.toString() }

    var selectedRoomType by remember { mutableStateOf(roomTypeList[0]) }
    var selectedGuestCount by remember { mutableStateOf(guestCountList[0]) }

    var checkInDate by remember { mutableStateOf("") }
    var checkOutDate by remember { mutableStateOf("") }

    fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                onDateSelected("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HotelBookingContent(
                selectedProvince = selectedProvince,
                roomTypeList = roomTypeList,
                guestCountList = guestCountList,
                selectedRoomType = selectedRoomType,
                selectedGuestCount = selectedGuestCount,
                checkInDate = checkInDate,
                checkOutDate = checkOutDate,
                onRoomTypeSelected = { selectedRoomType = it },
                onGuestCountSelected = { selectedGuestCount = it },
                onCheckInDateSelected = { checkInDate = it },
                onCheckOutDateSelected = { checkOutDate = it },
                onSearchClick = {
                    navController.navigate("hotel_result/${selectedProvince}")
                },
                showDatePicker = { onDateSelected -> showDatePicker(onDateSelected) }
            )
        }
    }
}

@Composable
private fun HotelBookingContent(
    selectedProvince: String,
    roomTypeList: List<String>,
    guestCountList: List<String>,
    selectedRoomType: String,
    selectedGuestCount: String,
    checkInDate: String,
    checkOutDate: String,
    onRoomTypeSelected: (String) -> Unit,
    onGuestCountSelected: (String) -> Unit,
    onCheckInDateSelected: (String) -> Unit,
    onCheckOutDateSelected: (String) -> Unit,
    onSearchClick: () -> Unit,
    showDatePicker: ((String) -> Unit) -> Unit
) {
    Text(
        text = "Đặt phòng tại $selectedProvince",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1B5E20),
        modifier = Modifier.padding(bottom = 16.dp)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = checkInDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Check-in") },
                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { showDatePicker(onCheckInDateSelected) }
            )

            OutlinedTextField(
                value = checkOutDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Check-out") },
                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { showDatePicker(onCheckOutDateSelected) }
            )

            CustomDropdown(
                label = "Số lượng khách",
                selectedItem = selectedGuestCount,
                items = guestCountList,
                onItemSelected = onGuestCountSelected,
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
            )

            CustomDropdown(
                label = "Kiểu phòng",
                selectedItem = selectedRoomType,
                items = roomTypeList,
                onItemSelected = onRoomTypeSelected,
                leadingIcon = { Icon(Icons.Default.Bed, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSearchClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Tìm kiếm", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
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

