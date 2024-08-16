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
    private val _presentList = MutableLiveData<PresentList>()
    val presentList: LiveData<PresentList> = _presentList

    fun requestArea() {
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
            }
        }
    }

    fun requestPrefecture(area: Entity.Area) {
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                heartRailsRepository.requestPrefectures(area = area)
            }.onSuccess {
                val prefectureList = it.response.toEntityList(area = area)
                _presentList.postValue(
                    checkNotNull(_presentList.value).copy(prefectureList = prefectureList)
                )
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun previousList(): Boolean {
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