package com.sosauce.chocola.feature.settings.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sosauce.chocola.R
import com.sosauce.chocola.feature.settings.presentation.components.ClickableSettingsCard
import com.sosauce.chocola.feature.settings.presentation.components.SettingsWithTitle

@Composable
fun SettingsBackup(
    onBackupAction: (BackupAction) -> Unit
) {

    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let {
            onBackupAction(
                BackupAction.Export(it)
            )
        }
    }
    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            onBackupAction(
                BackupAction.Import(it)
            )
        }
    }

    SettingsWithTitle(R.string.backup_and_restore) {
        ClickableSettingsCard(
            onClick = { exportLauncher.launch("chocola_backup.json") },
            topDp = 24.dp,
            bottomDp = 2.dp,
            text = stringResource(R.string.export_settings),
            optionalDescription = R.string.export_settings_desc
        )
        ClickableSettingsCard(
            onClick = { importLauncher.launch(arrayOf("application/json")) },
            topDp = 2.dp,
            bottomDp = 24.dp,
            text = stringResource(R.string.import_settings),
            optionalDescription = R.string.import_settings_desc
        )

    }
}