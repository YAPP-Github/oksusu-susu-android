package com.susu.feature.received.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.searchbar.SusuSearchBar
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.received.R

@Composable
fun LedgerSearchRoute(
    popBackStack: () -> Unit,
) {
    LedgerSearchScreen()
}

@Composable
fun LedgerSearchScreen(
    onClickBackIcon: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background10)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .susuClickable(
                                onClick = onClickBackIcon,
                            )
                            .padding(SusuTheme.spacing.spacing_xs),
                        painter = painterResource(id = com.susu.core.ui.R.drawable.ic_back),
                        contentDescription = stringResource(com.susu.core.ui.R.string.content_description_back_icon),
                    )
                },
            )

            Column(
                modifier = Modifier.padding(
                    top = SusuTheme.spacing.spacing_xxs,
                    start = SusuTheme.spacing.spacing_m,
                    end = SusuTheme.spacing.spacing_m,
                )
            ) {
                SusuSearchBar(value = "", placeholder = "찾고 싶은 장부를 검색해보세요")
            }
        }
    }
}

@Preview
@Composable
fun LedgerSearchScreenPreview() {
    SusuTheme {
        LedgerSearchScreen()
    }
}
