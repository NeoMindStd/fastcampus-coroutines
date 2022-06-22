package kr.co.fastcampus.co.kr.coroutines.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.co.fastcampus.co.kr.coroutines.data.NaverImageSearchRepository
import kr.co.fastcampus.co.kr.coroutines.model.Item

class ImageSearchViewModel : ViewModel() {
    private val repository = NaverImageSearchRepository()
    // shared 는 핫스트림
    private val queryFlow = MutableSharedFlow<String>()
    private val favorites = mutableSetOf<Item>()
    private val _favoritesFlow = MutableSharedFlow<List<Item>>(replay = 1)
    val favoritesFlow: SharedFlow<List<Item>>
        get() = _favoritesFlow.asSharedFlow()

    val pagingDataFlow = queryFlow
            // 사용자가 입력한 마지막값만 의미가 있으므로
        .flatMapLatest {
            searchImages(it)
        }
        .cachedIn(viewModelScope)

    private fun searchImages(query: String): Flow<PagingData<Item>> =
        repository.getImageSearch(query)

    fun handleQuery(query: String) {
        // viewModel 내에서는 viewModelScope 로만 이용
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    fun toggle(item: Item) {
        if (favorites.contains(item)) {
            favorites.remove(item)
        } else {
            favorites.add(item)
        }
        viewModelScope.launch {
            _favoritesFlow.emit(favorites.toList())
        }
    }
}
