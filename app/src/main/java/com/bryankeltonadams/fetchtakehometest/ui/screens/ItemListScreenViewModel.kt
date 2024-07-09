package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryankeltonadams.fetchtakehometest.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemListScreenViewModel : IItemListScreenViewModel, ViewModel() {

    private val _itemListScreenUiState = MutableStateFlow(ItemListScreenUiState())
    override val itemListScreenUiState = _itemListScreenUiState.asStateFlow()
    override fun loadItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dummyItems = listOf(
                    Item(id = 755, listId = 2, name = ""),
                    Item(id = 203, listId = 2, name = ""),
                    Item(id = 684, listId = 1, name = "Item 684"),
                    Item(id = 276, listId = 1, name = "Item 276"),
                    Item(id = 736, listId = 3, name = null),
                    Item(id = 926, listId = 4, name = null),
                    Item(id = 808, listId = 4, name = "Item 808"),
                    Item(id = 599, listId = 1, name = null),
                    Item(id = 424, listId = 2, name = null),
                    Item(id = 444, listId = 1, name = ""),
                    Item(id = 809, listId = 3, name = null),
                    Item(id = 293, listId = 2, name = null),
                    Item(id = 510, listId = 2, name = null),
                    Item(id = 680, listId = 3, name = "Item 680"),
                    Item(id = 231, listId = 2, name = null),
                    Item(id = 534, listId = 4, name = "Item 534"),
                )
                val sortedItems = dummyItems.sortedBy { it.name }
                val groupedItems = sortedItems.groupBy { it.listId }.toSortedMap(
                    compareBy { it }
                )
                _itemListScreenUiState.value = ItemListScreenUiState(
                    sectionedItems = groupedItems,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    init {
        loadItems()
    }
}

interface IItemListScreenViewModel {
    val itemListScreenUiState: StateFlow<ItemListScreenUiState>
    fun loadItems()
}

