package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.PriorityRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)

    private val _priority = MutableLiveData<List<PriorityModel>>()
    val priority: LiveData<List<PriorityModel>> = _priority

    fun save(task: TaskModel) {

    }

    fun loadPriority() {
        _priority.value = priorityRepository.getPriority()
    }
}