package com.jakey.simplefeedingtracker.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakey.simplefeedingtracker.R
import com.jakey.simplefeedingtracker.data.model.DataPoint
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.model.Header
import com.jakey.simplefeedingtracker.data.model.StickyHeader
import com.jakey.simplefeedingtracker.databinding.FeedingItemBinding
import com.jakey.simplefeedingtracker.databinding.HeaderViewHolderBinding
import com.jakey.simplefeedingtracker.databinding.StickyTopHeaderBinding
import com.jakey.simplefeedingtracker.presentation.list.ListFragmentDirections
import com.jakey.simplefeedingtracker.utils.Helper
import java.util.*

class FeedingsListAdapter(
    private var data: List<DataPoint> = emptyList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class FeedingViewHolder(private val binding: FeedingItemBinding) :
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
        fun onBind(data: Header /*onClick: (String) -> Unit*/) {
            binding.textView.text = "${data.day}  (${data.amount}) "

//            binding.root.setOnClickListener {
//                Toast.makeText(it.context, "${data.day}", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    class StickyHeaderViewHolder(
        private val binding: StickyTopHeaderBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StickyHeader) {
            binding.stickyHeaderTvLabel.text = "Time Since Last"
            binding.stickyHeaderTv.text = data.timeSinceLast
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is Header -> R.layout.header_view_holder
            is Feeding -> R.layout.feeding_item
            is StickyHeader -> R.layout.sticky_top_header
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
            R.layout.sticky_top_header -> {
                val binding =
                    StickyTopHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                StickyHeaderViewHolder(binding)
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
            is HeaderViewHolder -> holder.onBind((data[position] as Header))
            is StickyHeaderViewHolder -> holder.onBind(data[position] as StickyHeader)
            is FeedingViewHolder -> holder.onBind((data[position] as Feeding))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(feeding: List<Feeding>) {

        val dataList = mutableListOf<DataPoint>()
        val map: Map<String?, List<Feeding>> = feeding.groupBy {
            Helper.convertDay(it.timeStamp)
        }

        map.entries.forEach {
            val dayOfWeek = it.key
            val feedings = it.value
            dataList.add(
                Header(
                    day = dayOfWeek, amount = feedings.sumOf {
                        it.amount.toDouble()
                    }.toString()
                )
            )
            feedings.forEach { feeding ->
                dataList.add(feeding)
            }
        }
        dataList.add(0, StickyHeader(feeding[0].timeStamp.toString()))
        this.data = dataList
        notifyDataSetChanged()
    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}

