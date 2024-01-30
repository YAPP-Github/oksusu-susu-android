package com.susu.feature.loginsignup.signup.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.component.util.CheckCircle
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Term
import com.susu.core.ui.R
import com.susu.core.ui.extension.susuClickable

@Composable
fun TermsContent(
    modifier: Modifier = Modifier,
    descriptionText: String = "",
    terms: List<Term> = emptyList(),
    agreedTerms: List<Int> = emptyList(),
    onDetailClick: (termId: Int) -> Unit = {},
    onSelectAll: (agree: Boolean) -> Unit = {},
    onTermChecked: (agree: Boolean, id: Int) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier.padding(SusuTheme.spacing.spacing_m),
    ) {
        Text(
            text = descriptionText,
            style = SusuTheme.typography.title_m,
        )
        Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xl))
        TermListItem(
            title = stringResource(com.susu.feature.loginsignup.R.string.signup_term_agree_all),
            checked = agreedTerms.containsAll(terms.map { it.id }),
            isEssential = false,
            canRead = false,
            onCheckClick = { onSelectAll(it) },
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = Gray30,
        )
        terms.forEach { term ->
            TermListItem(
                title = term.title,
                checked = agreedTerms.contains(term.id),
                isEssential = term.isEssential,
                onDetailClick = { onDetailClick(term.id) },
                onCheckClick = {
                    onTermChecked(it, term.id)
                },
            )
        }
    }
}

@Composable
fun TermListItem(
    modifier: Modifier = Modifier,
    title: String = "",
    checked: Boolean = false,
    isEssential: Boolean = true,
    canRead: Boolean = true,
    onCheckClick: (Boolean) -> Unit = {},
    onDetailClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.padding(vertical = SusuTheme.spacing.spacing_m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CheckCircle(isChecked = checked, onCheckedChange = onCheckClick)
        Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_m))
        if (isEssential) {
            SusuBadge(
                color = BadgeColor.Blue60,
                text = stringResource(com.susu.feature.loginsignup.R.string.signup_essential),
                padding = BadgeStyle.smallBadge,
            )
            Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = SusuTheme.typography.title_xxs,
        )
        if (canRead) {
            Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_m))
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .susuClickable(rippleEnabled = false, onClick = onDetailClick),
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "약관 보기",
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TermsContentPreview() {
    SusuTheme {
        TermsContent(
            modifier = Modifier.fillMaxSize(),
            descriptionText = "어쩌구저쩌구\n뭐를해주세요",
            terms = listOf(Term(1, "노예 계약", true), Term(2, "농노 계약", true)),
        )
    }
}

@Preview
@Composable
fun TermCheckCirclePreview() {
    SusuTheme {
        CheckCircle(isChecked = true)
    }
}
