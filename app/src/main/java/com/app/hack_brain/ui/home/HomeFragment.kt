package com.app.hack_brain.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<HomeFragViewModel, FragmentHomeBinding>(HomeFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initialize() {
        initClickEvent()
    }

    private fun initClickEvent() {
        viewBinding.run {
            cvShortStory.setOnClickListener {
                navigateToShortStory()
            }
            cvCheck.setOnClickListener {
                navigateToCheck()
            }
            cvTranslate.setOnClickListener {
                navigateToTranslate()
            }
            cvIrregular.setOnClickListener {
                navigateToIrregular()
            }
            cvFavourite.setOnClickListener {
                navigateToFavourite()
            }
            cvTimer.setOnClickListener {
                navigateToTimer()
            }
            cvPronounce.setOnClickListener {
                navigateToPronounce()
            }
        }
    }

    private fun navigateToShortStory() =
        findNavController().navigate(R.id.actionHomeFragmentToShortStoryFragment)

    private fun navigateToCheck() =
        findNavController().navigate(R.id.actionHomeFragmentToCheckFragment)

    private fun navigateToTranslate() =
        findNavController().navigate(R.id.actionHomeFragmentToTranslateFragment)

    private fun navigateToIrregular() =
        findNavController().navigate(R.id.actionHomeFragmentToIrregularFragment)

    private fun navigateToFavourite() =
        findNavController().navigate(R.id.actionHomeFragmentToFavouriteFragment)

    private fun navigateToTimer() =
        findNavController().navigate(R.id.actionHomeFragmentToTimerFragment)

    private fun navigateToPronounce() =
        findNavController().navigate(R.id.actionHomeFragmentToPronounceFragment)
}