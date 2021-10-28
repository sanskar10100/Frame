package dev.sanskar.frame.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.sanskar.frame.databinding.FragmentAddTaskBinding
import dev.sanskar.frame.ui.MainViewModel
import dev.sanskar.frame.utils.hideKeyboard
import dev.sanskar.frame.utils.showKeyboard

class AddTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputLayoutAddTask.editText?.requestFocus()
        context?.showKeyboard()

        binding.buttonAddTask.setOnClickListener { checkTaskContent() }

        binding.textInputLayoutAddTask.editText?.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    checkTaskContent()
                    true
                }
                else -> false
            }
        }
    }

    private fun checkTaskContent() {
        val taskText = binding.textInputLayoutAddTask.editText?.text.toString().trim()
        if (taskText.isEmpty()) {
            binding.textInputLayoutAddTask.error = "Task Detail cannot be empty"
        } else {
            viewModel.addTask(taskText)
            context?.hideKeyboard()
            dismiss()
        }
    }
}