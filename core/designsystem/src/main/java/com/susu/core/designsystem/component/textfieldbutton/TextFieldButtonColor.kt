package com.susu.core.designsystem.component.textfieldbutton

import androidx.compose.ui.graphics.Color
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange10
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.Orange60

enum class TextFieldButtonColor(
    val buttonColor: TextButtonInnerButtonColor,
    val savedBackgroundColor: Color,
    val editBackgroundColor: Color,
    val unFocusedBackgroundColor: Color,
    val unFocusedTextColor: Color,
    val editTextColor: Color,
    val savedTextColor: Color,
    val placeholderColor: Color,
) {
    Black(
        buttonColor = TextButtonInnerButtonColor.Black,
        savedBackgroundColor = Gray10,
        editBackgroundColor = Gray10,
        unFocusedBackgroundColor = Gray10,
        editTextColor = Gray100,
        savedTextColor = Gray100,
        placeholderColor = Gray30,
        unFocusedTextColor = Gray30,
    ),
    Orange(
        buttonColor = TextButtonInnerButtonColor.Black,
        savedBackgroundColor = Orange60,
        editBackgroundColor = Gray10,
        unFocusedBackgroundColor = Orange20,
        editTextColor = Gray100,
        savedTextColor = Gray10,
        placeholderColor = Gray30,
        unFocusedTextColor = Gray10,
    ),
    Gray(
        buttonColor = TextButtonInnerButtonColor.Gray,
        savedBackgroundColor = Orange10,
        editBackgroundColor = Gray15,
        unFocusedBackgroundColor = Gray15,
        unFocusedTextColor = Gray50,
        editTextColor = Gray100,
        savedTextColor = Gray100,
        placeholderColor = Gray40,
    ),
}

enum class TextButtonInnerButtonColor(
    val activeContentColor: Color,
    val inactiveContentColor: Color,
    val unFocusedContentColor: Color,
    val activeBackgroundColor: Color,
    val inactiveBackgroundColor: Color,
    val unFocusedBackgroundColor: Color,
    val activeClearIconColor: Color,
    val inactiveClearIconColor: Color,
    val rippleColor: Color,
) {
    Black(
        activeContentColor = Gray10,
        inactiveContentColor = Gray10,
        unFocusedContentColor = Gray10,
        activeBackgroundColor = Gray100,
        inactiveBackgroundColor = Gray40,
        unFocusedBackgroundColor = Gray40,
        activeClearIconColor = Gray30,
        inactiveClearIconColor = Gray30,
        rippleColor = Gray10,
    ),
    Gray(
        activeContentColor = Gray10,
        inactiveContentColor = Gray10,
        unFocusedContentColor = Gray10,
        activeBackgroundColor = Gray100,
        inactiveBackgroundColor = Gray50,
        unFocusedBackgroundColor = Gray50,
        activeClearIconColor = Gray30,
        inactiveClearIconColor = Gray40,
        rippleColor = Gray10,
    ),
}
