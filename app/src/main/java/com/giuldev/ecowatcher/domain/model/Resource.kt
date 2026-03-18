package com.giuldev.ecowatcher.domain.model

/**
 * Classe genérica que encapsula um valor junto com seu estado de carregamento.
 * Utilizada para comunicar estados (Success, Error, Loading) entre as camadas do app.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
