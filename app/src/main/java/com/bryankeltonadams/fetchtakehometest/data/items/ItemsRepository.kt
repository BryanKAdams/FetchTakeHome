package com.bryankeltonadams.fetchtakehometest.data.items

import com.bryankeltonadams.fetchtakehometest.data.model.Item
import javax.inject.Inject
import javax.inject.Singleton

interface IItemsRepository {
    suspend fun getNetworkItems(): Result<List<Item>>

    suspend fun getStaticItems(): Result<List<Item>>
}

@Singleton
class ItemsRepository
@Inject
constructor(
    private val itemsRemoteDataSource: IItemsRemoteDataSource,
) : IItemsRepository {
    override suspend fun getNetworkItems(): Result<List<Item>> {
        return itemsRemoteDataSource.getItems()
    }


    // would move this to a local data source or a static, offline data source
    override suspend fun getStaticItems(): Result<List<Item>> {
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
        return Result.success(dummyItems)
    }
}
