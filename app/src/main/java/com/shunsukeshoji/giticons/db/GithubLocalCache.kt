package com.shunsukeshoji.giticons.db

import com.shunsukeshoji.giticons.model.User
import java.util.concurrent.Executor

class GithubLocalCache(
    private val userDao: UserDao,
    private val ioExecutor: Executor
) {
    fun insert(users: List<User>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            userDao.insert(users)
            insertFinished()
        }
    }

    fun usersById(since:Int) = userDao.usersById(since)
}
