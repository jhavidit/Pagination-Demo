package tech.jhavidit.payOAssignment.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.viewModel.UserDataViewModel
import tech.jhavidit.payOAssignment.adapter.UserDataAdapter
import tech.jhavidit.payOAssignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userDataAdapter =
        UserDataAdapter(this)
    lateinit var userDataViewModel: UserDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        userDataViewModel = ViewModelProvider(this).get(UserDataViewModel::class.java)

        observeLiveData()
        initializeList()

    }
    private fun observeLiveData() {
        //observe live data emitted by view model
//        userDataViewModel.showProgress.observe(this, Observer {
//            if(it)
//                binding.progressCircular.visibility= VISIBLE
//            else
//                binding.progressCircular.visibility=GONE
//        })

        userDataViewModel.getPosts().observe(this, Observer {
            userDataAdapter.submitList(it)
            binding.progressCircular.visibility=GONE

        })
    }
    private fun initializeList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userDataAdapter

    }

}