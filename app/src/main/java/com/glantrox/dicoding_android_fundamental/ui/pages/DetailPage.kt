package com.glantrox.dicoding_android_fundamental.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glantrox.dicoding_android_fundamental.core.models.DetailResponse


@Composable
fun DetailPage(
     detail: DetailResponse
) {
    return Column(
        modifier = Modifier.padding(22.dp)
    ) {
        Text("Hello my name is ${detail.name}, I Live in ${detail.location}, and this is my Twitter Account ${detail.twitterUsername}")
    }
}