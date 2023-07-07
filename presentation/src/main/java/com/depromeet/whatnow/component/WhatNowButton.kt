package com.depromeet.whatnow.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.whatnow.ui.theme.WhatNowTheme

@Composable
fun WhatNowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp),
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(45.dp),
        enabled = enabled,
        shape = KnowllyButtonDefaults.ButtonShape,
        colors = KnowllyButtonDefaults.containedButtonColors,
        contentPadding = contentPadding,
    ) {
        Text(text = text)
    }
}

object KnowllyButtonDefaults {
    val ButtonHeight = 56.dp
    val ButtonShape = RoundedCornerShape(10.dp)
    val textStyle
        @Composable
        get() = WhatNowTheme.typography.body3

    val containedButtonColors
        @Composable
        get() = ButtonDefaults.buttonColors(
            containerColor = WhatNowTheme.colors.whatNowPurple,
            contentColor = WhatNowTheme.colors.gray50,
            disabledContainerColor = WhatNowTheme.colors.gray200,
            disabledContentColor = WhatNowTheme.colors.gray900
        )

}

@Preview("Enabled")
@Composable
private fun KnowllyContainedButtonPreviewEnabled() {
    WhatNowTheme {
        WhatNowButton(
            text = "버튼",
            onClick = { },
            enabled = true,
        )
    }
}