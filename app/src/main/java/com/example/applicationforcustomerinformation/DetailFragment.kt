package com.example.applicationforcustomerinformation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.applicationforcustomerinformation.databinding.FragmentDetailBinding
import com.example.applicationforcustomerinformation.viewmodels.OverviewViewModel
import com.example.applicationforcustomerinformation.viewmodels.OverviewViewModelFactory

class DetailFragment : Fragment() {
    private var binding: FragmentDetailBinding? = null
    private val viewModel: OverviewViewModel by activityViewModels {
        OverviewViewModelFactory(
            (activity?.application as OverviewApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDetailBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            bin.text = viewModel.bin.value
            viewModel.addNewItem(binding?.bin?.text.toString())
            viewModel.data.observe(viewLifecycleOwner
            ) { newScore ->
                bindLuhn(newScore.number?.luhn)
                bindType(newScore.type.toString())
                bindPrepaid(newScore.prepaid)
                schemeAndNetwork.text = newScore.scheme.toString()
                brand.text = newScore.brand.toString()
                length.text = newScore.number?.length.toString()
                alpha2.text = newScore.country?.alpha2.toString()
                country.text = newScore.country?.name.toString()
                latitude.text = newScore.country?.latitude.toString()
                longitude.text = newScore.country?.longitude.toString()
                nameBank.text = newScore.bank?.name.toString()
                cityBank.text = newScore.bank?.city.toString()
                urlBank.text = newScore.bank?.url.toString()
                numberBank.text = newScore.bank?.phone.toString()
                numberBank.setOnClickListener { clickNumber(numberBank.text.toString()) }
                urlBank.setOnClickListener { clickUrl(urlBank.text.toString()) }
            }
        }
    }


    private fun clickUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://$url")
        startActivity(intent)
    }

    private fun clickNumber(number: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$number")
        startActivity(dialIntent)
    }

    private fun bindType(type: String) {
        when(type) {
            "debit" -> binding?.typeDebit?.alpha = 1.0F
            "credit" -> binding?.typeCredit?.alpha = 1.0F
            else -> {
                binding?.typeCredit?.alpha = 0.35F
                binding?.typeDebit?.alpha = 0.35F
            }
        }
    }

    private fun bindPrepaid(prepaid: Boolean?) {
        when(prepaid) {
            true -> binding?.prepaidYes?.alpha = 1.0F
            false -> binding?.prepaidNo?.alpha = 1.0F
            else -> {
                binding?.prepaidNo?.alpha = 0.35F
                binding?.prepaidYes?.alpha = 0.35F
            }
        }
    }

    private fun bindLuhn(luhn: Boolean?) {
        when(luhn) {
            true -> binding?.luhnYes?.alpha = 1.0F
            false -> binding?.luhnNo?.alpha = 1.0F
            else -> {
                binding?.luhnNo?.alpha = 0.35F
                binding?.luhnYes?.alpha = 0.35F
            }
        }
    }
}