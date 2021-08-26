package dev.sanskar.frame.ui.home

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sanskar.frame.databinding.TasksListItemBinding
import dev.sanskar.frame.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class TasksListAdapter(
    private val viewModel: MainViewModel,
    private val completed: Boolean = false
) : RecyclerView.Adapter<TasksListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TasksListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TasksListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = if (completed) {
            holder.binding.checkboxTask.isChecked = true
            holder.binding.checkboxTask.isClickable = false
            viewModel.completedTasks.value?.get(position) // Assign this. I love Kotlin syntax
        } else {
            holder.binding.checkboxTask.setOnClickListener {
                Handler(Looper.getMainLooper()).postDelayed({ viewModel.removeTask(position) }, 300)
            }
            viewModel.pendingTasks.value?.get(position)
        }
        holder.binding.textViewTaskItem.text = task?.item
        holder.binding.textViewTaskTimestamp.text =
            SimpleDateFormat("hh:mm a, dd/MM/yy", Locale.ENGLISH).format(task?.timestamp)
    }

    override fun getItemCount() = if(completed) viewModel.completedTasks.value?.size!! else viewModel.pendingTasks.value?.size!!
}