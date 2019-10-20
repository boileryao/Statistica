package com.boileryao.statistica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_number.view.*

/**
 * Created by boileryao on 2019/10/19.
 */
class EditableRecyclerAdapter(
    private val items: List<Any>,
    private val actionListener: ActionListener
) : RecyclerView.Adapter<EditableRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_number, parent, false)
        return ViewHolder(itemView as ViewGroup)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textView.text = get(position)

        // On click listeners
        holder.itemView.setOnClickListener {
            actionListener.onItemClicked(position, get(position))
        }
        holder.itemView.ivCopy.setOnClickListener {
            actionListener.onItemCopied(get(position))
        }
        holder.itemView.ivDelete.setOnClickListener {
            actionListener.onItemRemoved(position, get(position))
        }
    }

    private fun get(position: Int) = items[position].toString()

    class ViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView)

    interface ActionListener {
        fun onItemClicked(index: Int, value: String)
        fun onItemCopied(value: String)
        fun onItemRemoved(index: Int, value: String)
    }

}
