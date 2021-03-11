package com.app.hack_brain.extension

import android.R
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/6/20.
 */

fun Fragment?.hideKeyboard() {
    (this?.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

val options = navOptions {
    anim {
        enter = R.anim.slide_out_right
        exit = R.anim.slide_in_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}

fun Fragment.navigateWithSlideAnim(action: NavDirections) {

//    findNavController(this).navigate(action, options)
    findNavController().navigate(action)
}

fun Fragment.navigateWithSlideAnim(actionId: Int) {
//    findNavController(this).navigate(actionId, null, options)
    findNavController().navigate(actionId)
}

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.removeNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)
