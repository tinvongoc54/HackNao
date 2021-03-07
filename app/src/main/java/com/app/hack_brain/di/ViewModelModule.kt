package com.app.hack_brain.di

import com.app.hack_brain.ui.home.HomeViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */

val viewModelModule = module {
    viewModel<HomeViewModel>()
}
