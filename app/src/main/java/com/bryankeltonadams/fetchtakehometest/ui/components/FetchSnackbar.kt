package com.bryankeltonadams.fetchtakehometest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bryankeltonadams.fetchtakehometest.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class FetchSnackbarData(
    val type: SnackbarType = SnackbarType.INFO,
    val titleResId: Int,
    val messageResId: Int
)

enum class SnackbarType(
    val backgroundColor: Color,
    val icon: ImageVector,
    val iconColor: Color,
    val iconBackgroundColor: Color,
) {
    INFO(
        Color(0xFFF1F7FC),
        Icons.Outlined.Info,
        Color(0xFF486075),
        Color(0xFFE5EBF0)
    ),
    WARNING(
        Color(0xFFFFF9E4),
        Icons.Outlined.Warning,
        Color(0xFF8F7104),
        Color(0xFFFDF0BE)
    ),
    ERROR(
        Color(0xFFFFEDED),
        Icons.Outlined.Close,
        Color(0xFFA50808),
        Color(0xFFFDD8D8)
    ),
    SUCCESS(
        Color(0xFFE6FCED),
        Icons.Outlined.CheckCircle,
        Color(0xFF0B411D),
        Color(0xFFCBF6D9)
    )
}

@Composable
fun FetchSnackbarHost(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = snackbarHostState
    ) { snackbarData ->
        val fetchSnackbarData: FetchSnackbarData?
        try {
            fetchSnackbarData = Json.decodeFromString(snackbarData.visuals.message)
        } catch (e: Exception) {
            return@SnackbarHost
        }
        if (fetchSnackbarData != null) {
            FetchSnackbar(fetchSnackbarData)
        }
    }
}

@Composable
fun FetchSnackbar(fetchSnackbarData: FetchSnackbarData) {
    Snackbar(
        containerColor = fetchSnackbarData.type.backgroundColor,
        contentColor = fetchSnackbarData.type.iconColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(52.dp)
                    .fillMaxHeight()
                    .background(fetchSnackbarData.type.iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = fetchSnackbarData.type.icon,
                    tint = fetchSnackbarData.type.iconColor,
                    contentDescription = "Notification Icon"
                )
            }
            Column {
                Text(
                    text = stringResource(id = fetchSnackbarData.titleResId),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = fetchSnackbarData.messageResId), style =
                    MaterialTheme.typography.bodyMedium
                )

            }
        }

    }
}

@Composable
@Preview
fun FetchSnackbarPreviewInfo() {
    val fetchSnackbarData = FetchSnackbarData(
        type = SnackbarType.INFO,
        titleResId = R.string.app_name,
        messageResId = R.string.app_name
    )
    FetchSnackbar(fetchSnackbarData = fetchSnackbarData)

}

@Composable
@Preview
fun FetchSnackbarPreviewWarning() {
    val fetchSnackbarData = FetchSnackbarData(
        type = SnackbarType.WARNING,
        titleResId = R.string.app_name,
        messageResId = R.string.app_name
    )
    FetchSnackbar(fetchSnackbarData = fetchSnackbarData)
}

@Composable
@Preview
fun FetchSnackbarPreviewSuccess() {
    val fetchSnackbarData = FetchSnackbarData(
        type = SnackbarType.SUCCESS,
        titleResId = R.string.app_name,
        messageResId = R.string.app_name
    )
    FetchSnackbar(fetchSnackbarData = fetchSnackbarData)

}

@Composable
@Preview
fun FetchSnackbarPreviewError() {
    val fetchSnackbarData = FetchSnackbarData(
        type = SnackbarType.ERROR,
        titleResId = R.string.app_name,
        messageResId = R.string.app_name
    )
    FetchSnackbar(fetchSnackbarData = fetchSnackbarData)

}
