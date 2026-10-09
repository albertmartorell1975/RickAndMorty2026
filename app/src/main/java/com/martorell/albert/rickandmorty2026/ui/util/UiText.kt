package com.martorell.albert.rickandmorty2026.ui.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * A text ready to be consumed only by the UI
 * Its goal is to "transport" any either a dynamic or resource to compose without needing inject a Context into the ViewModel
 */
sealed interface UiText {

    data class DynamicString(val value: String) : UiText
    data class StringResource(@get:StringRes val resId: Int) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(resId)
    }

}