package com.shunsukeshoji.giticons.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shunsukeshoji.giticons.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<User>)

    @Query("SELECT * FROM users WHERE id  > (:since)  ORDER BY id ")
    fun usersById(since: Int): DataSource.Factory<Int, User>
}