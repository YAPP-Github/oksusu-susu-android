package com.susu.feature.navigator.initialization

import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainContract.MainState, MainContract.MainEffect>(MainContract.MainState.Loading)
