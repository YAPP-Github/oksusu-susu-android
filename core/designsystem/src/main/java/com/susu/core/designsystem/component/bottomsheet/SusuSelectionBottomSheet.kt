package com.susu.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuSelectionBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit = {},
    cornerRadius: Dp = 24.dp,
    containerHeight: Dp,
    items: ImmutableList<String>,
    selectedItemPosition: Int,
    onClickItem: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    SusuBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        cornerRadius = cornerRadius,
        containerHeight = containerHeight,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            items.forEachIndexed { index, name ->
                SusuGhostButton(
                    modifier = Modifier.fillMaxWidth(),
                    color = GhostButtonColor.Black,
                    style = SmallButtonStyle.height48,
                    isActive = index == selectedItemPosition,
                    isClickable = true,
                    text = name,
                    onClick = { onClickItem(index) },
                )
            }
        }
    }
}
