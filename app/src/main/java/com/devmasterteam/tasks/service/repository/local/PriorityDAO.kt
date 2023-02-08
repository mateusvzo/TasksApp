package com.devmasterteam.tasks.service.repository.local

import androidx.room.Insert
import androidx.room.Query
import com.devmasterteam.tasks.service.model.PriorityModel


interface PriorityDAO {

    @Insert
    fun save(list: List<PriorityModel>)

    @Query("SELECT * FROM Priority")
    fun list(): List<PriorityModel>
}