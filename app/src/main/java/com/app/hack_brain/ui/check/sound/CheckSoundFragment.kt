package com.app.hack_brain.ui.check.sound

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckSoundBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.show
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.ui.check.dialog.FinishDialogFragment
import com.app.hack_brain.ui.check.eng_vie.AnswerAdapter
import com.app.hack_brain.ui.check.vie_eng.CheckVieEngFragment
import com.app.hack_brain.ui.home.HomeActivity
import timber.log.Timber

class CheckSoundFragment : BaseFragment<CheckSoundFragViewModel, FragmentCheckSoundBinding>(CheckSoundFragViewModel::class) {

    private val args: CheckSoundFragmentArgs by navArgs()
    private val wordList: MutableList<Word> = mutableListOf()
    private val checkedList: MutableList<Word> = mutableListOf()
    private var point = 0
    private lateinit var checkWord: Word

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckSoundBinding {
        return FragmentCheckSoundBinding.inflate(inflater)
    }

    override fun initialize() {
        checkWord = Word(0, "", "", "")
        wordList.addAll(args.unit.words)
        checkedList.addAll(wordList)
        Timber.i("Size: " + wordList.size)
        viewBinding.sbProgress.max = wordList.size
        initRecyclerAdapter()
        showQuestion()

        viewBinding.btnNext.setOnClickListener {
            checkedList.remove(checkWord)
            showQuestion()
        }
    }

    private fun initRecyclerAdapter() {
        viewBinding.rvAnswer.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AnswerAdapter(requireContext()) { word: Word, position: Int ->
                Timber.i("click ${word.word}")
                checkAnswer(word, position)
            }
        }
    }

    private fun showQuestion() {
        if (viewBinding.sbProgress.progress < viewBinding.sbProgress.max) {
            checkWord = checkedList.random()
            var random2: Word
            var random3: Word
            do {
                random2 = wordList.random()
                random3 = wordList.random()
                Timber.i("${checkWord.word} , ${random2.word} , ${random3.word}")
            } while (checkWord.id == random2.id || checkWord.id == random3.id || random2.id == random3.id)

            viewBinding.run {
                llResult.gone()
                btnNext.gone()
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

    private fun checkAnswer(word: Word, position: Int) {
        val result = word.id == checkWord.id
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
                checkWord.meanings
            )
            tvAnswer.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (result) R.color.dark_green_text else R.color.red
                )
            )
        }

        if (result) {
            point += 10
            Handler().postDelayed({
                checkedList.remove(checkWord)
                showQuestion()
            }, 1000)
        } else {
            viewBinding.btnNext.show()
        }

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
                        activity?.onBackPressed()
                    },
                    onClickAgain = {
                        checkedList.addAll(args.unit.words)
                        sbProgress.progress = 0
                        point = 0
                        (activity as? HomeActivity)?.setPoint(point)
                        showQuestion()
                    }
                ).show(childFragmentManager, CheckVieEngFragment::class.java.name)
            }
        }
    }
}