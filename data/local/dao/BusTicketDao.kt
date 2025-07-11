package com.example.travelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelapp.data.local.entity.BusTicket
import kotlinx.coroutines.flow.Flow

@Dao
interface BusTicketDao {
    @Query("SELECT * FROM bus_tickets")
    fun getAllTickets(): Flow<List<BusTicket>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: BusTicket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tickets: List<BusTicket>)

    @Query("DELETE FROM bus_tickets")
    suspend fun clearTickets()

    @Query("SELECT * FROM bus_tickets WHERE LOWER(route) LIKE '%' || LOWER(:province) || '%'")
    fun getTicketsByProvince(province: String): Flow<List<BusTicket>>

}
