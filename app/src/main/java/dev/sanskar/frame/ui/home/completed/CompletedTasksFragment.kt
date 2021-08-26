package dev.sanskar.frame.ui.home.completed

import dev.sanskar.frame.databinding.FragmentPendingTasksBinding
import dev.sanskar.frame.ui.home.TasksListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.sanskar.frame.R
import dev.sanskar.frame.databinding.FragmentCompletedTasksBinding
import dev.sanskar.frame.databinding.FragmentHomeBinding
import dev.sanskar.frame.ui.MainViewModel
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

        if (Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        } else {
            viewModel.toastMessage.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.loadTasks()


            viewModel.pendingTasks.observe(viewLifecycleOwner) {
                Log.d(TAG, "onViewCreated: Setting pending tasks adapter")
                binding.listCompletedTasks.layoutManager = LinearLayoutManager(context)
                binding.listCompletedTasks.adapter = TasksListAdapter(viewModel, true)
            }
        }
    }
}