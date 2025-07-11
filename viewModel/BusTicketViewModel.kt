package com.example.travelapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.local.repository.BusTicketRepository
import com.example.travelapp.data.local.entity.BusTicket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusTicketViewModel @Inject constructor(
    private val repository: BusTicketRepository
) : ViewModel() {


    private val _tickets = MutableStateFlow<List<BusTicket>>(emptyList())
    val tickets: StateFlow<List<BusTicket>> = _tickets

    init {
        viewModelScope.launch {
            repository.tickets.collect {
                if (it.isEmpty()) {
                    repository.insertAll(generateTickets())
                }
                _tickets.value = it
            }
        }
    }

    private fun generateTickets(): List<BusTicket> {
        val provinces = listOf(
            "Hà Nội", "TP.HCM", "Hội An", "Huế", "Mộc Châu",
            "Sapa", "Nha Trang", "Phú Quốc", "Đà Nẵng", "Vịnh Hạ Long",
            "Cần Thơ", "Tây Ninh", "Ninh Thuận", "Ninh Bình", "Bình Thuận"
        )

        val timeSlots = listOf("06:00", "08:00", "10:00", "13:00", "15:00", "17:00")

        return provinces.flatMap { province ->
            timeSlots.mapIndexed { i, time ->
                BusTicket(
                    route = province,
                    time = time,
                    price = (150 + i * 20) * 1000,
                    remainingSeats = 40 - i * 2,
                    busType = "Giường nằm"
                )
            }
        }
    }

    fun saveSearchResults(results: List<BusTicket>) {
        viewModelScope.launch {
            _searchResults.value = results
        }
    }

    fun resetTickets() {
        viewModelScope.launch {
            repository.clearAndInsert(generateTickets())
        }
    }


    fun getTicketsByProvince(province: String, onResult: (List<BusTicket>) -> Unit) {
        viewModelScope.launch {
            repository.getTicketsByProvince(province.trim())
                .collect { results ->
                    onResult(results)
                }
        }
    }

    private val _searchResults = MutableStateFlow<List<BusTicket>>(emptyList())
    val searchResults: StateFlow<List<BusTicket>> = _searchResults


}
