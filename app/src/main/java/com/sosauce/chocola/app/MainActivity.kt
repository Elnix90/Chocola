package com.sosauce.chocola.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.sosauce.chocola.core.data.local.PlaylistCleanup
import com.sosauce.chocola.core.presentation.player.MusicViewModel
import com.sosauce.chocola.app.navigation.Nav
import com.sosauce.chocola.core.data.datastore.dataStore
import com.sosauce.chocola.feature.setup.presentation.SetupScreen
import com.sosauce.chocola.core.designsystem.theme.ChocolaTheme
import com.sosauce.chocola.core.presentation.util.hasMusicPermission
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val playlistCleanup by inject<PlaylistCleanup>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        lifecycleScope.launch {
            playlistCleanup.startCleanup()
        }


        setContent {
            val musicViewModel = koinViewModel<MusicViewModel>()

            ChocolaTheme(
                artImageBitmap = musicViewModel.artworkImageBitmap
            ) {

                var canProceedToApp by remember { mutableStateOf(hasMusicPermission()) }
                if (canProceedToApp) {
                    Nav(
                        musicViewModel = musicViewModel
                    )
                } else {
                    SetupScreen { canProceedToApp = true }
                }

            }
        }

    }

}