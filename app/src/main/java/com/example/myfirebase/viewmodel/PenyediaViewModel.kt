package com.example.myfirebase.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myfirebase.SiswaApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositorySiswa)
        }
        initializer {
            EntryViewModel(aplikasiSiswa().container.repositorySiswa)
        }
        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositorySiswa
            )
        }
        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositorySiswa
            )
        }
    }
}

fun CreationExtras.aplikasiSiswa(): SiswaApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SiswaApp)
