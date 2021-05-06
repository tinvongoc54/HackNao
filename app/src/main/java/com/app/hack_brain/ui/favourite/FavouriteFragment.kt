package com.app.hack_brain.ui.favourite

import android.content.DialogInterface
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentFavouriteBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.app.hack_brain.model.uimodel.Unit
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.ui.pronounce.PronounceAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber

class FavouriteFragment : BaseFragment<FavouriteFragViewModel, FragmentFavouriteBinding>(FavouriteFragViewModel::class) {

    private var mediaPlayer: MediaPlayer? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
    }

    override fun initialize() {
        val list = mutableListOf<Word>()
        list.add(Word(id = 1, word = "cup", phonetic = "", meanings = "cai ly, cai tach cai ly, cai tach cai ly, cai tach cai ly, cai tach cai ly, cai tach cai ly, cai tach"))
        list.add(Word(id = 2, word = "hello", phonetic = "", meanings = "Xin chao"))
        list.add(Word(id = 3, word = "sorry", phonetic = "sorry", meanings = "xin loi"))
        list.add(Word(id = 4, word = "cut", phonetic = "cut", meanings = "cat"))
        list.add(Word(id = 5, word = "cat", phonetic = "cat", meanings = "con meo"))
        list.add(Word(id = 6, word = "headphone", phonetic = "headphone", meanings = "tai nghe"))
        list.add(Word(id = 7, word = "clock", phonetic = "clock", meanings = "dong ho"))
        list.add(Word(id = 8, word = "die", phonetic = "die", meanings = "chet"))
        initFavouriteAdapter(list)

        val options = arrayOf("Anh - Việt", "Việt - Anh", "Âm thanh")

        viewBinding.btnCheck.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Chọn loại kiểm tra")
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> navigateToDetailCheckEngVieUnit(Unit(unit = "1", words = list))
                        1 -> navigateToDetailCheckVieEngUnit(Unit(unit = "1", words = list))
                        2 -> navigateToDetailCheckSoundUnit(Unit(unit = "1", words = list))
                    }
                }
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        (viewBinding.rvFavourite.adapter as? FavouriteAdapter)?.removeListener()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
    }

    private fun initFavouriteAdapter(list: List<Word>) {
        viewBinding.run {
            rvFavourite.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FavouriteAdapter(
                    onClickFavourite = {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Xoá yêu thích")
                            .setMessage("Bạn có muốn xoá từ này khỏi yêu thích không?")
                            .setNegativeButton("Huỷ", null)
                            .setPositiveButton("Xoá") { _, which ->
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    Timber.i("Da xoa")
                                }
                            }
                            .show()
                    },
                    onClickSound = {
                        openAudio("${it.word}.mp3")
                    }
                )
                with(adapter as FavouriteAdapter) {
                    replaceData(list.toMutableList())
                }
            }
        }
    }

    private fun navigateToDetailCheckEngVieUnit(unit: Unit) {
        val action = FavouriteFragmentDirections.actionFavouriteToCheckEngVieFragment(unit)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckVieEngUnit(unit: Unit) {
        val action = FavouriteFragmentDirections.actionFavouriteToCheckVieEngFragment(unit)
        navigateWithSlideAnim(action)
    }

    private fun navigateToDetailCheckSoundUnit(unit: Unit) {
        val action = FavouriteFragmentDirections.actionFavouriteToCheckSoundFragment(unit)
        navigateWithSlideAnim(action)
    }

    private fun openAudio(audio: String) {
        Timber.i(audio)
        mediaPlayer = MediaPlayer()
        try {
            val activity = activity ?: return
            val afd = activity.assets.openFd("sound/$audio")
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