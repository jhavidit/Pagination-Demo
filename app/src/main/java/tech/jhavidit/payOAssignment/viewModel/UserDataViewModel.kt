package tech.jhavidit.payOAssignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import tech.jhavidit.payOAssignment.models.Data
import tech.jhavidit.payOAssignment.repository.UserDataSource

class UserDataViewModel : ViewModel() {
     var userLiveData: LiveData<PagedList<Data>>
    var userDataSource=UserDataSource(Dispatchers.IO)
    // var showProgress : LiveData<Boolean>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(6)
            .setEnablePlaceholders(false)
            .build()
        userLiveData = initializedPagedListBuilder(config).build()
       // this.showProgress=userDataSource.showProgress
    }

    fun getPosts(): LiveData<PagedList<Data>> = userLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Data> {

        val dataSourceFactory = object : DataSource.Factory<Int, Data>() {
            override fun create(): DataSource<Int, Data> {
                return UserDataSource(
                    Dispatchers.IO
                )
            }
        }
        return LivePagedListBuilder<Int, Data>(dataSourceFactory, config)
    }


}