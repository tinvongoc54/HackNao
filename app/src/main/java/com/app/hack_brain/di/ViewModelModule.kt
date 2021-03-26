package com.app.hack_brain.di

import com.app.hack_brain.ui.check.CheckFragViewModel
import com.app.hack_brain.ui.home.HomeFragViewModel
import com.app.hack_brain.ui.home.HomeViewModel
import com.app.hack_brain.ui.irregular.IrregularFragViewModel
import com.app.hack_brain.ui.short_story.ShortStoryFragViewModel
import com.app.hack_brain.ui.short_story.detail.DetailShortStoryFragViewModel
import com.app.hack_brain.ui.splash.SplashActViewModel
import com.app.hack_brain.ui.translate.TranslateFragViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */

val viewModelModule = module {
    viewModel<HomeViewModel>()
    viewModel<SplashActViewModel>()
    viewModel<HomeFragViewModel>()
    viewModel<ShortStoryFragViewModel>()
    viewModel<DetailShortStoryFragViewModel>()
    viewModel<IrregularFragViewModel>()
    viewModel<CheckFragViewModel>()
    viewModel<TranslateFragViewModel>()
}
