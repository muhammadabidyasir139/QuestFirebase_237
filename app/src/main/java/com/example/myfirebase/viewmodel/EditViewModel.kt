package com.example.myfirebase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.DetailSiswa
import com.example.myfirebase.modeldata.UIStateSiswa
import com.example.myfirebase.modeldata.toDataSiswa
import com.example.myfirebase.modeldata.toUiStateSiswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiDetail
import com.example.myfirebase.view.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val idSiswa: Long =
        checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg]).toString().toLong()

    init {
        viewModelScope.launch {
            val result = repositorySiswa.getSatuSiswa(idSiswa)
            uiStateSiswa = result?.toUiStateSiswa(true) ?: UIStateSiswa()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    // Fungsi untuk menyimpan perubahan data siswa
    suspend fun editSatuSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {

            try {
                repositorySiswa.editSatuSiswa(
                    idSiswa,
                    uiStateSiswa.detailSiswa.toDataSiswa()
                )
                println("Update Sukses: $idSiswa")
            } catch (e: Exception) {
                println("Update Error: ${e.message}")
            }
        }
    }
}
