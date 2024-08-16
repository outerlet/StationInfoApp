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
                val newList = res.response.names.map { Entity.Area(it) }

                _presentList.postValue(
                    _presentList.value?.copy(areaList = newList) ?: PresentList(areaList = newList)
                )
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}