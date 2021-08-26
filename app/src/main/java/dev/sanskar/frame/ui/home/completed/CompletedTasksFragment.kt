package dev.sanskar.frame.ui.home.completed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.sanskar.frame.databinding.FragmentCompletedTasksBinding
import dev.sanskar.frame.ui.MainViewModel
import dev.sanskar.frame.ui.home.TasksListAdapter
import dev.sanskar.frame.utils.TAG
import dev.sanskar.frame.utils.crossfade

class CompletedTasksFragment : Fragment() {
    private lateinit var binding: FragmentCompletedTasksBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listCompletedTasks.layoutManager = LinearLayoutManager(context)

        viewModel.completedTasks.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.listCompletedTasks.crossfade(binding.lottieEmptyCompletedTaskList, 200)
                Log.d(TAG, "onViewCreated: Setting pending tasks adapter")
                binding.listCompletedTasks.adapter = TasksListAdapter(viewModel, true)
            } else {
                binding.lottieEmptyCompletedTaskList.crossfade(binding.listCompletedTasks, 200)
            }
        }
    }
}