package com.app.hack_brain.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.hack_brain.BuildConfig
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentHomeBinding
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import timber.log.Timber


class HomeFragment :
    BaseFragment<HomeFragViewModel, FragmentHomeBinding>(HomeFragViewModel::class) {

    private lateinit var reviewManager: ReviewManager
    private var reviewInfo: ReviewInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        createReviewApp()
    }

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
            cvShare.setOnClickListener {
                shareApp()
            }
            cvReview.setOnClickListener {
//                reviewApp()
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

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hack Nao")
            var shareMessage = "\nHack Não 3000 từ tiếng anh thông dụng!\n\n"
            shareMessage = "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share with"))
        } catch (e: Exception) {
            Timber.i("Share error: ${e.toString()}")
        }
    }

    private fun createReviewApp() {
        reviewManager = ReviewManagerFactory.create(requireContext())
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener {
            if (it.isSuccessful) {
                reviewInfo = it.result
                val flow = reviewManager.launchReviewFlow(requireActivity(), reviewInfo!!)
                flow.addOnCompleteListener {
                    Timber.i("Thank you")
                }
            } else {
                Timber.i("Fail")
            }
        }
    }

    private fun reviewApp() {
        if (reviewInfo != null) {
            val flow = reviewManager.launchReviewFlow(requireActivity(), reviewInfo!!)
            flow.addOnCompleteListener {
                Timber.i("Success")
            }
        } else {
            Timber.i("Null")
        }
    }
}