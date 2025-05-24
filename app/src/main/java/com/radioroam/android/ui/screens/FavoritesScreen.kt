package com.radioroam.android.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.radioroam.android.R
import com.radioroam.android.ui.components.FavoriteTopAppBar
import com.radioroam.android.ui.components.RadioStationList
import com.radioroam.android.ui.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    navController: NavController = rememberNavController()
) {
    val favoriteStations by viewModel.favorites.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            FavoriteTopAppBar {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // اضافه کردن تصویر پس‌زمینه
            Image(
                painter = painterResource(id = R.drawable.headphones2),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // اضافه کردن لایه تیره با شفافیت 80%
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
            )
                        RadioStationList(
                        modifier = Modifier
                            .padding(paddingValues),
                items = favoriteStations,
                onItemClick = { index ->
                    // favoriteStations[index]?.let { viewModel.removeItem(it) }
                },
                onFavClick = {
                    viewModel.removeItem(it)
                }
            )
        }
    }
}