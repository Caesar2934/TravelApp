package com.example.travelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.travelapp.data.local.entity.BusTicketHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface BusTicketHistoryDao {
    @Query("SELECT * FROM bus_ticket_history ORDER BY bookingTime DESC")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<BusTicketHistory>>

    @Insert
    suspend fun insert(ticket: BusTicketHistory)
}

