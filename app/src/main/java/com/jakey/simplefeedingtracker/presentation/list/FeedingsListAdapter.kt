package com.jakey.simplefeedingtracker.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.databinding.FeedingItemBinding
import java.util.*

class FeedingsListAdapter: RecyclerView.Adapter<FeedingsListAdapter.FeedingViewHolder>() {

    private var feedingList = emptyList<Feeding>()

    class FeedingViewHolder(val binding: FeedingItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingViewHolder {
        val binding = FeedingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedingViewHolder, position: Int) {
        val currentItem = feedingList[position]
        holder.apply {
            binding.tvTime.text = currentItem.time
            binding.tvAmount.text = currentItem.amount + " oz"
            binding.tvNotes.text = currentItem.note

            binding.feedingItem.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return feedingList.size
    }

    fun setData(feeding: List<Feeding>) {
        this.feedingList = feeding
        notifyDataSetChanged()
    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }

//    var sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
//    var c: Calendar = Calendar.getInstance()
//    var date: String = sdf.format(c.getTime())
}