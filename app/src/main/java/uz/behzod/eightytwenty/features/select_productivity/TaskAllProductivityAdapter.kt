package uz.behzod.eightytwenty.features.select_productivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.behzod.eightytwenty.data.local.entities.TaskEntity
import uz.behzod.eightytwenty.databinding.HeaderProductivityItemBinding
import uz.behzod.eightytwenty.databinding.RowTaskProductivityItemBinding

class TaskAllProductivityAdapter :
    ListAdapter<TaskEntity, RecyclerView.ViewHolder>(
        diffUtil
    ) {

    companion object {
        private const val HEADER_ITEM = 0
        private const val ROW_ITEM = 1

        private val diffUtil = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(
                oldItem: TaskEntity,
                newItem: TaskEntity,
            ): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(
                oldItem: TaskEntity,
                newItem: TaskEntity,
            ): Boolean {
                return oldItem.timestamp == newItem.timestamp &&
                        oldItem.title == newItem.title
            }
        }
    }

    private inner class HeaderViewHolder(
        val binding: HeaderProductivityItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.tvHeader.text = title
        }
    }

    private inner class RowViewHolder(
        val binding: RowTaskProductivityItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskEntity) {
            binding.tvTaskTitle.text = task.title
            binding.tvTaskTimestamp.text = task.timestamp.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_ITEM -> {
                val binding = HeaderProductivityItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            ROW_ITEM -> {
                val binding = RowTaskProductivityItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                RowViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type of View Holder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_ITEM else ROW_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind("Все подряд")
            }
            is RowViewHolder -> {
                holder.bind(item)
            }
        }
    }

}