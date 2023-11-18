package com.aking.tiktok.ui.test

import com.aking.base.base.BaseRepository
import com.aking.base.base.BaseViewModel
import com.aking.data.ApiResponse
import com.aking.data.model.Friend
import com.aking.data.model.User
import com.aking.tiktop.httpclient.http.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Rick at 2023-11-18 20:28.
 * Description:
 */
class TestViewModel(state: TestState) : BaseViewModel<TestState>(state) {
    private val repo = TestRepository()

    fun incrementCount() {
        setState { copy(count = count + 1) }
    }

    fun requestFriend() {
        setState {
            copy(friend = Friend(2, 2))
        }
    }

    fun requestUser() {
        suspend { repo.requestUser() }.execute {
            copy(data = it)
        }
    }

    fun request() {
        repo.requestUserF().execute {
            copy(data = it)
        }
    }
}


class TestRepository : BaseRepository() {

    suspend fun requestUser(): User {
        return request { ApiClient.userApi.login("16670267862", "123456").unwrap() }
    }

    fun requestUserF(): Flow<User> {
        return requestFlow { ApiClient.userApi.login("16670267862", "123456").unwrap() }
    }
}