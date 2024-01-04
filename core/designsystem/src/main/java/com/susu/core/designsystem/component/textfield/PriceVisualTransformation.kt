package com.susu.core.designsystem.component.textfield

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat

class PriceVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val amount = text.text

        if (amount.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val formatAmount = DecimalFormat("#,###").format(amount.toBigDecimal())

        return TransformedText(
            text = AnnotatedString("${formatAmount}원"),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 1) return offset

                    val entireCommaCount = if (amount.length % 3 == 0) amount.length / 3 - 1 else amount.length / 3
                    val sliceUntil = if (offset + entireCommaCount <= formatAmount.length) offset + entireCommaCount else formatAmount.length
                    val commaBeforeOffsetCount = formatAmount.substring(0 until sliceUntil).count { it == ',' }

                    return offset + commaBeforeOffsetCount
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return when (offset) {
                        in 0..1 -> offset
                        in 2 until formatAmount.length -> {
                            val entireCommaCount = if (amount.length % 3 == 0) amount.length / 3 - 1 else amount.length / 3
                            val sliceUntil = if (offset + entireCommaCount <= formatAmount.length) offset + entireCommaCount else formatAmount.length
                            val commaBeforeOffsetCount = formatAmount.substring(0 until sliceUntil).count { it == ',' }
                            offset - commaBeforeOffsetCount
                        }

                        else -> amount.length
                    }
                }
            },
        )
    }
}
