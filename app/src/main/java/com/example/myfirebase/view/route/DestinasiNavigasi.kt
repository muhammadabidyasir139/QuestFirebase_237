package com.example.myfirebase.view.route

import com.example.myfirebase.R

interface DestinasiNavigasi {
    val route: String
    val titleRes: Int
}

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.entry_siswa
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.app_name
}
