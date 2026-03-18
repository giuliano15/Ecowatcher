package com.giuldev.ecowatcher.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giuldev.ecowatcher.domain.model.Asset
import com.giuldev.ecowatcher.domain.model.Resource
import com.giuldev.ecowatcher.domain.usecase.GetMarketAssetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Estados da Tela de Ativos (Assets).
 * Define explicitamente o que a Interface do Usuário (UI) deve renderizar.
 */
sealed class AssetState {
    object Loading : AssetState()
    data class Success(val assets: List<Asset>) : AssetState()
    data class Error(val message: String) : AssetState()
}

/**
 * ViewModel responsável por gerenciar os estados da Tela Principal (Market).
 * Utiliza StateFlow em conjunto com a arquitetura reativa para emitir novos estados.
 */
@HiltViewModel
class AssetViewModel @Inject constructor(
    private val getMarketAssetsUseCase: GetMarketAssetsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AssetState>(AssetState.Loading)
    val state: StateFlow<AssetState> = _state.asStateFlow()

    init {
        loadAssets()
    }

    /**
     * Dispara o fluxo limpo (Clean Flow) do caso de uso de ativos.
     */
    fun loadAssets() {
        getMarketAssetsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Se estiver vazio, a View deve montar Empty State
                    _state.value = AssetState.Success(result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = AssetState.Error(result.message ?: "Erro desconhecido ao carregar ativos.")
                }
                is Resource.Loading -> {
                    _state.value = AssetState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}
