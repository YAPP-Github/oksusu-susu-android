package com.susu.core.android

import javax.inject.Qualifier

@Qualifier
annotation class Dispatcher(val susuDispatcher: SusuDispatchers)

enum class SusuDispatchers {
    IO,
}
