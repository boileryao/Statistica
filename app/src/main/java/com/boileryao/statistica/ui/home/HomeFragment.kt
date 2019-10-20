package com.boileryao.statistica.ui.home

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.boileryao.statistica.EditableRecyclerAdapter
import com.boileryao.statistica.R
import com.boileryao.statistica.math.average
import com.boileryao.statistica.math.rsd
import com.boileryao.statistica.math.sd
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private var homeViewModel = HomeViewModel()
    private lateinit var recyclerAdapter: EditableRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val content = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerAdapter = EditableRecyclerAdapter(
            homeViewModel.numbers,
            object : EditableRecyclerAdapter.ActionListener {
                override fun onItemClicked(index: Int, value: String) {
                    onItemCopied(value)
                }

                override fun onItemCopied(value: String) {
                    content.etNumber.setText(value)
                }

                override fun onItemRemoved(index: Int, value: String) {
                    homeViewModel.numbers.removeAt(index)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
        )
        content.rvNumbers.adapter = recyclerAdapter
        content.rvNumbers.layoutManager = LinearLayoutManager(context)
        content.rvNumbers.itemAnimator = DefaultItemAnimator()
        content.etNumber.inputType = TEXT_FLAG_SIGNED_DECIMAL
        content.btnAppend.setOnClickListener {
            val numberText = content.etNumber.text.toString()
            homeViewModel.numbers.add(numberText.toDoubleOrNull() ?: return@setOnClickListener)

            val lastItemPosition = homeViewModel.numbers.size - 1
            recyclerAdapter.notifyItemInserted(lastItemPosition)
            content.rvNumbers.scrollToPosition(lastItemPosition)

            content.etNumber.setText("")
            updateCalcResult(homeViewModel.numbers.toDoubleArray())
        }
        return content
    }

    private fun updateCalcResult(nums: DoubleArray) {
        val rsdInPercent = rsd(nums) * 100
        val sd = sd(nums)
        val avg = average(nums)
        val rsdResult = context?.getString(R.string.template_rsd_results, rsdInPercent, sd, avg)
        tvRsd.text = rsdResult
    }

    companion object {
        private const val TEXT_FLAG_SIGNED_DECIMAL = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

}
