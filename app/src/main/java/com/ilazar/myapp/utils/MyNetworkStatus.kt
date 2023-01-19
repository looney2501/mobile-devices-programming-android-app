package com.ilazar.myapp.todo.util

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class MyNetworkStatusViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(false)
        private set

    init {
        collectNetworkStatus()
    }

    private fun collectNetworkStatus() {
        viewModelScope.launch {
            ConnectivityManagerNetworkMonitor(getApplication()).isOnline.collect {
                uiState = it;
            }
        }
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyNetworkStatusViewModel(application)
            }
        }
    }
}

