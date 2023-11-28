package com.sumin.coroutineflow.team_score

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class TeamScoreViewModel : ViewModel() {
    //работает так же как и liveData, хранит в себе последнее значение но только одно
    // по умолчанию replay = 1, для того чтобы положить значение в стайт не обязательно вызывать
    //корутины, можно как в liveData ложить и получать при помощи value
    private val _state = MutableStateFlow<TeamScoreState>(TeamScoreState.Game(0, 0))
    val state = _state.asStateFlow()
    //у SharedFlow по дефолту replay = 0 и он не хранит значений, так же нельзя получить из
    // него значения используя value
    // сам replay возврашаят количество обьектов с потока
    private val _state1 = MutableSharedFlow<TeamScoreState>(replay = 1)
    val state1 = _state1.asSharedFlow()

    fun increaseScore(team: Team) {
        val currentState = _state.value
        if (currentState is TeamScoreState.Game) {
            if (team == Team.TEAM_1) {
                val oldValue = currentState.score1
                val newValue = oldValue + 1
                _state.value = currentState.copy(score1 = newValue)
                if (newValue >= WINNER_SCORE) {
                    _state.value = TeamScoreState.Winner(
                        winnerTeam = Team.TEAM_1,
                        newValue,
                        currentState.score2
                    )
                }
            } else {
                val oldValue = currentState.score2
                val newValue = oldValue + 1
                _state.value = currentState.copy(score2 = newValue)
                if (newValue >= WINNER_SCORE) {
                    _state.value = TeamScoreState.Winner(
                        winnerTeam = Team.TEAM_2,
                        currentState.score1,
                        newValue
                    )
                }
            }
        }
    }

    companion object {
        private const val WINNER_SCORE = 7
    }
}
