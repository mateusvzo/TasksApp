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

class PriorityRepository(context: Context) : BaseRepository(context) {

    private val remote = RetrofitClient.createService(PriorityService::class.java)
    private val db = TaskDatabase.getDatabase(context).priorityDAO()

    companion object {
        private val cache = mutableMapOf<Int, String>()
        fun getDescription(id: Int): String {
            return cache[id] ?: ""
        }

        fun setDescription(id: Int, str: String) {
            cache[id] = str
        }
    }

    fun getDescription(id: Int): String {
        val cached = PriorityRepository.getDescription(id)
        return if (cached == "") {
            val description = db.getDescription(id)
            setDescription(id, description)
            description
        } else {
            cached
        }
    }

    fun getPriority(listener: APIListener<List<PriorityModel>>) {
        executeCall(remote.getPriority(), listener)
    }

    fun getPriority(): List<PriorityModel> {
        return db.list()
    }

    fun save(list: List<PriorityModel>) {
        db.clear()
        db.save(list)
    }
}