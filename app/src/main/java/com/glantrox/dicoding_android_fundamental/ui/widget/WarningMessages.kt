package com.glantrox.dicoding_android_fundamental.ui.widget
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

enum class WarningMessages {ERROR, LOADING, EMPTY}

class Warning(
    val title: String,
    val icon: ImageVector
)

@Composable
fun WarningMessage(type: WarningMessages, callback: () -> Unit? = {}) {
    val warn : Warning = when(type) {
        WarningMessages.ERROR -> Warning("Unknown Error Occured\nClick Here to Retry", Icons.Rounded.Warning)
        WarningMessages.LOADING -> Warning("Loading..", Icons.Rounded.Refresh)
        WarningMessages.EMPTY -> Warning("Empty", Icons.Rounded.Clear)
    }

    val onTap: () -> Unit = {
        if (type == WarningMessages.ERROR) {
            callback()
        }
    }

    return Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = warn.icon, contentDescription = "Warning", Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(warn.title,
            modifier = Modifier.clickable { onTap() },
            textAlign = TextAlign.Center
        )
    }
}