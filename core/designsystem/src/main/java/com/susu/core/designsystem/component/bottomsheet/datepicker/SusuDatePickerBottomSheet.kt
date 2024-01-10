package com.susu.core.designsystem.component.bottomsheet.datepicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.bottomsheet.SusuBottomSheet
import com.susu.core.designsystem.theme.SusuTheme
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuDatePickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    yearRange: IntRange = 1930..2030,
    maximumContainerHeight: Dp,
    itemHeight: Dp = 48.dp,
    numberOfDisplayedItems: Int = 5,
    cornerRadius: Dp = 24.dp,
    onDismissRequest: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    var selectedYear by remember { mutableIntStateOf(LocalDate.now().year) }
    var selectedMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }
    var selectedDay by remember { mutableIntStateOf(LocalDate.now().dayOfMonth) }
    var lastDayInMonth by remember { mutableIntStateOf(getLastDayInMonth(LocalDate.now().year, LocalDate.now().monthValue)) }

    val yearList = yearRange.map { "${it}년" }.toImmutableList()
    val monthList = (1..12).map { "${it}월" }.toImmutableList()

    SusuBottomSheet(
        sheetState = sheetState,
        containerHeight = minOf(maximumContainerHeight, itemHeight * numberOfDisplayedItems + 32.dp),
        onDismissRequest = { onDismissRequest(selectedYear, selectedMonth, selectedDay) },
        cornerRadius = cornerRadius,
    ) {
        Row {
            InfiniteColumn(
                modifier = Modifier.width(100.dp),
                items = yearList,
                initialItem = "${LocalDate.now().year}년",
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedYear = item.dropLast(1).toIntOrNull() ?: LocalDate.now().year
                    getLastDayInMonth(selectedYear, selectedMonth).run {
                        lastDayInMonth = this
                        if (selectedDay > lastDayInMonth) {
                            selectedDay = 1
                        }
                    }
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
            )
            InfiniteColumn(
                modifier = Modifier.width(100.dp),
                items = monthList,
                initialItem = "${LocalDate.now().monthValue}월",
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedMonth = item.dropLast(1).toIntOrNull() ?: LocalDate.now().monthValue
                    getLastDayInMonth(selectedYear, selectedMonth).run {
                        lastDayInMonth = this
                        if (selectedDay > lastDayInMonth) {
                            selectedDay = 1
                        }
                    }
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
            )
            InfiniteColumn(
                modifier = Modifier.width(100.dp),
                items = (1..lastDayInMonth).map { "${it}일" },
                initialItem = "${selectedDay}일",
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedDay = item.dropLast(1).toIntOrNull() ?: 1
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
            )
        }
    }
}

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
    onItemSelected: (Int) -> Unit = {},
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
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            items = yearList,
            initialItem = "${initialYear}년",
            itemHeight = itemHeight,
            numberOfDisplayedItems = numberOfDisplayedItems,
            onItemSelected = { _, item ->
                selectedYear = item.dropLast(1).toIntOrNull() ?: initialYear
                onItemSelected(selectedYear)
            },
        )
    }
}

private fun getLastDayInMonth(year: Int, month: Int): Int {
    val isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear) 29 else 28
        else -> throw IllegalArgumentException("Invalid month: $month")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SusuDatePickerBottomSheetPreview() {
    SusuTheme {
        SusuDatePickerBottomSheet(
            maximumContainerHeight = 346.dp,
            onDismissRequest = { _, _, _ -> },
        )
    }
}
