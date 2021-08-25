package dev.sanskar.frame.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sanskar.frame.databinding.TasksListItemBinding
import dev.sanskar.frame.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class TasksListAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<TasksListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TasksListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TasksListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = viewModel.pendingTasks.value?.get(position)
        holder.binding.textViewTaskItem.text = task?.item
        holder.binding.textViewTaskTimestamp.text = SimpleDateFormat("hh:mm a, dd/MM/yy", Locale.ENGLISH).format(task?.timestamp)

        holder.binding.checkboxTask.setOnClickListener {
            viewModel.removeTask(position)
            notifyItemRemoved(position)
            notifyItemRangeRemoved(position, viewModel.pendingTasks.value?.size!!)
        }
    }

    override fun getItemCount() = viewModel.pendingTasks.value?.size!!
}