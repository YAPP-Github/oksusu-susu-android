package com.susu.core.ui.extension

import java.text.DecimalFormat

fun Long.toMoneyFormat(): String {
    // DecimalFormat은 Thread Safe하지 않으므로 지역 변수로 사용함.
    return DecimalFormat("#,###").format(this)
}
