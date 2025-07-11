package com.example.travelapp.data.local.repository


import com.example.travelapp.data.local.entity.BusTicket
import com.example.travelapp.data.local.dao.BusTicketDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusTicketRepository @Inject constructor (
    private val dao: BusTicketDao
) {
    val tickets: Flow<List<BusTicket>> = dao.getAllTickets()

    suspend fun insertAll(tickets: List<BusTicket>) {
        dao.clearTickets()
        dao.insertAll(tickets)
    }

    suspend fun insert(ticket: BusTicket) {
        dao.insertTicket(ticket)
    }

    fun getTicketsByProvince(province: String): Flow<List<BusTicket>> {
        return dao.getTicketsByProvince(province.trim())
    }

    suspend fun clearAndInsert(tickets: List<BusTicket>) {
        dao.clearTickets()
        dao.insertAll(tickets)
    }


}


