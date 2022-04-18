package com.example.kerklyv5.modelo.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Oficio {

    @SerializedName("nombreO")
    @Expose
    var nombreO: String = ""
}