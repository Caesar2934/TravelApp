package com.example.travelapp.screens.Review

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.R
import com.example.travelapp.data.local.entity.ReviewEntity
import com.example.travelapp.viewModel.ReviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavHostController,
    viewModel: ReviewViewModel = viewModel()
) {
    var isWriting by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var content by remember { mutableStateOf(TextFieldValue("")) }
    var selectedBottomNavItem by remember { mutableStateOf(0) }

    val reviews by viewModel.reviews.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF1B5E20)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* TODO: Profile icon action */ },
                        modifier = Modifier
                            .padding(end = 16.dp, top = 12.dp)
                            .size(70.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = "Profile Icon",
                            modifier = Modifier.size(60.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF1B5E20))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.height(80.dp)
            ) {
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
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Đánh giá", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { isWriting = !isWriting },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Viết đánh giá", color = Color.White, fontSize = 28.sp)
            }

            if (isWriting) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Tiêu đề") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Nội dung") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (title.text.isNotBlank() && content.text.isNotBlank()) {
                            viewModel.addReview("Người dùng", title.text, content.text)
                            title = TextFieldValue("")
                            content = TextFieldValue("")
                            isWriting = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Gửi", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            reviews.forEach { review ->
                ReviewItem(review)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ReviewItem(review: ReviewEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(review.author, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                Text(review.date, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(review.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(review.content, fontSize = 14.sp, lineHeight = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewScreen() {
    val navController = rememberNavController()
    ReviewScreen(navController = navController)
}
