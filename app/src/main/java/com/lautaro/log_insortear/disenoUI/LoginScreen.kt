package com.lautaro.log_insortear.disenoUI

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lautaro.log_insortear.disenoUI.LoginScreen
import com.lautaro.log_insortear.ui.theme.LoginSortearTheme

@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    isLoading:Boolean,
    onLoginClick:() -> Unit
) {
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val (focusEmail, focusPassword) = remember { FocusRequester.createRefs()}
    val keyboardController = LocalSoftwareKeyboardController.current

    var name = ""

    Scaffold() {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.50f),
                Alignment.TopEnd){

                Image(painter = painterResource(id = com.lautaro.log_insortear.R.drawable.trebol_pattern_edit),
                    contentDescription = "Panel head trebol",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillHeight)

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.90f)
                    .padding(horizontal = 20.dp, vertical = 90.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = com.lautaro.log_insortear.R.drawable.nuevo_logo_sortear_blanco),
                        contentDescription = "Logo App",
                        modifier = Modifier
                            .weight(1f)
                            .size(180.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.80f)
                        .padding(start = 15.dp),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    //Comprueba el nombre del usuario para saber que bienvenidad dar o a quien y lo muestra
                    comprobarName(name)
                }//termina el Box

            }

            //variable para determinar el maximo de caracteres del campo (Verlo en versiones futuras)
            val maxCaracter = 20

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 40.dp)) {//Posicionamiento del objeto

                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusEmail),

                    //cambia el boton del teclado para pasar al siguiente TextField
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),

                    //le da el focus al TextField de password
                    keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() }),

                    //determina que sea de una sola linea el textField (falta verificar la cantidad de caracteres que se admite)
                    singleLine = true,
                    label = { Text(text = "Email", color = Color(0xff757575)) }, //etiqueta por defecto
                    placeholder = { Text("¿Cuál es tu correo?", color = Color(0xff757575)) }, //etiqueta al seleccionar
                    leadingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Icono de email"
                            )
                        }
                    },
                    shape = RoundedCornerShape(15.dp), //Redondea los bordes
                    colors = TextFieldDefaults.outlinedTextFieldColors( //Determina el color del borde al seleccionar el TextField
                        focusedBorderColor = Color(0xff71bd43)
                    )
                )

                Spacer(modifier = (Modifier.height(8.dp))) // *** Espaciado ***

                OutlinedTextField( //TextField de Password
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusPassword),
                    shape = RoundedCornerShape(15.dp),
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Contraseña", color = Color(0xff757575)) },
                    placeholder = { Text("¿Cual es tu contraseña?", color = Color(0xff757575)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done), //determina el tipo de teclado y campia el icono de enter al de aceptar
                    keyboardActions = KeyboardActions (onDone = {keyboardController?.hide()}), //esconde el teclado al enviar el formulario
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(), //para cambiar la visibilidad de la contarseña
                    trailingIcon = { //tariling es para poner el icono al final del textField
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible}) {
                            Icon(imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Password Toggle" )
                        }
                    },
                    leadingIcon = {//leading es para poner el icono al comienzo del textField
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Icono de email"
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors( //Determina el color del borde al seleccionar el TextField
                        focusedBorderColor = Color(0xff71bd43)
                    )

                )

                Spacer(modifier = (Modifier.height(2.dp)))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    Arrangement.Center,

                    ) {
                    /*Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = true, onCheckedChange = {})
                        Text(text = "Recuerdame", fontSize = 12.sp)
                    }*/
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text ="¿Olvidaste tu contraseña?",
                            fontSize = 12.sp,
                            color = Color(0xff757575)
                        )
                    }
                }
                Spacer(modifier = (Modifier.height(15.dp)))

                Button(onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff71bd43),
                        contentColor = Color(0xffffffff)
                    )
                ) {
                    Text(text = "Iniciar Sesión")
                }

                Spacer(modifier = (Modifier.height(3.dp)))

                Button(onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffebb607),
                        contentColor = Color(0xffffffff)
                    )
                ) {
                    Text(text = "Registrarte")
                }

                Spacer(modifier = (Modifier.height(3.dp)))

                if(isLoading){

                    CircularProgressIndicator()

                }else{

                    Button(onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffffffff),
                        contentColor = Color(0xff737373)
                    )

                ) {
                    Text(text = "Ingresar con Google")
                }

                }



            }
        }
    }

}

@Composable
fun comprobarName(name: String?){

    val offset = Offset(1.0f, 2.0f) //determina las posiciones para shadow

    if (name != "" && name != null ){
        Text(
            buildAnnotatedString {//sirve para aplicar diferentes estilos a diferentes caracteres en una cadena de String

                withStyle(style = SpanStyle(color = Color.White,
                    fontSize = 25.sp,
                    shadow = Shadow(color = Color.Black,
                        offset = offset,
                        blurRadius = 3f))
                ){
                    append("Hola, ")
                }
                withStyle(style = SpanStyle(color = Color(0x0fffadc0a),
                    fontSize = 25.sp,
                    shadow = Shadow(color = Color.Black,
                        offset = offset,
                        blurRadius = 5f))){
                    append("$name!")
                }
            } //termina el "buildAnnotatedString"
        )//termina el Text
    }else{
        Text(
            buildAnnotatedString{
                withStyle(style = SpanStyle(color = Color.White,
                    fontSize = 25.sp,
                    shadow = Shadow(color = Color.Black,
                        offset = offset,
                        blurRadius = 3f))
                ){
                    append("Bienvenido!")
                }
            }

        )
    }
}

@Preview
@Composable
fun loginPreview() {
        LoginScreen(true){
            //NO-OP
        }
}