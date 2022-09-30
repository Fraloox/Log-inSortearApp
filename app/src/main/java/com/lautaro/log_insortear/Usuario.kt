package com.lautaro.log_insortear

import org.json.JSONObject

class Usuario(objetoJSON: JSONObject) {

    var email = objetoJSON.getString("email")
    var contraseña = objetoJSON.getString("contraseña")

}