package com.bryankeltonadams.fetchtakehometest

import com.bryankeltonadams.fetchtakehometest.data.items.IItemsRemoteDataSource
import com.bryankeltonadams.fetchtakehometest.data.items.ItemsRepository
import com.bryankeltonadams.fetchtakehometest.data.model.Item
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ItemsRepositoryTest {

    private lateinit var itemsRemoteDataSource: IItemsRemoteDataSource
    private lateinit var itemsRepository: ItemsRepository

    @Before
    fun setUp() {
        // Mock the IItemsRemoteDataSource
        itemsRemoteDataSource = mockk()

        // Instantiate ItemsRepository with the mocked IItemsRemoteDataSource
        itemsRepository = ItemsRepository(itemsRemoteDataSource)
    }

    @Test
    fun `getNetworkItems returns expected items`() = runBlocking {
        // Arrange
        val expectedItems = listOf(Item(id = 1, listId = 1, name = "Test Item"))
        coEvery { itemsRemoteDataSource.getItems() } returns Result.success(expectedItems)

        // Act
        val result = itemsRepository.getNetworkItems()

        // Assert
        assertEquals(Result.success(expectedItems), result)
    }

    @Test
    fun `getStaticItems returns predefined list of items`() = runBlocking {
        // Act
        val result = itemsRepository.getStaticItems()

        // Assert
        assert(result.isSuccess)
        val items = result.getOrNull()!!
        assertEquals(16, items.size) // Verify the size of the returned list
        assertEquals("Item 684", items.first { it.id == 684 }.name) // Verify specific item details
    }

    @Test
    fun `getNetworkItems returns failure on data source error`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Network error")
        coEvery { itemsRemoteDataSource.getItems() } returns Result.failure(exception)

        // Act
        val result = itemsRepository.getNetworkItems()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getNetworkItems returns empty list when data source returns empty list`() = runBlocking {
        // Arrange
        coEvery { itemsRemoteDataSource.getItems() } returns Result.success(emptyList())

        // Act
        val result = itemsRepository.getNetworkItems()

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isEmpty())
    }
}
