package dev.sanskar.frame.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.sanskar.frame.ui.home.completed.CompletedTasksFragment
import dev.sanskar.frame.ui.home.pending.PendingTasksFragment

class TasksFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingTasksFragment()
            1 -> CompletedTasksFragment()
            else -> PendingTasksFragment()
        }
    }
}