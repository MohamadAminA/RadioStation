package com.radioroam.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import com.radioroam.android.domain.model.RadioStation

@Composable
fun RadioStationList(
    modifier: Modifier = Modifier,
    items: List<RadioStation>,
    onItemClick: (Int) -> Unit = {},
    onFavClick: (RadioStation) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = items) { item ->
            RadioStationRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(Color.White)
                    .padding(16.dp)
                    .clickable {
                        onItemClick(items.indexOf(item))
                    },
                item = item,
                isFavorite = item.isFavorite,
                onFavClick = onFavClick
            )
        }
    }
}