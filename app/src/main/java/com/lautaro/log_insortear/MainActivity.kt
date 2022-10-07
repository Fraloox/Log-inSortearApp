package com.lautaro.log_insortear

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.lautaro.log_insortear.disenoUI.LoginScreen
import com.lautaro.log_insortear.disenoUI.MainViewModel
import com.lautaro.log_insortear.ui.theme.LoginSortearTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //Bloquea la rotación de la pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MainViewModel by viewModels()

            //Hace que recuerde el estado y lo guarda en la var isLoading para que asi no empiece cargando
            val isLoading by viewModel.isLoading().observeAsState(false)
            val hasError by viewModel.hasErrors().observeAsState(false)


            LoginSortearTheme {

                if (hasError) {
                    LoginErrorPopup {
                        viewModel.clearErrors()
                    }
                }

                LoginScreen(isLoading,
                    /*onLoginGoogleClick = { viewModel.loginWithGoogle( this@MainActivity)},*/
                    onRegistrarseClick = {viewModel.ventanaRegistrarse()},
                    onLoginSortearClick = {viewModel.LoginWithSortear(this@MainActivity)}
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //codigo de google

        super.onActivityResult(requestCode, resultCode, data)

        val viewModel: MainViewModel by viewModels()

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            viewModel.finishLogin(task)
        }
    }
}




@Composable
private fun LoginErrorPopup(onDismiss: () -> Unit){

    AlertDialog(
        onDismissRequest = onDismiss,
        text ={
            Text(

                text = "No fue posible completar el ingreso",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CERRAR")
            }
        }
    )





}

