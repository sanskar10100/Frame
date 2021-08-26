package dev.sanskar.frame.ui.home.pending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.sanskar.frame.R
import dev.sanskar.frame.databinding.FragmentPendingTasksBinding
import dev.sanskar.frame.ui.MainViewModel
import dev.sanskar.frame.ui.home.TasksListAdapter
import dev.sanskar.frame.utils.TAG

class PendingTasksFragment : Fragment() {
    private lateinit var binding: FragmentPendingTasksBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listPendingTasks.layoutManager = LinearLayoutManager(context)
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }

        viewModel.pendingTasks.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: Setting pending tasks adapter")
            binding.listPendingTasks.adapter = TasksListAdapter(viewModel)
        }
    }
}