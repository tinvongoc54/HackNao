package com.app.hack_brain.ui.check.eng_vie

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckEngVieBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.show
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.ui.home.HomeActivity
import timber.log.Timber
import kotlin.random.Random

class CheckEngVieFragment :
    BaseFragment<CheckEngVieFragViewModel, FragmentCheckEngVieBinding>(CheckEngVieFragViewModel::class) {

    private val args: CheckEngVieFragmentArgs by navArgs()
    private val wordList: MutableList<Word> = mutableListOf()
    private val checkedList: MutableList<Word> = mutableListOf()
    private var point = 0
    private lateinit var checkWord: Word

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckEngVieBinding {
        return FragmentCheckEngVieBinding.inflate(inflater)
    }

    override fun initialize() {
        checkWord = Word(0, "", "", "")
        wordList.addAll(args.unit.words)
        checkedList.addAll(wordList)
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
            checkWord = if (checkedList.size == 1) checkedList[0] else checkedList[Random.nextInt(
                0,
                checkedList.size - 1
            )]
            var random2: Word
            var random3: Word
            do {
                random2 = wordList[Random.nextInt(0, wordList.size - 1)]
                random3 = wordList[Random.nextInt(0, wordList.size - 1)]
                Timber.i("${checkWord.word} , ${random2.word} , ${random3.word}")
            } while (checkWord.id == random2.id || checkWord.id == random3.id || random2.id == random3.id)

            viewBinding.run {
                llResult.gone()
                btnNext.gone()
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
            tvAnswer.text = if (result) getString(R.string.text_correct_answer) else String.format(
                "%s %s",
                getString(R.string.text_wrong_answer),
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
    }
}