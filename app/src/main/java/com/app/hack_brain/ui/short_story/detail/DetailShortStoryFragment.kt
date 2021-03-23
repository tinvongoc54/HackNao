package com.app.hack_brain.ui.short_story.detail

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.fragment.navArgs
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentDetailShortStoryBinding
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.fragment_detail_short_story.*
import timber.log.Timber
import java.lang.Exception

class DetailShortStoryFragment :
    BaseFragment<DetailShortStoryFragViewModel, FragmentDetailShortStoryBinding>(
        DetailShortStoryFragViewModel::class
    ) {

    private val args: DetailShortStoryFragmentArgs by navArgs()
    private lateinit var mediaPlayer: MediaPlayer

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailShortStoryBinding {
        return FragmentDetailShortStoryBinding.inflate(inflater)
    }

    override fun initialize() {
        tvContent.text = args.shortStory.content
        openAudio(args.shortStory.audio)

        activity?.findViewById<AppCompatImageView>(R.id.ivAudio)?.setOnClickListener {
            mediaPlayer.release()
            openAudio(args.shortStory.audio)
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    private fun openAudio(audio: String) {
        mediaPlayer = MediaPlayer()
        try {
            val activity = activity ?: return
            val afd = activity.assets.openFd(audio)
            mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mediaPlayer.prepare()
        } catch (ex: Exception) {
            Timber.i("error")
        }
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }
}