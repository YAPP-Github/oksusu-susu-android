package com.susu.feature.community

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun CommunityRoute(
    padding: PaddingValues,
    navigateVoteAdd: () -> Unit,
) {
    CommunityScreen(
        padding = padding,
        navigateVoteAdd = navigateVoteAdd,
    )
}

@Composable
fun CommunityScreen(
    padding: PaddingValues,
    navigateVoteAdd: () -> Unit = {},
) {
    Text(
        modifier = Modifier
            .padding(padding)
            .susuClickable(onClick = navigateVoteAdd),
        text = "투표",
        fontSize = 48.sp,
    )
}

@Preview
@Composable
fun CommunityScreenPreview() {
    SusuTheme {
        CommunityScreen(padding = PaddingValues(0.dp))
    }
}
