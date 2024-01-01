package com.susu.core.designsystem.component.textfieldbutton

import androidx.compose.ui.graphics.Color
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Orange60

enum class TextFieldButtonColor(
    val buttonColor: TextButtonInnerButtonColor,
    val savedBackgroundColor: Color,
    val editBackgroundColor: Color,
    val editTextColor: Color,
    val savedTextColor: Color,
    val placeholderColor: Color,
) {
    Black(
        buttonColor = TextButtonInnerButtonColor.Black,
        savedBackgroundColor = Gray10,
        editBackgroundColor = Gray10,
        editTextColor = Gray100,
        savedTextColor = Gray100,
        placeholderColor = Gray30,
    ),
    Orange(
        buttonColor = TextButtonInnerButtonColor.Black,
        savedBackgroundColor = Orange60,
        editBackgroundColor = Gray10,
        editTextColor = Gray100,
        savedTextColor = Gray10,
        placeholderColor = Gray30,
    ),
}

enum class TextButtonInnerButtonColor(
    val activeContentColor: Color,
    val inactiveContentColor: Color,
    val activeBackgroundColor: Color,
    val inactiveBackgroundColor: Color,
    val rippleColor: Color,
) {
    Black(
        activeContentColor = Gray10,
        inactiveContentColor = Gray10,
        activeBackgroundColor = Gray100,
        inactiveBackgroundColor = Gray30,
        rippleColor = Gray10,
    ),
}
