package com.shunsukeshoji.giticons.data

import androidx.paging.LivePagedListBuilder
import com.shunsukeshoji.giticons.api.GithubService
import com.shunsukeshoji.giticons.db.GithubLocalCache
import com.shunsukeshoji.giticons.model.UserRequestResult

class GithubRepository(
    private val service: GithubService,
    private val cache: GithubLocalCache
) {

    fun load(since: Int): UserRequestResult {
        val dataSourceFactory = cache.usersByIds(since)

        val boundaryCallback = UserBoundaryCallBack(since,service, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, 30)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return UserRequestResult(data, networkErrors)
    }
}