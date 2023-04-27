package edu.qc.seclass.floorstore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
@OptIn(FlowPreview::class)
class MainViewModel : ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

//    private val _searchTextState: MutableState<String> =
//        mutableStateOf(value = "")
//    val searchTextState: State<String> = _searchTextState



//     Search
    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //change allFloors to database
    private val _floors = MutableStateFlow(allFloors)
    val floors = searchTextState
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_floors) { text, floors ->
            if(text.isBlank()) {
                floors
            } else {
                delay(2000L)
                floors.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _floors.value
        )

//    fun onSearchTextChange(text: String) {
//        _searchTextState.value = text
//    }
    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

}

data class Floor(
    //id is floor name, category is floor category (stone, etc)
    val id: String,
    val category: String
) {

    //any of these ways to type in search are allowed
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$id",
            "$category",
            "$id $category",
            "$category $id",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

//testing with premade floors
private val allFloors = listOf(
    Floor(
        id = "floor1",
        category = "category1"
    ),
    Floor(
        id = "floor2",
        category = "category2"
    ),
    Floor(
        id = "floor3",
        category = "category3"
    ),
    Floor(
        id = "floor4",
        category = "category4"
    ),
)