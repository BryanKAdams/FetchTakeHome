package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryankeltonadams.fetchtakehometest.data.items.IItemsRepository
import com.bryankeltonadams.fetchtakehometest.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.SortedMap
import javax.inject.Inject

@HiltViewModel
class ItemListScreenViewModel @Inject constructor(
    private val itemRepository: IItemsRepository,
) : IItemListScreenViewModel, ViewModel() {

    private val _itemListScreenUiState = MutableStateFlow(ItemListScreenUiState())
    override val itemListScreenUiState = _itemListScreenUiState.asStateFlow()

    override fun fetchAndHandleItemsResult() {
        viewModelScope.launch {
            // ktor functions are non blocking so we should not need
            // to use Dispatchers.IO
            val itemsResult = itemRepository.getNetworkItems()
            handleItemsResult(itemsResult)
        }
    }

    private fun filterItems(items: List<Item>): List<Item> {
        return items.filter { !it.name.isNullOrBlank() }
    }

    override fun createSortedMap(items: List<Item>): SortedMap<Int, List<Item>> {
        val sortedItems = items.sortedBy { it.name }
        val groupedItems = sortedItems.groupBy { it.listId }.toSortedMap(
            compareBy { it }
        )
        return groupedItems
    }

    override suspend fun handleItemsResult(itemsResult: Result<List<Item>>?) {
        if (itemsResult != null) {
            if (itemsResult.isSuccess) {
                val items = itemsResult.getOrNull()
                if (items != null) {
                    val groupedItems = createSortedMap(filterItems(items))
                    _itemListScreenUiState.update {
                        it.copy(
                            isLoading = false,
                            sectionedItems = groupedItems
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
            }
        } else {
            _itemListScreenUiState.update {
                it.copy(
                    isLoading = false,
                    error = "Error fetching items"
                )
            }
        }
    }

    init {
        fetchAndHandleItemsResult()
    }
}

interface IItemListScreenViewModel {
    val itemListScreenUiState: StateFlow<ItemListScreenUiState>
    fun fetchAndHandleItemsResult()

    fun createSortedMap(items: List<Item>): SortedMap<Int, List<Item>>

    suspend fun handleItemsResult(itemsResult: Result<List<Item>>?)

}

