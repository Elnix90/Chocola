@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.chocola.feature.setup.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sosauce.chocola.R
import com.sosauce.chocola.core.presentation.preferences.rememberMinTrackDuration
import com.sosauce.chocola.feature.settings.presentation.components.SliderSettingsCards
import com.sosauce.chocola.feature.settings.presentation.components.foldersView
import com.sosauce.nekobites.helpers.ScopedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupFolders(
    onNext: () -> Unit
) {
    ScopedViewModel {
        val viewModel = koinViewModel<SetupViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        var minTrackDuration by rememberMinTrackDuration()

        AnimatedContent(
            targetState = state.isLoadingFolders,
            modifier = Modifier.fillMaxSize()
        ) {
            if (it) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.scanning_folders_notice),
                            style = MaterialTheme.typography.bodyLargeEmphasized
                        )
                        LinearWavyProgressIndicator()

                    }
                }
            } else {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    item(key = "header_setup") {
                        Box(
                            modifier = Modifier
                                .size(156.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = MaterialShapes.Cookie12Sided.toShape()
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.hide_filled),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Text(
                            text = stringResource(R.string.library_setup),
                            style = MaterialTheme.typography.headlineSmallEmphasized.copy(
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            text = stringResource(R.string.library_setup_desc),
                            style = MaterialTheme.typography.bodyMediumEmphasized.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        )
                    }


                    foldersView(
                        whitelisted = state.whitelistedFolders,
                        blacklisted = state.blacklistedFolders,
                        onBlacklist = { paths ->
                            viewModel.handleSetupAction(
                                SetupActions.BlacklistFolders(paths)
                            )
                        },
                        onWhitelist = { paths ->
                            viewModel.handleSetupAction(
                                SetupActions.WhitelistFolders(paths)
                            )
                        }
                    )

                    item(key = "trailing_setup") {
                        Spacer(Modifier.height(25.dp))
                        SliderSettingsCards(
                            value = minTrackDuration,
                            onValueChange = { minTrackDuration = it },
                            topDp = 24.dp,
                            bottomDp = 24.dp,
                            text = stringResource(R.string.min_track_length_text)
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = onNext,
                            shapes = ButtonDefaults.shapes(),
                        ) {
                            Text(
                                text = "Let's a meow!"
                            )
                        }
                    }

                }
            }
        }


    }


}