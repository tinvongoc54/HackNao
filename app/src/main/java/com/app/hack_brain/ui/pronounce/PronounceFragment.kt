package com.app.hack_brain.ui.pronounce

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentPronounceBinding
import com.app.hack_brain.model.uimodel.Pronounce
import com.google.gson.Gson
import timber.log.Timber

class PronounceFragment : BaseFragment<PronounceFragViewModel, FragmentPronounceBinding>(PronounceFragViewModel::class) {

    private var mediaPlayer: MediaPlayer? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPronounceBinding {
        return FragmentPronounceBinding.inflate(inflater)
    }

    override fun initialize() {
        viewBinding.run {
            rvPronounce.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = PronounceAdapter().apply {
                    onItemClick = {
                        openAudio("$it.mp3")
                    }
                }
                with(adapter as PronounceAdapter) {
                    replaceData(getPronounceList().toMutableList())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (viewBinding.rvPronounce.adapter as? PronounceAdapter)?.removeListener()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
    }

    private fun openAudio(audio: String) {
        mediaPlayer = MediaPlayer()
        try {
            val activity = activity ?: return
            val afd = activity.assets.openFd(audio)
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

    private fun getPronounceList(): List<Pronounce> {
        var json = ""
        try {
            val activity = activity ?: return emptyList()
            val inputStream = activity.assets.open("pronounce")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            json = String(buffer)
        } catch (ex: Exception) {

        }
        return Gson().fromJson(json, Array<Pronounce>::class.java).asList()
    }
}