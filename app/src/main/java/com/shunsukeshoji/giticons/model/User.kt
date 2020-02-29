package com.shunsukeshoji.giticons.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    @field:Json(name = "login") val login: String,
    @field:Json(name = "avatar_url") val avatar_url: String?,
    @field:Json(name = "repos_url") val repos_url: String?
)