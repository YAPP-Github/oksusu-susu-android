package com.susu.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.shadow.susuShadow
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit = {},
    cornerRadius: Dp = 24.dp,
    containerHeight: Dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        scrimColor = Color.Transparent,
        containerColor = Color.Transparent,
        dragHandle = null,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(containerHeight),
        ) {
            Column(
                modifier = Modifier
                    .susuShadow(
                        color = Gray30,
                        spread = 0.1.dp,
                        borderRadius = cornerRadius,
                        blurRadius = 8.dp,
                    )
                    .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
                    .fillMaxWidth()
                    .height(containerHeight - 5.dp)
                    .background(Gray10)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(
                    modifier = Modifier
                        .size(
                            width = 56.dp,
                            height = 6.dp,
                        )
                        .clip(RoundedCornerShape(100.dp))
                        .background(Gray20),
                )

                Spacer(modifier = Modifier.size(16.dp))

                content()
            }
        }
    }
}
