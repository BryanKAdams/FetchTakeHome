package com.bryankeltonadams.fetchtakehometest.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bryankeltonadams.fetchtakehometest.R
import com.bryankeltonadams.fetchtakehometest.data.model.Item
import com.bryankeltonadams.fetchtakehometest.ui.components.FetchSnackbarData
import kotlinx.serialization.json.Json

@Composable
// some code bases name this function ItemListRoute, since it's composable that lives
// within the navigation graph and pulls the data and actions from the view model
// I prefer to just use the same name but a different signature
fun ItemListScreen(
    itemListScreenViewModel: ItemListScreenViewModel, onShowSnackbar: (String) -> Unit
) {
    val uiState by itemListScreenViewModel.itemListScreenUiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.fetchSnackbarData) {
        uiState.fetchSnackbarData?.let { snackbarData ->
            val sbData = Json.encodeToString(FetchSnackbarData.serializer(), snackbarData)
            onShowSnackbar(sbData)
            itemListScreenViewModel.clearSnackbarData()
        }

    }
    ItemListScreen(
        itemListScreenUiState = uiState, onRefresh = itemListScreenViewModel::refresh
    )
}

data class ItemListScreenUiState(
    val sectionedItems: Map<Int, List<Item>>? = null,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessageResId: Int? = null,
    val fetchSnackbarData: FetchSnackbarData? = null
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    itemListScreenUiState: ItemListScreenUiState, onRefresh: () -> Unit
) {
    val sectionedItems = itemListScreenUiState.sectionedItems

    // I had a great discussion with multiple guys in the Kotlin Slack channel
    // about the production implementation of PullToRefresh and how it could use some work
    // and how it was a step back from the material 2 implementation
    // this new PullToRefreshBox on the material3 beta is much better

    PullToRefreshBox(isRefreshing = itemListScreenUiState.isRefreshing, onRefresh = onRefresh) {
        // I could have put the conditional logic inside the lazy column and created
        // single box items for the progress indicator and the error message
        // but I prefer having the other conditional parts of the ui outside the lazy column
        // this does require having to pass in the scroll state to the boxes though
        if (itemListScreenUiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else if (itemListScreenUiState.errorMessageResId != null && itemListScreenUiState.sectionedItems.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = itemListScreenUiState.errorMessageResId),
                    textAlign = TextAlign.Center,
                )
            }

        } else if (itemListScreenUiState.sectionedItems.isNullOrEmpty()) {
            // this shouldn't happen, but would sort of simulate if you updated the docuemnt
            // to an empty array
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.list_item_empty_text),
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                sectionedItems?.forEach {
                    // create a sticky header for each listId
                    stickyHeader {
                        /** something quirky I noticed with the Divider implementation is
                        the gap between the Divider and ListItem isn't perfectly flush
                        if you look closely you can see a bit of the items text bleed through
                        I left this here to pinpoint it and show my attention for detail
                        I've resolved this issue by just putting a border on the ListItem
                        which technically does put borders on the sides as well
                        but if I wanted to I could have created a customer modifier that
                        only draws a border on the top or bottom.
                        ^ have to uncomment below code and comment the new  ListItem to
                        see what I mean
                         */

//                        HorizontalDivider()
//                        ListItem(headlineContent = {
//                            Text(
//                                text = "List ${it.key}",
//                                style = MaterialTheme.typography.titleLarge
//                            )
//                        })
//                        HorizontalDivider()

                        ListItem(modifier = Modifier.border(1.dp, Color.Black), headlineContent = {
                            Text(
                                text = stringResource(id = R.string.list_header_title, it.key),
                                style = MaterialTheme.typography.titleLarge
                            )
                        })
                    }
                    // go through the items in the map and create a ListItem for each
                    items(it.value) { item ->
                        ListItem(headlineContent = {
                            item.id?.let { itemId ->
                                Text(
                                    stringResource(
                                        id = R.string.list_item_headline_text, itemId
                                    )
                                )
                            }
                        }, supportingContent = {
                            item.name?.let { itemName ->
                                Text(
                                    stringResource(
                                        id = R.string.list_item_supporting_text, itemName
                                    )
                                )
                            }
                        },
                            // sort of redundant, but for the sake of displaying
                            // all of the data within the item.
                            trailingContent = {
                                Text(
                                    stringResource(
                                        id = R.string.list_item_trailing_text, item.listId
                                    )
                                )
                            })
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ItemListScreenSuccessPreview() {
    ItemListScreen(itemListScreenUiState = ItemListScreenUiState(
        sectionedItems = mapOf(
            1 to listOf(
                Item(1, "Cute Dog", 1), Item(2, "Cute Cat", 1), Item(3, "Cute Fish", 1)
            ), 2 to listOf(
                Item(4, "Cute Cow", 2), Item(5, "Cute Horse", 2), Item(6, "Cute Pig", 2)
            ), 3 to listOf(
                Item(7, "Cute Elephant", 3),
                Item(8, "Cute Giraffe", 3),
                Item(9, "Cute Monkey", 3)
            ), 4 to listOf(
                Item(10, "Cute Bear", 4), Item(11, "Cute Lion", 4), Item(12, "Cute Tiger", 4)
            ), 5 to listOf(
                Item(13, "Cute Penguin", 5),
                Item(14, "Cute Eagle", 5),
                Item(15, "Cute Flamingo", 5)
            )
        ), isLoading = false, isRefreshing = false, errorMessageResId = null
    ), onRefresh = {})
}

@Composable
@Preview
fun ItemListScreenLoadingPreview() {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            ItemListScreen(itemListScreenUiState = ItemListScreenUiState(
                sectionedItems = null,
                isLoading = true,
                isRefreshing = false,
                errorMessageResId = null
            ), onRefresh = {})
        }
    }
}

@Composable
@Preview
fun ItemListScreenErrorPreview() {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            ItemListScreen(itemListScreenUiState = ItemListScreenUiState(
                sectionedItems = null,
                isLoading = false,
                isRefreshing = false,
                errorMessageResId = R.string.list_item_error_text
            ), onRefresh = {})
        }
    }
}

@Composable
@Preview
fun ItemListScreenSuccessNoItemsPreview() {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            ItemListScreen(itemListScreenUiState = ItemListScreenUiState(
                sectionedItems = null,
                isLoading = false,
                isRefreshing = false,
                errorMessageResId = null
            ), onRefresh = {})
        }
    }
}
