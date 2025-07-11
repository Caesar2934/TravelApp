package com.example.travelapp.data.local.repository

import com.example.travelapp.data.local.dao.ReviewDao
import com.example.travelapp.data.local.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepo @Inject constructor(
    private val dao: ReviewDao
) {
    fun getAllReviews(): Flow<List<ReviewEntity>> = dao.getAllReviews()
    suspend fun insertReview(review: ReviewEntity) = dao.insertReview(review)
}