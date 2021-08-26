package dev.sanskar.frame.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dev.sanskar.frame.model.Task
import dev.sanskar.frame.utils.TAG
import dev.sanskar.frame.utils.TaskStates

class MainViewModel : ViewModel() {

    private val user: FirebaseUser = Firebase.auth.currentUser!!
    val pendingTasks = MutableLiveData<MutableList<Task>>()
    val completedTasks = MutableLiveData<MutableList<Task>>()
    val tasksListLoaded = MutableLiveData<Boolean>(false)
    val toastMessage = MutableLiveData<String>()

    fun loadTasks() {
        Firebase.database.reference.child("users").child(user.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "onDataChange: $snapshot")
                    val tempPendingTasksList = mutableListOf<Task>()
                    val tempCompletedTasksList = mutableListOf<Task>()
                    for (child in snapshot.children) {
                        val task = child.getValue<Task>()
                        task?.id = child.key.toString()

                        if (task?.status == TaskStates.pending.toString()) tempPendingTasksList.add(task) else tempCompletedTasksList.add(task!!)

                        Log.d(TAG, "onDataChange: $task")
                    }
                    pendingTasks.value = tempPendingTasksList
                    completedTasks.value = tempCompletedTasksList
                    tasksListLoaded.value = true

                    Log.d(TAG, "onDataChange: ${pendingTasks.value}")
                    Log.d(TAG, "onDataChange: ${completedTasks.value}")

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.code}, ${error.message}")
                }
            })
    }

    fun removeTask(position: Int) {
        val task = pendingTasks.value?.get(position)
        task?.status = TaskStates.completed.toString()
        Firebase.database.reference.child("users").child(user.uid).child(task?.id!!)
            .setValue(task)
            .addOnSuccessListener {
                toastMessage.value = "Task marked complete!"
            }
            .addOnFailureListener {
                toastMessage.value = "Task Completion failed successfully!"
            }
    }

    fun addTask(taskContent: String) {
        val task = Task(taskContent, TaskStates.pending.toString(), System.currentTimeMillis(), "")
        Firebase.database.reference.child("users").child(user.uid).push()
            .setValue(task)
            .addOnSuccessListener {
                    toastMessage.value = "Task Added Successfully!"
            }
            .addOnFailureListener {
                toastMessage.value = "Task Addition Failed Successfully!"
            }
    }
}