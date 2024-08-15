package jp.craftman1take.stationinfoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.repository.HeartRailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val heartRailsRepository: HeartRailsRepository,
) : ViewModel() {
    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _areaList = MutableLiveData<List<Entity.Area>>()
    val areaList: LiveData<List<Entity.Area>> = _areaList

    fun requestArea() {
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                heartRailsRepository.requestAreas()
            }.onSuccess { res ->
                _title.postValue("地域一覧")
                _areaList.postValue(res.response.names.map { Entity.Area(it) })
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}