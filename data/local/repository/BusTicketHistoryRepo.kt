package com.example.travelapp.data.local.repository


import com.example.travelapp.data.local.dao.BusTicketHistoryDao
import com.example.travelapp.data.local.entity.BusTicketHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusTicketHistoryRepo @Inject constructor(
    private val dao: BusTicketHistoryDao
) {
    suspend fun insert(ticket: BusTicketHistory) = dao.insert(ticket)

    fun getAll(): Flow<List<BusTicketHistory>> = dao.getAll()

}


