package com.shunsukeshoji.giticons.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.shunsukeshoji.giticons.api.GithubService
import com.shunsukeshoji.giticons.api.loadUser
import com.shunsukeshoji.giticons.db.GithubLocalCache
import com.shunsukeshoji.giticons.model.User

class UserBoundaryCallBack(
    since: Int,
    private val service: GithubService,
    private val cache: GithubLocalCache
) : PagedList.BoundaryCallback<User>() {
    // avoid triggering multiple requests in the same time
    private var isRequestInProgress: Boolean = false

    // keep the last requested page. When the request is successful, increment the page number.
    private var rowLastId: Int = since

    // LiveData of network errors.
    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String> = _networkErrors


    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return
        isRequestInProgress = true
        loadUser(service, rowLastId, { users ->
            cache.insert(users) {
                rowLastId = users.last().id
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }
}

