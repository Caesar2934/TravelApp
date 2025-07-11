package com.example.travelapp.data.local.repository

import com.example.travelapp.data.local.dao.UserDao
import com.example.travelapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val dao: UserDao
) {
    fun getUser(): Flow<UserEntity?> = dao.getUser()
    suspend fun insertUser(user: UserEntity) = dao.insertUser(user)
}