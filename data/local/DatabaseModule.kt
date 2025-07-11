package com.example.travelapp.data.local

import android.content.Context
import androidx.room.Room
import com.example.travelapp.data.local.dao.BusTicketDao
import com.example.travelapp.data.local.dao.BusTicketHistoryDao
import com.example.travelapp.data.local.dao.DestinationDao
import com.example.travelapp.data.local.dao.HotelRoomDao
import com.example.travelapp.data.local.dao.HotelRoomHistoryDao
import com.example.travelapp.data.local.dao.ReviewDao
import com.example.travelapp.data.local.dao.UserDao
import com.example.travelapp.data.local.database.AppDatabase
import com.example.travelapp.data.local.db.HistoryDatabase
import com.example.travelapp.data.local.repository.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "travel_app_db"
        )
            .fallbackToDestructiveMigration() // 👉 THÊM DÒNG NÀY
            .build()

    }

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            HistoryDatabase::class.java
        ).build()
    }

    @Provides
    fun provideDestinationDao(db: AppDatabase): DestinationDao = db.destinationDao()

    @Provides
    fun provideBusTicketDao(db: AppDatabase): BusTicketDao = db.busTicketDao()

    @Provides
    fun provideHotelRoomDao(db: AppDatabase): HotelRoomDao = db.hotelRoomDao()

    @Provides
    fun provideReviewDao(db: AppDatabase): ReviewDao = db.reviewDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideBusTicketHistoryDao(db: AppDatabase): BusTicketHistoryDao = db.busTicketHistoryDao()

    @Provides
    fun provideHotelRoomHistoryDao(db: AppDatabase): HotelRoomHistoryDao = db.hotelRoomHistoryDao()

    @Provides
    fun provideUserRepo(userDao: UserDao): UserRepo = UserRepo(userDao)

}