package com.vtd.hacknao.ui.favourite

import android.content.DialogInterface
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vtd.hacknao.R
import com.vtd.hacknao.common.base.BaseFragment
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.databinding.FragmentFavouriteBinding
import com.vtd.hacknao.extension.navigateWithSlideAnim
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialSharedAxis
import com.vtd.hacknao.extension.gone
import timber.log.Timber

class FavouriteFragment : BaseFragment<FavouriteFragViewModel, FragmentFavouriteBinding>(FavouriteFragViewModel::class) {

    private var mediaPlayer: MediaPlayer? = null
    private var wordList: MutableList<VocabularyEntity> = mutableListOf()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
    }

    override fun initialize() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        initFavouriteAdapter()
        viewModel.getFavouriteVocList()

        viewBinding.btnCheck.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Chọn loại kiểm tra")
                .setItems(resources.getStringArray(R.array.check_option)) { _, which ->
                    when (which) {
                        0 -> navigateToDetailCheckEngVieUnit(wordList.toTypedArray())
                        1 -> navigateToDetailCheckVieEngUnit(wordList.toTypedArray())
                        2 -> navigateToDetailCheckSoundUnit(wordList.toTypedArray())
                    }
                }
                .show()
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            favouriteVocEvent.observe(viewLifecycleOwner, Observer {
                wordList.clear()
                wordList.addAll(it.toMutableList())
                viewBinding.tvResult.gone(it.isNotEmpty())
                viewBinding.btnCheck.gone(it.isEmpty())
                (viewBinding.rvFavourite.adapter as FavouriteAdapter).replaceData(it.toMutableList())
            })
        }
    }

    override fun onStop() {
        super.onStop()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        (viewBinding.rvFavourite.adapter as? FavouriteAdapter)?.removeListener()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
    }

    private fun initFavouriteAdapter() {
        viewBinding.run {
            rvFavourite.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FavouriteAdapter(
                    context = requireContext(),
                    onClickFavourite = { id, position ->
                        onClickFavourite(id, position)
                    },
                    onClickSound = {
                        openAudio(it)
                    }
                )
            }
        }
    }

    private fun onClickFavourite(id: Int, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xoá yêu thích")
            .setMessage("Bạn có muốn xoá từ này khỏi yêu thích không?")
            .setNegativeButton("Huỷ", null)
            .setPositiveButton("Xoá") { _, which ->
                Timber.i("position: $position")
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    viewModel.setUnFavouriteVoc(id)
                    with(viewBinding.rvFavourite.adapter as FavouriteAdapter) {
                        removeItem(position, isNotifyAll = true)
                        viewBinding.tvResult.gone(getData().isNotEmpty())
                        viewBinding.btnCheck.gone(getData().isEmpty())
                    }
                }
            }
            .show()
    }

    private fun navigateToDetailCheckEngVieUnit(favouriteList: Array<VocabularyEntity>) {
        val action = FavouriteFragmentDirections.actionFavouriteToCheckEngVieFragment(0, favouriteList)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckVieEngUnit(favouriteList: Array<VocabularyEntity>) {
        val action = FavouriteFragmentDirections.actionFavouriteToCheckVieEngFragment(0, favouriteList)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckSoundUnit(favouriteList: Array<VocabularyEntity>) {
        val action = FavouriteFragmentDirections.actionFavouriteToCheckSoundFragment(0, favouriteList)
        navigateWithSlideAnim(action)
    }

    private fun openAudio(audio: String) {
        Timber.i(audio)
        mediaPlayer = MediaPlayer()
        try {
            val activity = activity ?: return
            val afd = activity.assets.openFd("sound/$audio.mp3")
            mediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mediaPlayer?.prepare()
        } catch (ex: Exception) {
            Timber.i("error")
        }
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            it.reset()
        }
    }
}