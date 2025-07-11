// data/local/database/AppDatabase.kt
package com.example.travelapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.travelapp.data.local.Converters
import com.example.travelapp.data.local.dao.BusTicketDao
import com.example.travelapp.data.local.dao.BusTicketHistoryDao
import com.example.travelapp.data.local.dao.DestinationDao
import com.example.travelapp.data.local.dao.HotelRoomDao
import com.example.travelapp.data.local.dao.HotelRoomHistoryDao
import com.example.travelapp.data.local.dao.ReviewDao
import com.example.travelapp.data.local.dao.UserDao
import com.example.travelapp.data.local.entity.BusTicket
import com.example.travelapp.data.local.entity.BusTicketHistory
import com.example.travelapp.data.local.entity.Destination
import com.example.travelapp.data.local.entity.HotelRoom
import com.example.travelapp.data.local.entity.HotelRoomHistory
import com.example.travelapp.data.local.entity.ReviewEntity
import com.example.travelapp.data.local.entity.UserEntity

@Database(
    entities = [
        BusTicket::class,
        HotelRoom::class,
        BusTicketHistory::class,
        HotelRoomHistory::class,
        Destination::class,
        ReviewEntity::class,
        UserEntity::class

    ],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun busTicketDao(): BusTicketDao
    abstract fun destinationDao(): DestinationDao
    abstract fun hotelRoomDao(): HotelRoomDao
    abstract fun busTicketHistoryDao(): BusTicketHistoryDao
    abstract fun hotelRoomHistoryDao(): HotelRoomHistoryDao
    abstract fun reviewDao(): ReviewDao
    abstract fun userDao(): UserDao
}



