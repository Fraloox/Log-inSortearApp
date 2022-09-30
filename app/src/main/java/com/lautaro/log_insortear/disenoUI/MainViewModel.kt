package com.lautaro.log_insortear.disenoUI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.random

class MainViewModel: ViewModel() { //Esta clase es para manejar los estados

    /*Se cambia la variable para que solamente se pueda leer, asi nadie cambia el valor
    desde afuera del loading*/
    private val isLoading = MutableLiveData(false)
    private val hasErrors = MutableLiveData(false)

    //esta "observando la variable para saber cuando cambia"
    fun isLoading(): LiveData<Boolean> = isLoading

    fun hasErrors(): LiveData<Boolean> = hasErrors

    fun loginWithGoogle(){

        //avisa que el valor cambia
        isLoading.postValue(true)

        viewModelScope.launch {
            delay(5000) //delay de 5 segundos

            isLoading.postValue(false)

            if (random() < 0.4){
                hasErrors.postValue(true)
            }
        }

    }

    fun clearErrors() {
        hasErrors.postValue(false)
    }

}