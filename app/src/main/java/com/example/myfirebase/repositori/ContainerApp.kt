package com.example.myfirebase.repositori

interface AppContainer {
    val repositorySiswa: RepositorySiswa
}

class ContainerApp : AppContainer {
    override val repositorySiswa: RepositorySiswa by lazy {
        FirebaseRepositorySiswa()
    }
}
