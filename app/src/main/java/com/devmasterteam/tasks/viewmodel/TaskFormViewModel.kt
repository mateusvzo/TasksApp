package com.devmasterteam.tasks.viewmodel

import android.app.Application
import android.text.BoringLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val taskRepository = TaskRepository(application.applicationContext)

    private val _priority = MutableLiveData<List<PriorityModel>>()
    val priority: LiveData<List<PriorityModel>> = _priority

    private val _task = MutableLiveData<ValidationModel>()
    val task: LiveData<ValidationModel> = _task

    fun save(task: TaskModel) {
        taskRepository.create(task, object : APIListener<Boolean> {
            override fun onSuccess(result: Boolean) {
                _task.value = ValidationModel()
            }
            override fun onFailure(message: String) {
                _task.value = ValidationModel(message)
            }

        })
    }

    fun loadPriority() {
        _priority.value = priorityRepository.getPriority()
    }
}