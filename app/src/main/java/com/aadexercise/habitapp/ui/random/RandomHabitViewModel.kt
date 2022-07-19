package com.aadexercise.habitapp.ui.random

import androidx.lifecycle.*
import com.aadexercise.habitapp.data.Habit
import com.aadexercise.habitapp.data.HabitRepository

class RandomHabitViewModel (habitRepository: HabitRepository) : ViewModel() {
    val priorityLevelHigh: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("High")
    val priorityLevelMedium: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("Medium")
    val priorityLevelLow: LiveData<Habit> = habitRepository.getRandomHabitByPriorityLevel("Low")
}