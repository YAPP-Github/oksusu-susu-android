package com.susu.core.designsystem.component.bottomsheet.datepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.component.bottomsheet.SusuBottomSheet
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.util.currentDate
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuDatePickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    yearRange: IntRange = 1930..2030,
    initialYear: Int? = null,
    initialMonth: Int? = null,
    initialDay: Int? = null,
    maximumContainerHeight: Dp,
    itemHeight: Dp = 48.dp,
    numberOfDisplayedItems: Int = 5,
    cornerRadius: Dp = 24.dp,
    onDismissRequest: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    val currentDate = remember { currentDate }

    var selectedYear by remember { mutableIntStateOf(initialYear ?: currentDate.year) }
    var selectedMonth by remember { mutableIntStateOf(initialMonth ?: currentDate.monthValue) }
    var selectedDay by remember { mutableIntStateOf(initialDay ?: currentDate.dayOfMonth) }
    var lastDayInMonth by remember { mutableIntStateOf(getLastDayInMonth(initialYear ?: currentDate.year, initialMonth ?: currentDate.monthValue)) }

    val yearList = yearRange.map { stringResource(R.string.word_year_format, it) }.toImmutableList()
    val monthList = (1..12).map { stringResource(R.string.word_month_format, it) }.toImmutableList()

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
                initialItem = stringResource(R.string.word_year_format, currentDate.year),
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedYear = item.dropLast(1).toIntOrNull() ?: currentDate.year
                    getLastDayInMonth(selectedYear, selectedMonth).run {
                        lastDayInMonth = this
                        if (selectedDay > lastDayInMonth) {
                            selectedDay = 1
                        }
                    }
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
                onItemClicked = { item ->
                    selectedYear = item.dropLast(1).toIntOrNull() ?: currentDate.year
                },
            )
            InfiniteColumn(
                modifier = Modifier.width(100.dp),
                items = monthList,
                initialItem = stringResource(R.string.word_month_format, currentDate.monthValue),
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedMonth = item.dropLast(1).toIntOrNull() ?: currentDate.monthValue
                    getLastDayInMonth(selectedYear, selectedMonth).run {
                        lastDayInMonth = this
                        if (selectedDay > lastDayInMonth) {
                            selectedDay = 1
                        }
                    }
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
                onItemClicked = { item ->
                    selectedMonth = item.dropLast(1).toIntOrNull() ?: currentDate.monthValue
                },
            )
            InfiniteColumn(
                modifier = Modifier.width(100.dp),
                items = (1..lastDayInMonth).map { stringResource(R.string.word_day_format, it) },
                initialItem = stringResource(R.string.word_day_format, selectedDay),
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedDay = item.dropLast(1).toIntOrNull() ?: 1
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
                onItemClicked = { item ->
                    selectedMonth = item.dropLast(1).toIntOrNull() ?: 1
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuLimitDatePickerBottomSheet(
    initialCriteriaYear: Int? = null,
    initialCriteriaMonth: Int? = null,
    initialCriteriaDay: Int? = null,
    initialYear: Int? = null,
    initialMonth: Int? = null,
    initialDay: Int? = null,
    afterDate: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(),
    yearRange: IntRange = 1930..2030,
    maximumContainerHeight: Dp,
    itemHeight: Dp = 48.dp,
    numberOfDisplayedItems: Int = 5,
    cornerRadius: Dp = 24.dp,
    onDismissRequest: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    val (criteriaYear, criteriaMonth, criteriaDay) = listOf(
        initialCriteriaYear ?: 2030,
        initialCriteriaMonth ?: 12,
        initialCriteriaDay ?: 31,
    )

    var selectedYear by remember {
        mutableIntStateOf(
            when {
                initialYear == null -> criteriaYear
                (afterDate && initialYear < criteriaYear) || (!afterDate && initialYear > criteriaYear) -> criteriaYear
                else -> initialYear
            },
        )
    }
    var selectedMonth by remember {
        mutableIntStateOf(
            when {
                initialMonth == null -> criteriaMonth
                (afterDate && initialYear != null && initialYear == criteriaYear && initialMonth < criteriaMonth) ||
                    (!afterDate && initialYear != null && initialYear == criteriaYear && initialMonth > criteriaMonth)
                -> criteriaMonth

                else -> initialMonth
            },
        )
    }
    var selectedDay by remember {
        mutableIntStateOf(
            when {
                initialDay == null -> criteriaDay
                (
                    afterDate && initialYear != null && initialYear == criteriaYear && initialMonth != null &&
                        initialMonth == criteriaMonth && initialDay < criteriaDay
                    ) ||
                    (
                        !afterDate && initialYear != null && initialYear == criteriaYear && initialMonth != null &&
                            initialMonth == criteriaMonth && initialDay > criteriaDay
                        )
                -> criteriaDay

                else -> initialDay
            },
        )
    }

    val yearList = if (afterDate) {
        (criteriaYear..yearRange.last).map { stringResource(R.string.word_year_format, it) }.toImmutableList()
    } else {
        (yearRange.first..criteriaYear).map { stringResource(R.string.word_year_format, it) }.toImmutableList()
    }
    val monthRange by remember(selectedYear) {
        derivedStateOf {
            if (selectedYear == criteriaYear) {
                if (afterDate) {
                    criteriaMonth..12
                } else {
                    1..criteriaMonth
                }
            } else {
                1..12
            }
        }
    }
    val dayRange by remember(selectedYear, selectedMonth) {
        derivedStateOf {
            if (selectedYear == criteriaYear && selectedMonth == criteriaMonth) {
                if (afterDate) {
                    if (selectedDay < criteriaDay) {
                        selectedDay = criteriaDay
                    }
                    criteriaDay..getLastDayInMonth(selectedYear, selectedMonth)
                } else {
                    if (selectedDay > criteriaDay) {
                        selectedDay = 1
                    }
                    1..criteriaDay
                }
            } else {
                val lastDay = getLastDayInMonth(selectedYear, selectedMonth)
                if (selectedDay > lastDay) {
                    selectedDay = 1
                }
                1..lastDay
            }
        }
    }

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
                initialItem = stringResource(R.string.word_year_format, selectedYear),
                itemHeight = itemHeight,
                numberOfDisplayedItems = numberOfDisplayedItems,
                onItemSelected = { _, item ->
                    selectedYear = item.dropLast(1).toIntOrNull() ?: criteriaYear
                    onItemSelected(selectedYear, selectedMonth, selectedDay)
                },
                onItemClicked = { item ->
                    selectedYear = item.dropLast(1).toIntOrNull() ?: criteriaYear
                },
            )
            if (monthRange.count() > 1) {
                InfiniteColumn(
                    modifier = Modifier.width(100.dp),
                    items = monthRange.map { stringResource(id = R.string.word_month_format, it) },
                    initialItem = stringResource(R.string.word_month_format, selectedMonth),
                    itemHeight = itemHeight,
                    numberOfDisplayedItems = numberOfDisplayedItems,
                    onItemSelected = { _, item ->
                        selectedMonth = item.dropLast(1).toIntOrNull() ?: criteriaMonth
                        onItemSelected(selectedYear, selectedMonth, selectedDay)
                    },
                    onItemClicked = { item ->
                        selectedMonth = item.dropLast(1).toIntOrNull() ?: criteriaMonth
                    },
                )
            } else {
                selectedMonth = criteriaMonth
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight(),
                ) {
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.word_month_format, selectedMonth),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray100,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            if (dayRange.count() > 1) {
                InfiniteColumn(
                    modifier = Modifier.width(100.dp),
                    items = dayRange.map { stringResource(R.string.word_day_format, it) },
                    initialItem = stringResource(R.string.word_day_format, selectedDay),
                    itemHeight = itemHeight,
                    numberOfDisplayedItems = numberOfDisplayedItems,
                    onItemSelected = { _, item ->
                        selectedDay = item.dropLast(1).toIntOrNull() ?: 1
                        onItemSelected(selectedYear, selectedMonth, selectedDay)
                    },
                    onItemClicked = { item ->
                        selectedDay = item.dropLast(1).toIntOrNull() ?: 1
                    },
                )
            } else {
                selectedDay = criteriaDay
                onItemSelected(selectedYear, selectedMonth, selectedDay)
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight(),
                ) {
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.word_day_format, selectedDay),
                        style = SusuTheme.typography.title_xxs,
                        color = Gray100,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuYearPickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    yearRange: IntRange = 1930..2030,
    reverseItemOrder: Boolean = false,
    initialYear: Int? = null,
    maximumContainerHeight: Dp,
    itemHeight: Dp = 48.dp,
    numberOfDisplayedItems: Int = 5,
    cornerRadius: Dp = 24.dp,
    onDismissRequest: (Int) -> Unit = {},
    onItemSelected: (Int) -> Unit = {},
    onItemClicked: (Int) -> Unit = {},
) {
    val currentYear = remember { LocalDate.now().year }
    var selectedYear by remember { mutableIntStateOf(initialYear ?: currentYear) }
    val yearList = if (reverseItemOrder) {
        (yearRange.reversed()
            .map { stringResource(id = R.string.word_year_format, it) } +
            listOf(stringResource(R.string.word_not_select))).toImmutableList()
    } else {
        (yearRange.map { stringResource(id = R.string.word_year_format, it) } +
            listOf(stringResource(R.string.word_not_select))).toImmutableList()
    }

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
            initialItem = stringResource(id = R.string.word_year_format, currentYear),
            itemHeight = itemHeight,
            numberOfDisplayedItems = numberOfDisplayedItems,
            onItemSelected = { _, item ->
                selectedYear = item.dropLast(1).toIntOrNull() ?: 0
                onItemSelected(selectedYear)
            },
            onItemClicked = { onItemClicked(it.dropLast(1).toIntOrNull() ?: 0) },
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
fun SusuLimitDatePickerBottomSheetPreview() {
    SusuTheme {
        SusuLimitDatePickerBottomSheet(
            initialCriteriaYear = 2024,
            initialCriteriaMonth = 2,
            initialCriteriaDay = 16,
            initialYear = 2024,
            initialMonth = 2,
            initialDay = 20,
            afterDate = true,
            maximumContainerHeight = 346.dp,
            onDismissRequest = { _, _, _ -> },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SusuYearPickerBottomSheetPreview() {
    SusuTheme {
        SusuYearPickerBottomSheet(maximumContainerHeight = 300.dp)
    }
}
