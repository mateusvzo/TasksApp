package com.devmasterteam.tasks.viewmodel

import android.app.Application
import android.app.Person
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonRepository(application.applicationContext)

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _failLogin = MutableLiveData<String>()
    val failLogin: LiveData<String> = _failLogin
    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        repository.login(email, password, object : APIListener<PersonModel>{
            override fun onSuccess(result: PersonModel) {
                if(result != null) {
                    _success.value = true
                }
            }

            override fun onFailure(message: String) {
                _failLogin.value = message
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}