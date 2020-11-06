package tech.jhavidit.payOAssignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.adapter.UserDataAdapter
import tech.jhavidit.payOAssignment.databinding.FragmentHomeBinding
import tech.jhavidit.payOAssignment.viewModel.UserDataViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userDataAdapter : UserDataAdapter
    lateinit var userDataViewModel: UserDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home, container, false
            )
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDataViewModel = ViewModelProvider(this).get(UserDataViewModel::class.java)
        userDataAdapter = UserDataAdapter(requireContext())

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

        userDataViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            userDataAdapter.submitList(it)
            binding.progressCircular.visibility= View.GONE

        })
    }
    private fun initializeList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = userDataAdapter

    }


}