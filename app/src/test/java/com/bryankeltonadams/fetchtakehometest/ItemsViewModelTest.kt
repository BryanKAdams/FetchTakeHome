import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bryankeltonadams.fetchtakehometest.data.items.IItemsRepository
import com.bryankeltonadams.fetchtakehometest.data.model.Item
import com.bryankeltonadams.fetchtakehometest.ui.screens.ItemListScreenViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemListScreenViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ItemListScreenViewModel
    private lateinit var itemsRepository: IItemsRepository

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        itemsRepository = mockk(relaxed = true)
        viewModel = ItemListScreenViewModel(itemsRepository)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `filteredAndSortedMap returns correctly filtered and sorted items`() = runBlocking {
        val items = listOf(
            Item(id = 3, listId = 2, name = "B"),
            Item(id = 1, listId = 1, name = "C"),
            Item(id = 2, listId = 1, name = "A"),
            Item(id = 4, listId = 2, name = "")
        )
        val expected = mapOf(
            1 to listOf(Item(id = 2, listId = 1, name = "A"), Item(id = 1, listId = 1, name = "C")),
            2 to listOf(Item(id = 3, listId = 2, name = "B"))
        )
        val result = viewModel.filteredAndSortedMap(items)
        assertEquals(expected, result)
    }

}