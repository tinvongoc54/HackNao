package com.app.hack_brain.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hack_brain.common.base.BaseFragment
import com.app.hack_brain.databinding.FragmentFavouriteBinding
import com.app.hack_brain.extension.navigateWithSlideAnim
import com.app.hack_brain.model.uimodel.Unit
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.ui.check.CheckFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber

class FavouriteFragment : BaseFragment<FavouriteFragViewModel, FragmentFavouriteBinding>(FavouriteFragViewModel::class) {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
    }

    override fun initialize() {
        val list = mutableListOf<Word>()
        list.add(Word(id = 1, word = "cup", phonetic = "", meanings = "cai ly, cai tach cai ly, cai tach cai ly, cai tach cai ly, cai tach cai ly, cai tach cai ly, cai tach"))
        list.add(Word(id = 1, word = "hello", phonetic = "", meanings = "Xin chao"))
        list.add(Word(id = 1, word = "sorry", phonetic = "", meanings = "xin loi"))
        list.add(Word(id = 1, word = "cut", phonetic = "", meanings = "cat"))
        initFavouriteAdapter(list)

        val options = arrayOf("Anh - Việt", "Việt - Anh", "Âm thanh")

        viewBinding.btnCheck.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Chọn loại kiểm tra")
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> navigateToDetailCheckEngVieUnit(Unit())
                        1 -> navigateToDetailCheckVieEngUnit(Unit())
                        2 -> navigateToDetailCheckSoundUnit(Unit())
                    }
                }
                .show()
        }
    }

    private fun initFavouriteAdapter(list: List<Word>) {
        viewBinding.run {
            rvFavourite.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FavouriteAdapter()
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
        val action = FavouriteFragmentDirections.actionFavouriteToCheckEngVieFragment(unit)
        navigateWithSlideAnim(action)
    }
}