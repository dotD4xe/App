package com.example.applicationforcustomerinformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationforcustomerinformation.databinding.FragmentOverviewBinding
import com.example.applicationforcustomerinformation.viewmodels.OverviewViewModel
import com.example.applicationforcustomerinformation.viewmodels.OverviewViewModelFactory

class OverviewFragment : Fragment() {
    private var binding: FragmentOverviewBinding? = null
    private val viewModel: OverviewViewModel by activityViewModels {
        OverviewViewModelFactory(
            (activity?.application as OverviewApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.filledButton?.setOnClickListener {
            if (binding?.searchBin?.text?.isBlank() == true) {
                binding?.searchBin?.error = "Enter BIN"
            } else {
                val bin = binding?.searchBin?.text.toString()
                viewModel.getClientData(bin)
                this.findNavController().navigate(R.id.action_overviewFragment2_to_detailFragment)
            }
        }

        val adapter = OverviewListAdapter(
            onItemClicked =  {
                viewModel.getClientData(it.bin)
                this.findNavController().navigate(R.id.action_overviewFragment2_to_detailFragment)
            }
        )
        binding?.recyclerView?.adapter = adapter

        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)
    }

}