package com.app.hack_brain.ui.check.sound

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.R
import com.app.hack_brain.common.Constant
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.databinding.FragmentCheckSoundBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.nullToBlank
import com.app.hack_brain.extension.show
import com.app.hack_brain.ui.check.dialog.FinishDialogFragment
import com.app.hack_brain.ui.check.eng_vie.AnswerAdapter
import com.app.hack_brain.ui.check.vie_eng.CheckVieEngFragment
import com.app.hack_brain.ui.home.HomeActivity
import timber.log.Timber

class CheckSoundFragment :
    BaseFragment<CheckSoundFragViewModel, FragmentCheckSoundBinding>(CheckSoundFragViewModel::class) {

    private val args: CheckSoundFragmentArgs by navArgs()
    private val wordList: MutableList<VocabularyEntity> = mutableListOf()
    private val checkedList: MutableList<VocabularyEntity> = mutableListOf()
    private var point = 0
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var checkWord: VocabularyEntity

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckSoundBinding {
        return FragmentCheckSoundBinding.inflate(inflater)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize() {
        viewModel.getVocabularyOfUnit(args.unit)

        viewBinding.run {
            sbProgress.setOnTouchListener { _, _ -> true }

            btnNext.setOnClickListener {
                checkedList.remove(checkWord)
                showQuestion()
            }

            ivPronounce.setOnClickListener {
                openAudio("${checkWord.word}")
            }
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            vocabularyListEvent.observe(viewLifecycleOwner, Observer {
                wordList.addAll(it.toMutableList())
                checkedList.addAll(wordList)
                viewBinding.sbProgress.max = wordList.size
                initRecyclerAdapter()
                showQuestion()
            })

            updateProgressSuccess.observe(viewLifecycleOwner, Observer {
                if (it) activity?.onBackPressed()
            })
        }
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
    }

    private fun initRecyclerAdapter() {
        viewBinding.rvAnswer.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AnswerAdapter(requireContext()) { word: VocabularyEntity, position: Int ->
                checkAnswer(word, position)
            }
        }
    }

    private fun showQuestion() {
        if (viewBinding.sbProgress.progress < viewBinding.sbProgress.max) {
            checkWord = checkedList.random()
            openAudio(checkWord.word.nullToBlank())
            var random2: VocabularyEntity
            var random3: VocabularyEntity
            do {
                random2 = wordList.random()
                random3 = wordList.random()
                Timber.i("${checkWord.word} , ${random2.word} , ${random3.word}")
            } while (checkWord.id == random2.id || checkWord.id == random3.id || random2.id == random3.id)

            viewBinding.run {
                llResult.gone()
                btnNext.gone()

                with(rvAnswer.adapter as AnswerAdapter) {
                    clearData()
                    val listAnswer = mutableListOf(checkWord, random2, random3)
                    listAnswer.shuffle()
                    replaceData(listAnswer)
                }
            }
        } else {
            Timber.i("finish")
        }
    }

    private fun checkAnswer(word: VocabularyEntity, position: Int) {
        val result = word.id == checkWord.id
        openAudio(if (result) Constant.CORRECT else Constant.FAILED)
        viewBinding.run {
            rvAnswer.findViewHolderForAdapterPosition(position)?.itemView?.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    if (result) R.drawable.background_correct_answer else R.drawable.background_wrong_answer
                )
            llResult.isEnabled = result
            llResult.show()
            ivCorrect.show(result)
            ivWrong.show(result.not())
            tvAnswer.text = String.format(
                "%s %s",
                if (result) getString(R.string.text_correct_answer) else getString(R.string.text_wrong_answer),
                checkWord.shortMean
            )
            tvAnswer.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (result) R.color.dark_green_text else R.color.red
                )
            )
        }

        if (result) point += 10
        viewBinding.btnNext.show()

        viewBinding.sbProgress.progress++
        (activity as? HomeActivity)?.setPoint(point)

        showDialogFinish()
    }

    private fun showDialogFinish() {
        viewBinding.run {
            if (sbProgress.progress == sbProgress.max) {
                FinishDialogFragment(
                    result = point / 10,
                    onClickNext = {
                        viewModel.updateProcess(
                            args.unit,
                            (point * 10 / Constant.AMOUNT_VOC_AN_UNIT)
                        )
                    },
                    onClickAgain = {
                        checkedList.addAll(wordList)
                        sbProgress.progress = 0
                        point = 0
                        (activity as? HomeActivity)?.setPoint(point)
                        showQuestion()
                    }
                ).show(childFragmentManager, CheckVieEngFragment::class.java.name)
            }
        }
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