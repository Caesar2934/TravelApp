package com.example.travelapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.travelapp.R
import com.example.travelapp.data.local.entity.Destination
import com.example.travelapp.viewModel.HomeViewModel
import java.text.Normalizer
import java.util.*

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val destinations by viewModel.destinations.collectAsState()
    var selectedBottomNavItem by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredDestinations = destinations.filter {
        it.name.removeAccents().contains(searchQuery.removeAccents(), ignoreCase = true)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, modifier = Modifier.height(80.dp)) {
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(70.dp))
                    },
                    selected = selectedBottomNavItem == 0,
                    onClick = { selectedBottomNavItem = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        unselectedIconColor = Color(0xFF1B5E20)
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", modifier = Modifier.size(70.dp))
                    },
                    selected = selectedBottomNavItem == 1,
                    onClick = { selectedBottomNavItem = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        unselectedIconColor = Color(0xFF1B5E20)
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(top = 72.dp, start = 16.dp, end = 16.dp, bottom = 96.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Trang chủ", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.Transparent, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "Profile Icon",
                        colorFilter = ColorFilter.tint(Color(0xFF1B5E20)),
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = (-20).dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_dalat),
                    contentDescription = "Đà Lạt",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(modifier = Modifier.align(Alignment.TopStart).padding(12.dp)) {
                    Text("Hello, Caesar", color = Color.White, fontSize = 26.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Đà Lạt", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        cursorBrush = SolidColor(Color.Gray),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text("Tìm kiếm địa điểm...", color = Color.Gray, fontSize = 16.sp)
                            }
                            innerTextField()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Điểm đến hàng đầu",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filteredDestinations.forEach { destination ->
                    TopDestinationCard(destination = destination) {
                        navController.navigate("intro/${destination.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun TopDestinationCard(destination: Destination, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(285.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(255.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = destination.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

fun String.removeAccents(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        .lowercase(Locale.getDefault())
}
