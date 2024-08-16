package jp.craftman1take.stationinfoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.craftman1take.stationinfoapp.data.Entity
import jp.craftman1take.stationinfoapp.data.PresentList
import jp.craftman1take.stationinfoapp.repository.HeartRailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val heartRailsRepository: HeartRailsRepository,
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _presentList = MutableLiveData<PresentList>()
    val presentList: LiveData<PresentList> = _presentList

    fun requestAreas() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                heartRailsRepository.requestAreas()
            }.onSuccess { res ->
                val areaList = res.response.toEntityList()
                _presentList.postValue(
                    _presentList.value?.copy(areaList = areaList) ?: PresentList(areaList = areaList)
                )
            }.onFailure {
                Timber.e(it)
            }.also {
                _isLoading.postValue(false)
            }
        }
    }

    fun requestPrefectures(area: Entity.Area) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                heartRailsRepository.requestPrefectures(area = area)
            }.onSuccess {
                _presentList.postValue(
                    checkNotNull(_presentList.value).copy(
                        prefectureList = it.response.toEntityList(area = area),
                    )
                )
            }.onFailure {
                Timber.e(it)
            }.also {
                _isLoading.postValue(false)
            }
        }
    }

    fun requestLines(prefecture: Entity.Prefecture) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                heartRailsRepository.requestLines(prefecture = prefecture)
            }.onSuccess {
                _presentList.postValue(
                    checkNotNull(_presentList.value).copy(
                        lineList = it.response.toEntityList(prefecture = prefecture),
                    )
                )
            }.onFailure {
                Timber.e(it)
            }.also {
                _isLoading.postValue(false)
            }
        }
    }

    fun requestStations(line: Entity.Line) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                heartRailsRepository.requestStations(line = line)
            }.onSuccess {
                _presentList.postValue(
                    checkNotNull(_presentList.value).copy(
                        stationList = it.response.toEntityList(line),
                    )
                )
            }.onFailure {
                Timber.e(it)
            }.also {
                _isLoading.postValue(false)
            }
        }
    }

    fun previous(): Boolean {
        val nextList = _presentList.value?.let { list ->
            when {
                list.stationList != null -> list.copy(stationList = null)
                list.lineList != null -> list.copy(lineList = null)
                list.prefectureList != null -> list.copy(prefectureList = null)
                else -> null
            }
        } ?: return false

        _presentList.value = nextList

        return true
    }
}