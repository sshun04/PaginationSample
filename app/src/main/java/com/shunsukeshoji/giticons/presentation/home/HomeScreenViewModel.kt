package com.shunsukeshoji.giticons.presentation.home

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.shunsukeshoji.giticons.data.GithubRepository
import com.shunsukeshoji.giticons.model.User
import com.shunsukeshoji.giticons.model.UserRequestResult
import kotlin.random.Random

class HomeScreenViewModel(private val repository: GithubRepository) : ViewModel() {

    private val startId: MutableLiveData<Int> = MutableLiveData()
    private val userResult: LiveData<UserRequestResult> = Transformations.map(startId) {
        repository.load(it)
    }

    val users: LiveData<PagedList<User>> = Transformations.switchMap(userResult) { it.data }
    val netWorkErrors: LiveData<String> = Transformations.switchMap(userResult) { it.networkErrors }

    fun refresh() {
        startId.postValue(1)
    }

    class ViewModelFactory(private val repository: GithubRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeScreenViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

