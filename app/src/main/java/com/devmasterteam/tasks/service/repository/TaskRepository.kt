package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.devmasterteam.tasks.service.repository.remote.TaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context) : BaseRepository() {

    private val remote = RetrofitClient.createService(TaskService::class.java)

    fun list(listener: APIListener<List<TaskModel>>) {
        val call = remote.list()
        list(call, listener)
    }

    fun listNextDays(listener: APIListener<List<TaskModel>>) {
        val call = remote.listNextDays()
        list(call, listener)
    }

    fun listOverdue(listener: APIListener<List<TaskModel>>) {
        val call = remote.listOverdue()
        list(call, listener)
    }

    private fun list(call: Call<List<TaskModel>>, listener: APIListener<List<TaskModel>>) {
        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                handleResponse(response, listener)
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                handleResponse(response, listener)
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde")
            }

        })
    }

    fun delete(id: Int, listener: APIListener<Boolean>) {
        val call = remote.delete(id)
        call.enqueue(object :Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                handleResponse(response, listener)
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde")
            }
        })
    }
}