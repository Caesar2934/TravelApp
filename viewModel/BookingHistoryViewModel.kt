package com.example.travelapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.local.entity.BusTicket
import com.example.travelapp.data.local.repository.BusTicketHistoryRepo
import com.example.travelapp.data.local.repository.HotelRoomHistoryRepo
import com.example.travelapp.data.local.entity.BusTicketHistory
import com.example.travelapp.data.local.entity.HotelRoom
import com.example.travelapp.data.local.entity.HotelRoomHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingHistoryViewModel @Inject constructor(
    private val ticketRepo: BusTicketHistoryRepo,
    private val roomRepo: HotelRoomHistoryRepo
) : ViewModel() {
    private val _busHistory = MutableStateFlow<List<BusTicketHistory>>(emptyList())
    val busHistory: StateFlow<List<BusTicketHistory>> = _busHistory

    private val _roomHistory = MutableStateFlow<List<HotelRoomHistory>>(emptyList())
    val roomHistory: StateFlow<List<HotelRoomHistory>> = _roomHistory

    private fun observeRoomHistory() {
        viewModelScope.launch {
            roomRepo.getAll().collectLatest { rooms ->
                _roomHistory.value = rooms
            }
        }
    }

    init {
        observeAll()
    }

    private fun observeAll() {
        viewModelScope.launch {
            ticketRepo.getAll().collectLatest { tickets ->
                _busHistory.value = tickets
            }
        }


        viewModelScope.launch {
            roomRepo.getAll().collectLatest { rooms ->
                _roomHistory.value = rooms
            }
        }
    }

    fun insertTicket(ticket: BusTicketHistory) {
        viewModelScope.launch {
            ticketRepo.insert(ticket)
        }
    }

    fun addRoomToHistory(room: HotelRoom) {
        viewModelScope.launch {
            val history = HotelRoomHistory(
                name = room.name,
                bedType = room.bedType,
                size = room.size,
                price = room.price,
                features = room.features
            )
            roomRepo.insert(history)
        }
    }

    fun addTicketToHistory(ticket: BusTicketHistory) {
        viewModelScope.launch {
            val history = BusTicketHistory(
                route = ticket.route,
                time = ticket.time,
                price = ticket.price,
                remainingSeats = ticket.remainingSeats,
                busType = ticket.busType,
                bookingTime = ticket.bookingTime
            )
            ticketRepo.insert(history)
        }
    }

}
