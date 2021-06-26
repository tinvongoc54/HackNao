package com.app.hack_brain.ui.check.vie_eng

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.R
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentCheckVieEngBinding
import com.app.hack_brain.extension.gone
import com.app.hack_brain.extension.show
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.ui.check.dialog.FinishDialogFragment
import com.app.hack_brain.ui.home.HomeActivity
import kotlin.random.Random

class CheckVieEngFragment :
    BaseFragment<CheckVieEngFragViewModel, FragmentCheckVieEngBinding>(CheckVieEngFragViewModel::class) {

    private val args: CheckVieEngFragmentArgs by navArgs()
    private val wordList: MutableList<Word> = mutableListOf()
    private var point = 0
    private lateinit var checkWord: Word

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckVieEngBinding {
        return FragmentCheckVieEngBinding.inflate(inflater)
    }

    override fun initialize() {
//        wordList.addAll(args.unit.words)
        viewBinding.sbProgress.max = wordList.size
        initSuggestCharacter()
        showQuestion()

        viewBinding.run {
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

            }
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
        checkWord = wordList.random()
        viewBinding.run {
            btnCheck.show()
            btnNext.gone()
            llResult.gone()
            tvMeanings.text = checkWord.meanings
            with(rvCharacter.adapter as CharacterAdapter) {
                setPositionShowCharacter(Random.nextInt(0, checkWord.word.toMutableList().size - 1))
                replaceData(checkWord.word.toMutableList())
            }
        }
    }

    private fun checkAnswer() {
        val result = checkWord.word == viewBinding.edtWord.text.toString().trim()
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
            wordList.remove(checkWord)
            (activity as? HomeActivity)?.setPoint(point)
            sbProgress.progress++
            btnCheck.gone()
            btnNext.show()

            if (sbProgress.progress == sbProgress.max) {
                FinishDialogFragment(
                    result = point / 10,
                    onClickNext = {
                        activity?.onBackPressed()
                    },
                    onClickAgain = {
//                        wordList.addAll(args.unit.words)
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
}