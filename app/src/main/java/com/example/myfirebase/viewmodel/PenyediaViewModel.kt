package com.example.myfirebase.viewmodel

import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel() }
        initializer { EntryViewModel() }
        initializer { DetailViewModel() }
        initializer { EditViewModel() }
    }
}
