package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    val state: LiveData<State> = repository.getCurrencyList()
        // если .filter даст false то код дальше выполнятся не будет
        .filter { it.isNotEmpty() }
        // можем мапать данные и приводить колекцию к стайту,
        //.map кроме мапинга он сразу делает emit
        .map { State.Content(currencyList = it) as State }
        // если после мапинга был не стайт Content то в .onStart мы его эмитим
        .onStart { emit(State.Loading) }
        //приводим Flow к типу LiveData
        .asLiveData()
}
