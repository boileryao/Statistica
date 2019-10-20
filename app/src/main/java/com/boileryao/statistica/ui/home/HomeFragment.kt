package com.boileryao.statistica.ui.home

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.boileryao.statistica.EditableRecyclerAdapter
import com.boileryao.statistica.R
import com.boileryao.statistica.math.rsd
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private var homeViewModel = HomeViewModel()
    private lateinit var recyclerAdapter: EditableRecyclerAdapter

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
    }

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
            recyclerAdapter.notifyItemInserted(homeViewModel.numbers.size - 1)
            content.etNumber.setText("")
        }
        content.btnAppend.setOnLongClickListener {
            val rsd = rsd(homeViewModel.numbers.toDoubleArray())
            Toast.makeText(context, rsd.toString(), Toast.LENGTH_LONG).show()
            true
        }
        return content
    }

    companion object {
        private const val TEXT_FLAG_SIGNED_DECIMAL = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

}
