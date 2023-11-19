package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    private val _state = MutableLiveData<State>(State.Initial)
    val state: LiveData<State> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
                repository.getCurrencyList()
                        // .onStart идет в начале и можно ставить стайт загрузки сюда
                    .onStart {
                        val currentState = _state.value
                        if (currentState !is State.Content || currentState.currencyList.isEmpty()) {
                            _state.value = State.Loading }
                            // в .onEach мы можем реагировать на каждый .emit
                    }.onEach { _state.value = State.Content(currencyList = it) }
                        // в данном случае .collect и .onEach схожы
//                    .collect {
//                    _state.value = State.Content(currencyList = it) }
                    .collect()
            //можно вместо вызова viewModelScope.launch делать как ниже, в таком случае  .collect() не нужен
//                    .launchIn(viewModelScope)



        }
    }
}
