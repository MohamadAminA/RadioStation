package com.radioroam.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.radioroam.android.R
import com.radioroam.android.domain.model.RadioStation
import com.radioroam.android.ui.components.loading.LoadingNextPageItem
import androidx.compose.foundation.lazy.items
import com.radioroam.android.ui.viewmodel.ScreenState

@Composable
fun RadioStationPaginatedList(
    modifier: Modifier = Modifier,
    state: ScreenState,
    onNextPage: () -> Unit,
    onItemClick: (Int) -> Unit = {},
    onFavClick: (RadioStation) -> Unit = {}
) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.headphones),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(state.items) { item ->
                RadioStationRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(16.dp)
                        .clickable {
                            onItemClick(state.items.indexOf(item))
                        },
                    item = item,
                    isFavorite = item.isFavorite,
                    onFavClick = onFavClick
                )
            }

            item {
                if (state.isLoading) {
                    LoadingNextPageItem()
                }
            }
        }
    }
}