package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.repository.local.TaskDatabase
import com.devmasterteam.tasks.service.repository.remote.PriorityService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context) : BaseRepository() {

    private val remote = RetrofitClient.createService(PriorityService::class.java)
    private val db = TaskDatabase.getDatabase(context).priorityDAO()

    fun getPriority(listener: APIListener<List<PriorityModel>>) {
        val call = remote.getPriority()
        call.enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                handleResponse(response, listener)
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                listener.onFailure("")
            }

        })
    }

    fun getPriority(): List<PriorityModel> {
        return db.list()
    }

    fun save(list: List<PriorityModel>) {
        db.clear()
        db.save(list)
    }
}