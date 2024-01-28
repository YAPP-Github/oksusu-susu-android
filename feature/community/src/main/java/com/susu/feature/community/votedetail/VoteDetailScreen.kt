package com.susu.feature.community.votedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.DeleteText
import com.susu.core.designsystem.component.appbar.icon.EditText
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.community.R
import com.susu.feature.community.votedetail.component.VoteItem

@Composable
fun VoteDetailRoute(
    popBackStack: () -> Unit,
    @Suppress("detekt:UnusedParameter")
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    VoteDetailScreen(
        onClickBack = popBackStack,
    )
}

@Composable
fun VoteDetailScreen(
    onClickBack: () -> Unit = {},
    onClickReport: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val isMine: Boolean = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            title = "결혼식",
            actions = {
                if (isMine) {
                    EditText(
                        onClick = onClickEdit,
                    )
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                    DeleteText(
                        onClick = onClickDelete,
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .susuClickable(rippleEnabled = false, onClick = onClickReport),
                        painter = painterResource(id = R.drawable.ic_report),
                        contentDescription = stringResource(id = com.susu.core.ui.R.string.content_description_report_button),
                    )
                }
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
            },
        )

        Column(
            modifier = Modifier
                .padding(SusuTheme.spacing.spacing_m)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(20.dp)
                        .border(width = 1.dp, color = Gray15, shape = CircleShape),
                    painter = painterResource(id = com.susu.core.ui.R.drawable.img_default_profile),
                    contentDescription = null,
                )

                Text(text = "익명 수수 123", style = SusuTheme.typography.title_xxxs)
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "고등학교 동창이고 좀 애매하게 친한 사인데 축의금 얼마 내야 돼?",
                style = SusuTheme.typography.text_xxs,
            )

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_vote),
                    contentDescription = null,
                    tint = Orange60,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Text(
                    text = "8명 참여",
                    style = SusuTheme.typography.title_xxxs,
                    color = Orange60,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "2023.11.24",
                    style = SusuTheme.typography.text_xxxs,
                    color = Gray50,
                )
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                repeat(5) {
                    VoteItem(title = "${it}만원", showResult = false)
                }
            }
        }
    }
}

@Preview
@Composable
fun VoteDetailScreenPreview() {
    SusuTheme {
        VoteDetailScreen()
    }
}
