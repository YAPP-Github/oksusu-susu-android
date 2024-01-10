package com.susu.core.designsystem.component.bottomsheet.datepicker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.bottomsheet.SusuBottomSheet
import com.susu.core.designsystem.theme.SusuTheme
import okhttp3.internal.toImmutableList
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuYearPickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    yearRange: IntRange = 1930..2030,
    initialYear: Int = LocalDate.now().year,
    maximumContainerHeight: Dp,
    itemHeight: Dp = 48.dp,
    numberOfDisplayedItems: Int = 5,
    cornerRadius: Dp = 24.dp,
    onDismissRequest: (Int) -> Unit = {},
) {
    var selectedYear by remember { mutableIntStateOf(initialYear) }
    val yearList = yearRange.map { "${it}년" }.toImmutableList()
    SusuBottomSheet(
        sheetState = sheetState,
        containerHeight = minOf(maximumContainerHeight, itemHeight * numberOfDisplayedItems + 32.dp),
        onDismissRequest = { onDismissRequest(selectedYear) },
        cornerRadius = cornerRadius,
    ) {
        InfiniteColumn(
            modifier = Modifier.fillMaxWidth(),
            items = yearList,
            initialItem = "${initialYear}년",
            itemHeight = itemHeight,
            numberOfDisplayedItems = numberOfDisplayedItems,
            onItemSelected = { _, item ->
                selectedYear = item.dropLast(1).toIntOrNull() ?: initialYear
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SusuYearPickerBottomSheetPreview() {
    SusuTheme {
        SusuYearPickerBottomSheet(maximumContainerHeight = 346.dp, onDismissRequest = { println(it) })
    }
}
