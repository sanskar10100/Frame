package dev.sanskar.frame.ui.home

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
import coil.load
import coil.transform.CircleCropTransformation
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.sanskar.frame.R
import dev.sanskar.frame.databinding.FragmentHomeBinding
import dev.sanskar.frame.ui.MainViewModel
import dev.sanskar.frame.utils.TAG
import dev.sanskar.frame.utils.crossfade

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Firebase.auth.currentUser == null) {
            Log.d(TAG, "onViewCreated: Moving to login!")
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        } else {
            viewModel.toastMessage.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }

            renderContent()
        }
    }

    private fun renderContent() {
        viewModel.loadTasks()
        setupToolbar()

        binding.viewpager2TasksFragments.adapter = TasksFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewpager2TasksFragments) { tab, position ->
            tab.text = when (position) {
                0 -> "Pending"
                1 -> "Completed"
                else -> "N/A"
            }
        }.attach()

        viewModel.tasksListLoaded.observe(viewLifecycleOwner) {
            if (it) {
                binding.viewpager2TasksFragments.crossfade(binding.lottieLoadingTasks, 500)
            }
        }
    }

    private fun setupToolbar() {
        binding.imageViewUserImage.load(Firebase.auth.currentUser?.photoUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.textViewUserName.text = Firebase.auth.currentUser?.displayName
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_sign_out) {
                AuthUI.getInstance().signOut(requireContext())
                    .addOnSuccessListener {
                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Logout failed!")
                    }
            }
            true
        }
    }
}