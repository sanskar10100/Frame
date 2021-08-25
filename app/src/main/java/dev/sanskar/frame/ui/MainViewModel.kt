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

class MainViewModel : ViewModel() {

    private val user: FirebaseUser = Firebase.auth.currentUser!!
    val tasks = MutableLiveData<MutableList<Task>>()
    val toastMessage = MutableLiveData<String>()

    fun loadTasks() {
        Firebase.database.reference.child("users").child(user.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "onDataChange: $snapshot")
                    val tempTasksList = mutableListOf<Task>()
                    for (child in snapshot.children) {
                        val task = child.getValue<Task>()
                        task?.id = child.key.toString()
                        tempTasksList.add(task!!)

                        Log.d(TAG, "onDataChange: $task")
                    }
                    tasks.value = tempTasksList

                    Log.d(TAG, "onDataChange: ${tasks.value}")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.code}, ${error.message}")
                }
            })
    }

    fun removeTask(position: Int) {
        val tasksCopy = tasks.value
        tasksCopy?.removeAt(position)
        tasks.value = tasksCopy
    }

    fun addTask(taskContent: String) {
        val task = Task(taskContent, "pending", System.currentTimeMillis(), "")
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