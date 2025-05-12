package com.example.withcontexttest.ui

import android.util.Log
import android.view.Choreographer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.withcontexttest.domain.model.User
import com.example.withcontexttest.domain.usecase.GetOldestUserUseCase
import com.example.withcontexttest.domain.usecase.SaveUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val getOldestUserUseCase: GetOldestUserUseCase,
    private val saveUsersUseCase: SaveUsersUseCase,
) : ViewModel() {

    private val TAG = this::class.java.simpleName
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun saveUsers(count: Int, dispatcher: CoroutineDispatcher? = null) {
        viewModelScope.launch {
            Log.i(TAG, "saveUsers running on thread: ${Thread.currentThread().name}, count: $count")
            detectMainThreadLag() // Check if UI thread is lagging
            saveUsersUseCase.execute(count, dispatcher)
            detectMainThreadLag() // Check again after task is completed
        }
    }

    fun findOldestUser(dispatcher: CoroutineDispatcher? = null) {
        viewModelScope.launch {
            Log.i(TAG, "findOldestUser running on thread: ${Thread.currentThread().name}")
            detectMainThreadLag() // Check if UI thread is lagging
            val oldestUser = getOldestUserUseCase.execute(dispatcher)
            Log.i(TAG, "findOldestUser: oldestUser = $oldestUser")
            detectMainThreadLag() // Check again after task is completed
            _user.value = oldestUser
        }
    }

    fun detectMainThreadLag(tag: String = "LagTest") {
        val choreographer = Choreographer.getInstance()
        val frameStartTime = System.nanoTime()

        choreographer.postFrameCallback {
            val frameEndTime = System.nanoTime()
            val diffMs = (frameEndTime - frameStartTime) / 1_000_000

            if (diffMs > 16) {
                Log.w(tag, "Skipped frame! Took $diffMs ms.")
            } else {
                Log.d(tag, "Frame OK: $diffMs ms.")
            }
        }
    }
}
