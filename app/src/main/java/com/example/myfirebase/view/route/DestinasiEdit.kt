package com.example.myfirebase.view.route

import com.example.myfirebase.R

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = R.string.app_name
    const val argId = "id"
    val routeWithArgs = "$route/{$argId}"
}
