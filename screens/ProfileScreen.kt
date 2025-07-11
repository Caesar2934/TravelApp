package com.example.travelapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelapp.R
import com.example.travelapp.data.local.entity.UserEntity
import com.example.travelapp.model.MenuItem
import com.example.travelapp.viewModel.UserProfileViewModel

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.lastMenuClicked) {
        when (uiState.lastMenuClicked) {
            "history" -> {
                navController.navigate("history?tab=all")
                viewModel.clearLastMenuClicked()
            }
        }
    }

    UserProfileContent(
        user = uiState.user,
        menuItems = uiState.menuItems,
        selectedBottomNavItem = uiState.selectedBottomNavItem,
        isLoading = uiState.isLoading,
        onMenuItemClick = viewModel::onMenuItemClick,
        onBottomNavItemClick = { index ->
            viewModel.onBottomNavItemClick(index)
            if (index == 0) {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserProfileContent(
    user: UserEntity,
    menuItems: List<MenuItem>,
    selectedBottomNavItem: Int,
    isLoading: Boolean,
    onMenuItemClick: (String) -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedBottomNavItem,
                onItemClick = onBottomNavItemClick
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header với tên và avatar
                UserHeader(user = user)

                Spacer(modifier = Modifier.height(24.dp))

                // Menu items
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(menuItems) { item ->
                        MenuItemRow(
                            menuItem = item,
                            onClick = { onMenuItemClick(item.id) }
                        )
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF1B5E20)
                    )
                }
            }
        }
    }
}

@Composable
private fun UserHeader(user: UserEntity) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Profile Icon",
                colorFilter = ColorFilter.tint(Color(0xFF1B5E20)),
                modifier = Modifier.size(90.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "Danh mục",
                fontSize = 20.sp,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = user.username.ifBlank { "Caesar" },
                fontSize = 24.sp,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun MenuItemRow(
    menuItem: MenuItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.title,
                tint = Color(0xFF1B5E20),
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = menuItem.title,
                fontSize = 20.sp,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Arrow",
                tint = Color(0xFF1B5E20),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit
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
                    modifier = Modifier.size(40.dp)
                )
            },
            selected = selectedItem == 0,
            onClick = { onItemClick(0) },
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
                    modifier = Modifier.size(40.dp)
                )
            },
            selected = selectedItem == 1,
            onClick = { onItemClick(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B5E20),
                unselectedIconColor = Color(0xFF1B5E20)
            )
        )
    }
}
