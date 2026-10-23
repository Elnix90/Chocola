@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.chocola.feature.bakery.presentation.story.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sosauce.chocola.R
import com.sosauce.chocola.core.designsystem.components.CuteListItem
import com.sosauce.chocola.core.presentation.util.getCuteItemShape
import com.sosauce.chocola.feature.bakery.presentation.model.AlbumStatUi
import com.sosauce.chocola.feature.bakery.presentation.model.ArtistStatUi
import com.sosauce.chocola.feature.bakery.presentation.model.PeriodId
import com.sosauce.chocola.feature.bakery.presentation.model.PeriodType
import com.sosauce.chocola.feature.bakery.presentation.model.StoryPageUi
import com.sosauce.chocola.feature.bakery.presentation.model.StreakUi
import com.sosauce.chocola.feature.bakery.presentation.model.TopDayUi
import com.sosauce.chocola.feature.bakery.presentation.model.TrackStatUi
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape


@Composable
fun SummaryPage(
    summary: StoryPageUi.Summary,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .padding(top = 15.dp)
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    )
                ),
                RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.cookie),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.bakery_summary_title),
                        style = MaterialTheme.typography.labelLargeEmphasized,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = summary.title,
                    style = MaterialTheme.typography.displaySmallEmphasized,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = summary.totalListens.toString(),
                        style = MaterialTheme.typography.displayMediumEmphasized,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.listens),
                        style = MaterialTheme.typography.titleMediumEmphasized,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
                Text(
                    text = stringResource(
                        R.string.listening_time_value,
                        formatListeningTime(summary.totalListenedMillis)
                    ),
                    style = MaterialTheme.typography.bodyMediumEmphasized,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(18.dp))

                summary.topTrack?.let { track ->
                    TopTrackCard(track = track)
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    SummaryStat(
                        label = stringResource(R.string.top_artist),
                        value = summary.topArtist?.name ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                    SummaryStat(
                        label = stringResource(R.string.current_streak),
                        value = summary.streak.current.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    SummaryStat(
                        label = stringResource(R.string.album),
                        value = summary.topAlbum?.name ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                    SummaryStat(
                        label = stringResource(R.string.bakery_top_day),
                        value = summary.topDay?.label ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TopTrackCard(track: TrackStatUi) {


    Column {

        CuteListItem(
            onClick = null,
            shape = RoundedCornerShape(24.dp),
            backgroundColor =  MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.85f),
            leadingContent = {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(50.dp)
                        .clip(SquircleShape(smoothing = CornerSmoothing.Full))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.music_note),
                        contentDescription = null
                    )
                    AsyncImage(
                        model = track.artworkUri,
                        contentDescription = stringResource(R.string.artwork),
                        contentScale = ContentScale.Crop
                    )
                }
            },
            trailingContent = {
                Text(
                    text = stringResource(R.string.listens_count_value, track.listenCount),
                    style = MaterialTheme.typography.labelMediumEmphasized.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        ) {
            Text(
                text = track.title,
                maxLines = 1,
                style = MaterialTheme.typography.titleMediumEmphasized,
                modifier = Modifier.basicMarquee()
            )
            Text(
                text = track.artist,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLargeEmphasized.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.basicMarquee()
            )
        }
    }
//    Surface(
//        shape = MaterialTheme.shapes.extraLarge,
//        color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.85f)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.music_note),
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.size(22.dp)
//                )
//                if (track.artworkUri != null) {
//                    AsyncImage(
//                        model = track.artworkUri,
//                        contentDescription = stringResource(R.string.artwork),
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.matchParentSize()
//                    )
//                }
//            }
//
//            Spacer(Modifier.width(12.dp))
//
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = stringResource(R.string.top_track),
//                    style = MaterialTheme.typography.labelSmallEmphasized,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Text(
//                    text = track.title,
//                    style = MaterialTheme.typography.titleSmallEmphasized,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//                Text(
//                    text = track.artist,
//                    style = MaterialTheme.typography.bodySmallEmphasized,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            Text(
//                text = stringResource(R.string.listens_count_value, track.listenCount),
//                style = MaterialTheme.typography.labelMediumEmphasized,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    }
}

@Composable
private fun SummaryStat(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmallEmphasized,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmallEmphasized,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
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

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun SummaryPagePreview() {
    MaterialTheme {
        SummaryPage(
            summary = StoryPageUi.Summary(
                period = PeriodId(PeriodType.MONTH, "2026-10"),
                title = "October 2026",
                totalListens = 1_284,
                totalListenedMillis = 21_834_000L,
                topTrack = TrackStatUi(
                    mediaId = "1",
                    title = "Cinnamon Girl",
                    artist = "Lana Del Rey",
                    album = "Norman Fucking Rockwell!",
                    artworkUri = null,
                    listenCount = 412
                ),
                topArtist = ArtistStatUi("Lana Del Rey", 1_024),
                topAlbum = AlbumStatUi(
                    name = "Norman Fucking Rockwell!",
                    artist = "Lana Del Rey",
                    artworkUri = null,
                    listenCount = 892
                ),
                streak = StreakUi(current = 12, best = 36),
                topDay = TopDayUi(
                    epochDay = 20_694,
                    label = "Saturday, October 18",
                    listenCount = 186,
                    listenedMillis = 3_060_000L
                )
            )
        )
    }
}
