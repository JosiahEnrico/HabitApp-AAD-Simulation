package com.aadexercise.habitapp.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadexercise.habitapp.data.Habit
import com.aadexercise.habitapp.data.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHabitViewModel(private val habitRepository: HabitRepository) : ViewModel() {
    fun saveHabit(habit: Habit) {
        viewModelScope.launch (Dispatchers.IO){
            habitRepository.insertHabit(habit)
        }
    }
}