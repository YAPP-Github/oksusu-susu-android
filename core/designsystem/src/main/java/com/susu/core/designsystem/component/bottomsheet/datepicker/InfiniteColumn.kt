package com.susu.core.designsystem.component.bottomsheet.datepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.SusuTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> InfiniteColumn(
    modifier: Modifier = Modifier,
    itemHeight: Dp = 48.dp,
    numberOfDisplayedItems: Int = 5,
    items: List<T>,
    initialItem: T,
    textStyle: TextStyle = SusuTheme.typography.title_xxs,
    selectedTextStyle: TextStyle = SusuTheme.typography.title_xxs,
    textColor: Color = Gray30,
    selectedTextColor: Color = Gray100,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> },
) {
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember { mutableStateOf(0) }
    var itemsState by remember { mutableStateOf(items) }

    LaunchedEffect(items) {
        var targetIndex = items.indexOf(initialItem)
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        itemsState = items
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(targetIndex - 2)
    }

    LazyColumn(
        modifier = modifier.height(itemHeight * numberOfDisplayedItems),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(
            lazyListState = scrollState,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = Int.MAX_VALUE,
            itemContent = { i ->
                val item = itemsState[i % itemsState.size]
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            val y = coordinates.positionInParent().y - itemHalfHeight
                            val parentHeight = coordinates.parentCoordinates?.size?.height ?: 0
                            val parentHalfHeight = parentHeight / 2f
                            val isSelected =
                                y > parentHalfHeight &&
                                    y < parentHalfHeight + 2 * itemHalfHeight
                            if (isSelected && lastSelectedIndex != i) {
                                onItemSelected(i % itemsState.size, item)
                                lastSelectedIndex = i
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item.toString(),
                        style = if (lastSelectedIndex == i) {
                            selectedTextStyle
                        } else {
                            textStyle
                        },
                        color = if (lastSelectedIndex == i) {
                            selectedTextColor
                        } else {
                            textColor
                        },
                    )
                }
            },
        )
    }
}
