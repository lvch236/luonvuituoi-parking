package com.example.luonvuituoi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luonvuituoi.item.HotNewItem
import com.example.luonvuituoi.services.HotNewRestClient
import kotlinx.coroutines.launch

class HotNewVM : ViewModel() {
    private var _movieData : MutableLiveData<List<HotNewItem>> = MutableLiveData<List<HotNewItem>>()
    val hotNewData : LiveData<List<HotNewItem>>
        get() = _movieData

    private var _errEvent: MutableLiveData<String> = MutableLiveData<String>()
    val errEvent: LiveData<String>
        get() = _errEvent

    fun getNowPlaying() {
        viewModelScope.launch {
            try {
                val movieResp = HotNewRestClient.getInstance().api.listNowPlayMovies(
                    q = "tesla",
                    from = "2022-04-19",
                    sortBy = "publishedAt",
                    language = "vi",
                )
                _movieData.postValue(movieResp.articles!!)
            } catch (e: Exception) {
                _errEvent.value = e.message
            }
        }
    }
}