package com.example.travelapp.screens.bus_ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelapp.data.local.entity.BusTicket
import com.example.travelapp.data.local.entity.BusTicketHistory
import com.example.travelapp.viewModel.BookingHistoryViewModel
import com.example.travelapp.viewModel.BusTicketViewModel
import com.example.travelapp.screens.BottomNavigationBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusResultScreen(
    navController: NavController,
    selectedProvince: String,
    onBackClick: () -> Unit = { navController.popBackStack() }
) {
    val bookingHistoryViewModel: BookingHistoryViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val viewModel: BusTicketViewModel = hiltViewModel()
    val tickets by viewModel.searchResults.collectAsState()
    val selectedTicket = remember { mutableStateOf<BusTicket?>(null) }

    LaunchedEffect(selectedProvince) {
        viewModel.getTicketsByProvince(selectedProvince) { results ->
            viewModel.saveSearchResults(results)
        }
    }

    var selectedBottomNavItem by remember { mutableStateOf(0) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                onItemClick = { index: Int -> selectedBottomNavItem = index },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            BusResultContent(
                tickets = tickets,
                selectedTicket = selectedTicket.value,
                onSelect = { selectedTicket.value = it },
                onPayClick = {
                    selectedTicket.value?.let { selected ->
                        coroutineScope.launch {
                            bookingHistoryViewModel.insertTicket(
                                BusTicketHistory(
                                    route = selected.route,
                                    time = selected.time,
                                    price = selected.price,
                                    remainingSeats = selected.remainingSeats,
                                    busType = selected.busType,
                                    bookingTime = System.currentTimeMillis().toString()
                                )
                            )
                            snackbarHostState.showSnackbar("Thanh toán thành công!")
                            delay(1000)
                            navController.navigate("history?tab=ticket") {
                                popUpTo("bus_result/${selectedProvince}") { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun BusResultContent(
    tickets: List<BusTicket>,
    selectedTicket: BusTicket?,
    onSelect: (BusTicket) -> Unit,
    onPayClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kết quả tìm kiếm",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color(0xFF1B5E20)
        )
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tickets) { ticket ->
                        BusTicketCard(
                            ticket = ticket,
                            selectedTicket = selectedTicket,
                            onSelect = onSelect
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onPayClick,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                    enabled = selectedTicket != null
                ) {
                    Text("Thanh toán", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
private fun BusTicketCard(
    ticket: BusTicket,
    selectedTicket: BusTicket?,
    onSelect: (BusTicket) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(ticket) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(ticket.route, fontWeight = FontWeight.Bold)
                Text(ticket.time)
                Text("Còn ${ticket.remainingSeats} chỗ", fontSize = 14.sp)
                Text("Loại xe: ${ticket.busType}", fontSize = 12.sp, color = Color.DarkGray)
            }
            RadioButton(
                selected = selectedTicket == ticket,
                onClick = { onSelect(ticket) }
            )
        }
    }
}
