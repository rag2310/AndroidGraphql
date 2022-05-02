package com.rago.androidgraphql.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.rago.LaunchListQuery
import com.rago.androidgraphql.repositories.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _launchList = MutableStateFlow<HomeUiState>(HomeUiState.NotStatus)
    val launchList: StateFlow<HomeUiState>
        get() = _launchList

    fun getLaunchesList() {
        _launchList.value = HomeUiState.Loading
        viewModelScope.launch {
            homeRepository.getLaunchList().toFlow().catch { e ->
                _launchList.value = HomeUiState.Error(msg = e.localizedMessage!!, throwable = e)
            }.collect {
                Log.i("HOMEVIEWMODEL","HOLA")
                if (it.data != null) {
                    if (it.data!!.launches.launches.isNotEmpty())
                        _launchList.value = HomeUiState.Success(data = it.data!!.launches.launches)
                    else
                        _launchList.value = HomeUiState.NoData
                } else {
                    _launchList.value = HomeUiState.NoData
                }
            }
        }
    }
}