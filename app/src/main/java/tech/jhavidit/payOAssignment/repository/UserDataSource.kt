package tech.jhavidit.payOAssignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.jhavidit.payOAssignment.models.Data
import tech.jhavidit.payOAssignment.network.APIClient
import kotlin.coroutines.CoroutineContext

class UserDataSource(coroutineContext: CoroutineContext) : PageKeyedDataSource<Int, Data>() {
    private val page = APIClient.FIRST_PAGE
    private val apiService = APIClient.getClient()
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
  //  val showProgress = MutableLiveData<Boolean>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Data>
    ) {
        scope.launch {
            try {
                val response = apiService.fetchUsers(page)
            //    showProgress.value = true
                when {
                    response.isSuccessful -> {
                  //      showProgress.value = false
                        val userData = response.body()?.data
                        if (response.body() != null)
                            callback.onResult(response.body()?.data!!, null, page + 1)
                    }
                }
            } catch (e: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
        scope.launch {
            try {
                val response = apiService.fetchUsers(params.key)
             //   showProgress.value = true
                when {
                    response.isSuccessful -> {
                    //    showProgress.value = false
                        val userData = apiService.fetchUsers(params.key)
                        if (response.body() != null) {

                            if (response.body()?.totalPages!! >= params.key)

                                callback.onResult(response.body()?.data!!, params.key + 1)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
        scope.launch {
            try {
             //   showProgress.value = true
                val response = apiService.fetchUsers(params.key)
                when {
                    response.isSuccessful -> {
              //          showProgress.value = false
                        val userData = apiService.fetchUsers(params.key)
                        if (response.body() != null) {

                            if (params.key > 1)

                                callback.onResult(response.body()?.data!!, params.key - 1)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

