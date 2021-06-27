package com.app.hack_brain.ui.check.vie_eng

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
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
import com.app.hack_brain.databinding.FragmentCheckVieEngBinding
import com.app.hack_brain.extension.*
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.ui.check.dialog.FinishDialogFragment
import com.app.hack_brain.ui.home.HomeActivity
import timber.log.Timber
import kotlin.random.Random

class CheckVieEngFragment :
    BaseFragment<CheckVieEngFragViewModel, FragmentCheckVieEngBinding>(CheckVieEngFragViewModel::class) {

    private val args: CheckVieEngFragmentArgs by navArgs()
    private val wordList: MutableList<VocabularyEntity> = mutableListOf()
    private val checkedList: MutableList<VocabularyEntity> = mutableListOf()
    private var point = 0
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var checkWord: VocabularyEntity

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckVieEngBinding {
        return FragmentCheckVieEngBinding.inflate(inflater)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize() {
        viewModel.getVocabularyOfUnit(args.unit)

        viewBinding.run {
            sbProgress.setOnTouchListener { _, _ -> true }

            btnCheck.setOnClickListener {
                if (!viewBinding.edtWord.text.isNullOrBlank()) checkAnswer()
            }

            btnNext.setOnClickListener {
                if (sbProgress.progress < sbProgress.max) {
                    edtWord.setText("")
                    showQuestion()
                }
            }

            ivAudio.setOnClickListener {
                openAudio(checkWord.word.nullToBlank())
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
                initSuggestCharacter()
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

    private fun initSuggestCharacter() {
        viewBinding.rvCharacter.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = CharacterAdapter()
        }
    }

    private fun showQuestion() {
        showKeyboard()
        checkWord = checkedList.random()
        viewBinding.run {
            edtWord.requestFocus()
            btnCheck.show()
            btnNext.gone()
            llResult.gone()
            tvMeanings.text = checkWord.shortMean
            with(rvCharacter.adapter as CharacterAdapter) {
                setPositionShowCharacter(
                    Random.nextInt(
                        0,
                        checkWord.word.nullToBlank().toMutableList().size
                    )
                )
                replaceData(checkWord.word.nullToBlank().toMutableList())
            }
        }
    }

    private fun checkAnswer() {
        hideKeyboard()
        val result = checkWord.word.nullToBlank()
            .equals(viewBinding.edtWord.text.toString().trim(), ignoreCase = true)
        openAudio(if (result) Constant.CORRECT else Constant.FAILED)
        viewBinding.run {
            llResult.isEnabled = result
            llResult.show()
            ivCorrect.show(result)
            ivWrong.show(result.not())
            tvAnswer.text = String.format(
                "%s %s\n%s",
                if (result) getString(R.string.text_correct_answer) else getString(R.string.text_wrong_answer),
                checkWord.word,
                checkWord.phonetic
            )
            tvAnswer.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (result) R.color.dark_green_text else R.color.red
                )
            )

            if (result) point += 10
            checkedList.remove(checkWord)
            (activity as? HomeActivity)?.setPoint(point)
            sbProgress.progress++
            btnCheck.gone()
            btnNext.show()

            if (sbProgress.progress == sbProgress.max) {
                FinishDialogFragment(
                    result = point / 10,
                    onClickNext = {
                        viewModel.updateProcess(args.unit, point * 10 / Constant.AMOUNT_VOC_AN_UNIT)
                    },
                    onClickAgain = {
                        checkedList.addAll(wordList)
                        sbProgress.progress = 0
                        point = 0
                        edtWord.setText("")
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