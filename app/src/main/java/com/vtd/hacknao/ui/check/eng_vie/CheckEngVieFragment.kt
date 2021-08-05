package com.vtd.hacknao.ui.check.eng_vie

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
import com.vtd.hacknao.databinding.FragmentCheckEngVieBinding
import com.vtd.hacknao.extension.*
import com.vtd.hacknao.model.eventBus.UpdateTargetEvent
import com.vtd.hacknao.ui.check.dialog.FinishDialogFragment
import com.vtd.hacknao.ui.check.vie_eng.CheckVieEngFragment
import com.vtd.hacknao.ui.home.HomeActivity
import org.greenrobot.eventbus.EventBus
import timber.log.Timber


class CheckEngVieFragment :
    BaseFragment<CheckEngVieFragViewModel, FragmentCheckEngVieBinding>(CheckEngVieFragViewModel::class) {

    private val args: CheckEngVieFragmentArgs by navArgs()
    private val wordList: MutableList<VocabularyEntity> = mutableListOf()
    private val checkedList: MutableList<VocabularyEntity> = mutableListOf()
    private val tempCheckedList: MutableList<VocabularyEntity> = mutableListOf()
    private var point = 0
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var checkWord: VocabularyEntity

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckEngVieBinding {
        return FragmentCheckEngVieBinding.inflate(inflater)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize() {
        viewModel.getVocabularyOfUnit(if (args.unit != 0) args.unit else 1)

        viewBinding.run {
            sbProgress.setOnTouchListener { _, _ -> true }

            btnNext.setOnClickListener {
                checkedList.remove(checkWord)
                (viewBinding.rvAnswer.adapter as AnswerAdapter).setIsClickableItem(true)
                showQuestion()
            }

            ivPronounce.setOnClickListener {
                openAudio("${checkWord.word}")
            }

            cbFavourite.setOnClickListener {
                it.preventDoubleClick()
                viewModel.setFavouriteVoc(checkWord.id, !checkWord.isFavourite.isTrue())
            }
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            vocabularyListEvent.observe(viewLifecycleOwner, Observer {
                initData(it.toMutableList())
            })

            updateProgressEvent.observe(viewLifecycleOwner, Observer {
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
        initRecyclerAdapter()
        showQuestion()
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
                cbFavourite.isChecked = checkWord.isFavourite.isTrue()
                tvWord.text = checkWord.word
                tvSpelling.text = checkWord.phonetic
                Timber.i("check word: ${checkWord.word}")

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
                    numberQuestion = tempCheckedList.size,
                    onClickNext = {
                        if (args.unit != 0) {
                            viewModel.updateProcess(
                                args.unit,
                                (point * 10 / Constant.AMOUNT_VOC_AN_UNIT)
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
                        (activity as? HomeActivity)?.setPoint(point)
                        showQuestion()
                    }
                ).show(childFragmentManager, CheckVieEngFragment::class.java.name)
            }
        }
    }

    private fun openAudio(audio: String) {
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