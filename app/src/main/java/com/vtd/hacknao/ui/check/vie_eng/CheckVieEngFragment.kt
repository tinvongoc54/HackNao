package com.vtd.hacknao.ui.check.vie_eng

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vtd.hacknao.R
import com.vtd.hacknao.common.Constant
import com.vtd.hacknao.common.base.BaseFragment
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.databinding.FragmentCheckVieEngBinding
import com.vtd.hacknao.extension.*
import com.vtd.hacknao.model.eventBus.UpdateTargetEvent
import com.vtd.hacknao.ui.check.dialog.FinishDialogFragment
import com.vtd.hacknao.ui.home.HomeActivity
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import kotlin.random.Random

class CheckVieEngFragment :
    BaseFragment<CheckVieEngFragViewModel, FragmentCheckVieEngBinding>(CheckVieEngFragViewModel::class) {

    private val args: CheckVieEngFragmentArgs by navArgs()
    private val wordList: MutableList<VocabularyEntity> = mutableListOf()
    private val checkedList: MutableList<VocabularyEntity> = mutableListOf()
    private val tempCheckedList: MutableList<VocabularyEntity> = mutableListOf()
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
        viewModel.getVocabularyOfUnit(if (args.unit != 0) args.unit else 1)

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
                initData(it.toMutableList())
            })

            updateProgressSuccess.observe(viewLifecycleOwner, Observer {
                if (it) {
                    EventBus.getDefault().post(UpdateTargetEvent(unit = args.unit))
                    activity?.onBackPressed()
                }
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

    private fun initData(vocList: MutableList<VocabularyEntity>) {
        wordList.clear()
        wordList.addAll(vocList)
        checkedList.addAll(if (args.unit != 0) wordList else args.favouriteList?.toMutableList() ?: mutableListOf())
        tempCheckedList.addAll(checkedList)
        viewBinding.sbProgress.max = checkedList.size
        initSuggestCharacter()
        showQuestion()
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
                    numberQuestion = tempCheckedList.size,
                    onClickNext = {
                        if (args.unit != 0) {
                            viewModel.updateProcess(
                                args.unit,
                                point * 10 / Constant.AMOUNT_VOC_AN_UNIT
                            )
                        } else {
                            activity?.onBackPressed()
                        }
                    },
                    onClickAgain = {
                        checkedList.clear()
                        checkedList.addAll(tempCheckedList)
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