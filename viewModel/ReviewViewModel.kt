package com.example.travelapp.viewModel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.local.repository.ReviewRepo
import com.example.travelapp.data.local.entity.ReviewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepo
) : ViewModel() {

    val reviews = repository.getAllReviews().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addReview(author: String, title: String, content: String) {
        val now = java.util.Date()
        val formatter = java.text.SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH)
        val date = formatter.format(now)

        val review = ReviewEntity(author = author, title = title, content = content, date = date)

        viewModelScope.launch {
            repository.insertReview(review)
        }
    }
}

