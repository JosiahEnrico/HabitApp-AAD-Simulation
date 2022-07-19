package com.aadexercise.habitapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aadexercise.habitapp.data.HabitRepository
import com.aadexercise.habitapp.ui.add.AddHabitViewModel
import com.aadexercise.habitapp.ui.detail.DetailHabitViewModel
import com.aadexercise.habitapp.ui.random.RandomHabitViewModel
import com.aadexercise.habitapp.ui.list.HabitListViewModel

class ViewModelFactory private constructor(private val habitRepository: HabitRepository) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    HabitRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HabitListViewModel::class.java) -> {
                HabitListViewModel(habitRepository) as T
            }
            modelClass.isAssignableFrom(AddHabitViewModel::class.java) -> {
                AddHabitViewModel(habitRepository) as T
            }
            modelClass.isAssignableFrom(DetailHabitViewModel::class.java) -> {
                DetailHabitViewModel(habitRepository) as T
            }
            modelClass.isAssignableFrom(RandomHabitViewModel::class.java) -> {
                RandomHabitViewModel(habitRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}