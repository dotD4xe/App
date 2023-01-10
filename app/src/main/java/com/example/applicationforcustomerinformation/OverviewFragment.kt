package com.example.applicationforcustomerinformation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.applicationforcustomerinformation.databinding.FragmentOverviewBinding
import com.example.applicationforcustomerinformation.network.BankApi
import com.example.applicationforcustomerinformation.network.clientData
import kotlinx.coroutines.*

class OverviewFragment : Fragment() {
    private var binding: FragmentOverviewBinding? = null

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
        GlobalScope.launch {
            val result = BankApi.retrofitService.getUser("45717360")
            Log.d("ayush: ", result[0].brand)
        }

    }

}