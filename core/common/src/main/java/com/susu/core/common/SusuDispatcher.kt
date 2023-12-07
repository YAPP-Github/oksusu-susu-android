package com.susu.core.common

import javax.inject.Qualifier

@Qualifier
annotation class Dispatcher(val susuDispatcher: SusuDispatchers)

enum class SusuDispatchers {
    IO,
}
