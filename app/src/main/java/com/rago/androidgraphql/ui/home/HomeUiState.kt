package com.rago.androidgraphql.ui.home

import com.rago.LaunchListQuery

sealed class HomeUiState {
    object NotStatus : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val data: List<LaunchListQuery.Launch?>) : HomeUiState()
    data class Error(val msg: String, val throwable: Throwable) : HomeUiState()
    object NoData : HomeUiState()
}