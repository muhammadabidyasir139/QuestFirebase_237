package com.example.myfirebase.view.route

import com.example.myfirebase.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = R.string.app_name
    const val argId = "id"
    val routeWithArgs = "$route/{$argId}"
}
