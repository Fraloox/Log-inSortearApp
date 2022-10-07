package com.lautaro.log_insortear.clases

import org.json.JSONObject

class Usuario(objetoJSON: JSONObject) { //Encriptación MD5 y otro mas

    var email = objetoJSON.getString("email")
    var contraseña = objetoJSON.getString("contraseña")

}