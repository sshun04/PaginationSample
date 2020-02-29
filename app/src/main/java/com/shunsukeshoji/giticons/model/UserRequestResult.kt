package com.shunsukeshoji.giticons.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class UserRequestResult(
    val data: LiveData<PagedList<User>>,
    val networkErrors: LiveData<String>
)