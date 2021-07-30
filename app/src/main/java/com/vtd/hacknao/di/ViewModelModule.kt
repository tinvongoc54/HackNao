package com.vtd.hacknao.di

import com.vtd.hacknao.ui.check.CheckFragViewModel
import com.vtd.hacknao.ui.check.dialog.FinishDialogFragViewModel
import com.vtd.hacknao.ui.check.eng_vie.CheckEngVieFragViewModel
import com.vtd.hacknao.ui.check.sound.CheckSoundFragViewModel
import com.vtd.hacknao.ui.check.vie_eng.CheckVieEngFragViewModel
import com.vtd.hacknao.ui.favourite.FavouriteFragViewModel
import com.vtd.hacknao.ui.home.HomeFragViewModel
import com.vtd.hacknao.ui.home.HomeViewModel
import com.vtd.hacknao.ui.irregular.IrregularFragViewModel
import com.vtd.hacknao.ui.pronounce.PronounceFragViewModel
import com.vtd.hacknao.ui.short_story.ShortStoryFragViewModel
import com.vtd.hacknao.ui.short_story.detail.DetailShortStoryFragViewModel
import com.vtd.hacknao.ui.splash.SplashActViewModel
import com.vtd.hacknao.ui.timer.TimerFragViewModel
import com.vtd.hacknao.ui.timer.dialog.ChooseTimerFragViewModel
import com.vtd.hacknao.ui.translate.TranslateFragViewModel
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
    viewModel<CheckEngVieFragViewModel>()
    viewModel<CheckVieEngFragViewModel>()
    viewModel<FavouriteFragViewModel>()
    viewModel<TimerFragViewModel>()
    viewModel<ChooseTimerFragViewModel>()
    viewModel<PronounceFragViewModel>()
    viewModel<FinishDialogFragViewModel>()
    viewModel<CheckSoundFragViewModel>()
}
