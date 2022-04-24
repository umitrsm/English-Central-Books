package com.egb.englishcentralbooks.network.datasource

interface Mapper<Storage, Remote> {
    fun Storage.toRemote(): Remote
    fun Remote.toStorage(type: String = ""): Storage
    fun List<Storage>.toRemote(): List<Remote> = this.map { it.toRemote() }
    fun List<Remote>.toStorage(type: String = ""): List<Storage> = this.map { it.toStorage(type) }
}