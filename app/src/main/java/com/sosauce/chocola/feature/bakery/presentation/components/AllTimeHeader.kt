@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.chocola.feature.bakery.presentation.components

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sosauce.chocola.R
import com.sosauce.chocola.core.designsystem.theme.ChocolaTheme
import com.sosauce.chocola.feature.bakery.presentation.model.AllTimeStatsUi
import com.sosauce.chocola.feature.bakery.presentation.model.ArtistStatUi
import com.sosauce.chocola.feature.bakery.presentation.model.StreakUi
import com.sosauce.chocola.feature.bakery.presentation.model.TrackStatUi
import com.sosauce.nekobites.components.Spacer
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

@Composable
fun AllTimeHeader(
    stats: AllTimeStatsUi,
    streak: StreakUi,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = stringResource(R.string.all_time),
//                    style = MaterialTheme.typography.labelLargeEmphasized,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
//                )
//                Icon(
//                    painter = painterResource(R.drawable.cookie),
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                    modifier = Modifier.size(26.dp)
//                )
//            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stats.totalListens.toString(),
                    style = MaterialTheme.typography.displayLargeEmphasized.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Text(
                    text = stringResource(R.string.listens),
                    style = MaterialTheme.typography.titleMediumEmphasized.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f)
                    ),
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoPill(
                    label = stringResource(R.string.listening_time),
                    value = formatListeningTime(stats.totalListenedMillis),
                    modifier = Modifier.weight(1f)
                )
                stats.topArtist?.let {
                    InfoPill(
                        label = stringResource(R.string.top_artist),
                        value = it.name,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            stats.topTrack?.let { track ->

                Column(
                    modifier = modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.08f))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.top_track),
                        style = MaterialTheme.typography.labelSmallEmphasized.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.65f)
                        )
                    )
                    Spacer(10.dp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MiniArtwork(artworkUri = track.artworkUri)

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = track.title,
                                style = MaterialTheme.typography.titleSmallEmphasized,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.basicMarquee()
                            )
                            Text(
                                text = track.artist,
                                style = MaterialTheme.typography.bodySmallEmphasized,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                                modifier = Modifier.basicMarquee()
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val iconSize = MaterialTheme.typography.labelMediumEmphasized.fontSize.value.dp + 5.dp
                            Icon(
                                painter = painterResource(R.drawable.music_note),
                                contentDescription = null,
                                modifier = Modifier.size(iconSize)
                            )
                            Spacer(5.dp)
                            Text(
                                text = track.listenCount.toString(),
                                style = MaterialTheme.typography.labelMediumEmphasized,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoPill(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.08f))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmallEmphasized,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.65f)
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLargeEmphasized,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MiniArtwork(artworkUri: Uri?) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(SquircleShape(smoothing = CornerSmoothing.Full))
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.music_note),
            contentDescription = null
        )
        AsyncImage(
            model = artworkUri,
            contentDescription = stringResource(R.string.artwork),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun formatListeningTime(millis: Long): String {
    val totalMinutes = (millis / 60_000L).coerceAtLeast(0L)
    val hours = totalMinutes / 60L
    val minutes = totalMinutes % 60L
    return if (hours > 0L) {
        stringResource(R.string.bakery_hours_minutes_short, hours, minutes)
    } else {
        stringResource(R.string.bakery_minutes_short, minutes)
    }
}

@Preview(showBackground = true, widthDp = 380, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AllTimeHeaderPreview() {

    ChocolaTheme {
        AllTimeHeader(
            stats = AllTimeStatsUi(
                totalListens = 12_482,
                totalListenedMillis = 218_340_000L,
                topTrack = TrackStatUi(
                    mediaId = "1",
                    title = "Cinnamon Girl",
                    artist = "Lana Del Rey",
                    album = "Norman Fucking Rockwell!",
                    artworkUri = null,
                    listenCount = 412
                ),
                topArtist = ArtistStatUi(
                    name = "Lana Del Rey",
                    listenCount = 1_284
                )
            ),
            streak = StreakUi(current = 12, best = 36),
            modifier = Modifier.padding(16.dp)
        )
    }
}
