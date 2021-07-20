package com.app.hack_brain.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.BuildConfig
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentHomeBinding
import com.app.hack_brain.ui.home.adapter.EverydayVocAdapter
import com.app.hack_brain.ui.home.adapter.TargetAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.gson.Gson
import timber.log.Timber


class HomeFragment :
    BaseFragment<HomeFragViewModel, FragmentHomeBinding>(HomeFragViewModel::class) {

    private lateinit var reviewManager: ReviewManager
    private var reviewInfo: ReviewInfo? = null
    private var unitNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        createReviewApp()
        viewModel.getTargetUnit()
        viewModel.getRandomVoc()
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun onStop() {
        super.onStop()
        exitTransition = MaterialFadeThrough()
    }

    @SuppressLint("IdleBatteryChargingConstraints")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun initialize() {
        enterTransition = MaterialFadeThrough()
        initClickEvent()
        setupRVVocabulary()
        setupRVTarget()
        viewBinding.tvChooseTarget.text = String.format("%s Day / Unit", viewModel.getUnitNumber())
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            randomVoc.observe(viewLifecycleOwner, Observer {
                (viewBinding.rvEverydayVocabulary.adapter as EverydayVocAdapter).replaceData(it.toMutableList())
            })
            targetList.observe(viewLifecycleOwner, Observer {
                (viewBinding.rvTarget.adapter as TargetAdapter).replaceData(it.toMutableList())
            })
            target.observe(viewLifecycleOwner, Observer {
                Timber.i("target: " + Gson().toJson(it))
                changeTarget(it.id ?: 1, unitNumber)
            })
        }
    }

    @SuppressLint("IdleBatteryChargingConstraints")
    @RequiresApi(Build.VERSION_CODES.M)
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
            btnShare.setOnClickListener {
                shareApp()
            }
            btnReview.setOnClickListener {
//                reviewApp()
            }
            tvChooseTarget.setOnClickListener {
                chooseTargetForDay()
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
            shareMessage =
                "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
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

    private fun setupRVVocabulary() {
        viewBinding.rvEverydayVocabulary.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = EverydayVocAdapter()
        }
    }

    private fun setupRVTarget() {
        viewBinding.rvTarget.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = TargetAdapter()
        }
    }

    private fun chooseTargetForDay() {
        val options = resources.getStringArray(R.array.unit_a_day)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chọn số unit")
            .setSingleChoiceItems(options, 0) { _, which ->
                unitNumber = options[which].toInt()
            }
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                viewBinding.tvChooseTarget.text = String.format("%s Unit / Day", unitNumber)
                viewModel.setUnitNumber(unitNumber)
                viewModel.getCurrentTarget()
            }
            .show()
    }
}