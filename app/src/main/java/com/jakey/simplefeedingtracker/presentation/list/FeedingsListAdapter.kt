package com.jakey.simplefeedingtracker.presentation.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.DataPoint
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.model.Header
import com.jakey.simplefeedingtracker.databinding.FeedingItemBinding
import com.jakey.simplefeedingtracker.databinding.HeaderViewHolderBinding
import java.util.*

class FeedingsListAdapter(
    private var data: List<DataPoint>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class FeedingViewHolder(val binding: FeedingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Feeding) {
            binding.tvTime.text = data.time
            binding.tvAmount.text = data.amount
            binding.tvNotes.text = data.note

            binding.feedingItem.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(data)
                binding.root.findNavController().navigate(action)
            }
        }
    }

    class HeaderViewHolder(private val binding: HeaderViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String, /*onClick: (String) -> Unit*/) {
            binding.textView.text = data

            binding.root.setOnClickListener {
                Toast.makeText(it.context, "header", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is Header -> R.layout.header_view_holder
            is Feeding -> R.layout.feeding_item
            else -> throw RuntimeException("unknown view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.header_view_holder -> {
                val binding = HeaderViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            R.layout.feeding_item -> {
                val binding =
                    FeedingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FeedingViewHolder(binding)
            }
            else -> throw RuntimeException("Unknown viewtype : $viewType")
        }
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingViewHolder {
//        val binding = FeedingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return FeedingViewHolder(binding)
//    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is HeaderViewHolder -> holder.onBind((data[position] as Header).day)

            is FeedingViewHolder -> holder.onBind((data[position] as Feeding))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

//    fun setData(feeding: List<Feeding>) {
//        this.data = feeding
//        notifyDataSetChanged()
//    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}
