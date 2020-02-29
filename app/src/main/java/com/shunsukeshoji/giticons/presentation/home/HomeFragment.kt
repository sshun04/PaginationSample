package com.shunsukeshoji.giticons.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.shunsukeshoji.giticons.presentation.MyPagedListAdapter
import com.shunsukeshoji.giticons.databinding.FragmentHomeBinding
import com.shunsukeshoji.giticons.injection.provideViewModelFactory
import com.shunsukeshoji.giticons.model.User

class HomeFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by lazy {
        ViewModelProvider(requireActivity(), provideViewModelFactory(requireContext())).get(
            HomeScreenViewModel::class.java
        )
    }

    private lateinit var binding: FragmentHomeBinding

    private val pagedListAdapter = MyPagedListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        initAdapter()
        viewModel.refresh()

        return binding.root
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = pagedListAdapter
        viewModel.users.observe(viewLifecycleOwner, Observer<PagedList<User>> {
            pagedListAdapter.submitList(it)
        })
        viewModel.netWorkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(requireContext(), "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }



}