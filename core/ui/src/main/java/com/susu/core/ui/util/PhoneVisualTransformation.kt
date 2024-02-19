package com.susu.core.ui.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > 11) text.text.substring(0 until 11) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 2 || i == 6) out += "-"
        }

        return TransformedText(AnnotatedString(out), phoneOffsetTranslator)
    }

    private val phoneOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 6) return offset + 1
            if (offset <= 11) return offset + 2
            return 13
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 8) return offset - 1
            if (offset <= 13) return offset - 2
            return 11
        }
    }
}
