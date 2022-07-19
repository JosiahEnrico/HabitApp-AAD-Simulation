package com.aadexercise.habitapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.aadexercise.habitapp.R
import com.aadexercise.habitapp.data.Habit
import com.aadexercise.habitapp.data.HabitRepository
import com.aadexercise.habitapp.utils.Event
import com.aadexercise.habitapp.utils.HabitSortType

class HabitListViewModel(private val habitRepository: HabitRepository) : ViewModel() {

    private val _filter = MutableLiveData<HabitSortType>()

    val habits: LiveData<PagedList<Habit>> = _filter.switchMap {
        habitRepository.getHabits(it)
    }

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _undo = MutableLiveData<Event<Habit>>()
    val undo: LiveData<Event<Habit>> = _undo

    init {
        _filter.value = HabitSortType.START_TIME
    }

    fun filter(filterType: HabitSortType) {
        _filter.value = filterType
    }

    fun deleteHabit(habit: Habit) {
        habitRepository.deleteHabit(habit)
        _snackbarText.value = Event(R.string.habit_deleted)
        _undo.value = Event(habit)
    }

    fun insert(habit: Habit) {
        habitRepository.insertHabit(habit)
    }
}