package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryankeltonadams.fetchtakehometest.data.items.IItemsRepository
import com.bryankeltonadams.fetchtakehometest.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListScreenViewModel @Inject constructor(
    private val itemRepository: IItemsRepository,
) : IItemListScreenViewModel, ViewModel() {

    private val _itemListScreenUiState = MutableStateFlow(ItemListScreenUiState())
    override val itemListScreenUiState = _itemListScreenUiState.asStateFlow()


    override fun refresh() {
        viewModelScope.launch {
            _itemListScreenUiState.update { it.copy(isRefreshing = true) }
            fetchAndHandleItemsResult()
            // if the error comes back fast, it will leave the drag handle there
            delay(500)
            _itemListScreenUiState.update { it.copy(isRefreshing = false) }
        }
    }

    override suspend fun fetchAndHandleItemsResult() {
        val itemsResult = itemRepository.getNetworkItems()
        handleItemsResult(itemsResult)
    }


    override fun filteredAndSortedMap(items: List<Item>): Map<Int, List<Item>> {
        val filteredAndSortedItems = items
            .filter { !it.name.isNullOrBlank() } // Filter out items with blank or null names
            .groupBy { it.listId } // Group by listId
            .toSortedMap() // Ensure groups are sorted by listId
            .mapValues { entry ->
                entry.value.sortedBy { it.name } // Sort each group by name
            }
        return filteredAndSortedItems
    }

    override fun handleItemsResult(itemsResult: Result<List<Item>>) {
        if (itemsResult.isSuccess) {
            val items = itemsResult.getOrNull()
            if (items != null) {
                val groupedItems = filteredAndSortedMap(items)

                _itemListScreenUiState.update {
                    it.copy(
                        isLoading = false,
                        sectionedItems = groupedItems,
                        error = null
                    )
                }
            } else {
                _itemListScreenUiState.update {
                    it.copy(
                        isLoading = false,
                        error = itemsResult.exceptionOrNull()?.message
                    )
                }
            }
        } else {
            // errors specifically from ktor error
            _itemListScreenUiState.update {
                it.copy(
                    isLoading = false,
                    error = "Error fetching items. Ensure you have a stable internet connection and try pulling to refresh."
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            fetchAndHandleItemsResult()
        }
    }
}

interface IItemListScreenViewModel {
    val itemListScreenUiState: StateFlow<ItemListScreenUiState>

    fun refresh()

    suspend fun fetchAndHandleItemsResult()

    fun filteredAndSortedMap(items: List<Item>): Map<Int, List<Item>>

    fun handleItemsResult(itemsResult: Result<List<Item>>)

}

