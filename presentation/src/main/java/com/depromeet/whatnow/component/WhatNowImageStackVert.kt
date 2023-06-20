package com.depromeet.whatnow.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.depromeet.whatnow.ui.theme.WhatNowTheme
import com.depromeet.whatnow.ui.theme.White
import kotlin.math.min

@Composable
fun WhatNowImageStackVert(
    modifier: Modifier = Modifier,
    imageUrls: List<String>,
    maxLength: Int = 2
) {
    val length = imageUrls.size

    Box(
        modifier = modifier.then(
            Modifier.height((36 + (min(2, length - 1) * 28)).dp)
        )
    ) {
        imageUrls.take(maxLength).forEachIndexed { idx, url ->
            Column {
                Spacer(modifier = Modifier.height((idx * 28).dp))
                if (idx == maxLength - 1 && length > maxLength) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = WhatNowTheme.colors.whatNowBlack,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .border(
                                width = (1.5).dp,
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+${length - maxLength + 1}",
                            style = WhatNowTheme.typography.body4,
                            color = White
                        )
                    }
                } else {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                width = (1.5).dp,
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(14.dp)
                            )
                    )
                }
            }
        }
    }
}