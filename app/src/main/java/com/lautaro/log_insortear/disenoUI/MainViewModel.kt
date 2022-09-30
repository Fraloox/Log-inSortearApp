package com.lautaro.log_insortear.disenoUI

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lautaro.log_insortear.R


class MainViewModel: ViewModel() { //Esta clase es para manejar los estados

    /*Se cambia la variable para que solamente se pueda leer, asi nadie cambia el valor
    desde afuera del loading*/
    private val isLoading = MutableLiveData(false)
    private val hasErrors = MutableLiveData(false)

    //esta "observando la variable para saber cuando cambia"
    fun isLoading(): LiveData<Boolean> = isLoading

    fun hasErrors(): LiveData<Boolean> = hasErrors

    fun loginWithGoogle(activity: Activity){

        //avisa que el valor cambia
        isLoading.postValue(true)


        //Codigo de google https://developers.google.com/identity/sign-in/android/sign-in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(activity, gso);

        val signInIntent: Intent = client.signInIntent //delega la tarea a la app correspondiente
        activity.startActivityForResult(signInIntent, 1)

    }

    fun clearErrors() {
        hasErrors.postValue(false)
    }

    fun finishLogin(accountTask: Task<GoogleSignInAccount>) {

        try {
            val account: GoogleSignInAccount = accountTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            account?.idToken?.let { token ->

                //instancia para trabajar con firebase
                val auth = FirebaseAuth.getInstance()

                //El "GoogleAuthProvider" se puede poner dependiendo el servicio que queremos logear
                val credential = GoogleAuthProvider.getCredential(token, null)

                auth.signInWithCredential(credential)
                    .addOnCompleteListener{ task ->

                        var user = auth.currentUser

                        if (task.isSuccessful){
                            Log.d("ACA", "Ingreso ${user?.displayName}")
                        }else{
                            Log.d("ACA", "Failed ${task.exception?.message}")
                            hasErrors.postValue(true)
                        }
                        isLoading.postValue(false)


                }

            }

        } catch (e: ApiException) {

            Log.d("ACA","Failed ${e.message}")

            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            hasErrors.postValue(true)
            isLoading.postValue(false)
        }

    }

}