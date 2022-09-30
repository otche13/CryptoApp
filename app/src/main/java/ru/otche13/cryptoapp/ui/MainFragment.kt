package ru.otche13.cryptoapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.otche13.cryptoapp.R
import ru.otche13.cryptoapp.adapter.MainAdapter
import ru.otche13.cryptoapp.databinding.FragmentMainBinding
import ru.otche13.cryptoapp.utils.Resource

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CryptoViewModel> ()
    lateinit var mainAdapter: MainAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        changeList(true)

        activity?.toolbar?.title = "Список криптовалют"
        activity?.appbar?.elevation=0F

        val usdChip = binding.chipUsd
        val eurChip = binding.chipEur

        mainAdapter.setOnItemClickListener {
            val bundle = bundleOf("cryptoItem" to it)
            view.findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )

        }

        usdChip.setOnClickListener {
            if (usdChip.isChecked) {
                eurChip.isChecked = false
            } else {
                usdChip.isChecked = true
                eurChip.isChecked = false
            }
            changeList(usdChip.isChecked)
        }

        eurChip.setOnClickListener {
            if (eurChip.isChecked) {
                usdChip.isChecked = false
            } else {
                usdChip.isChecked = false
                eurChip.isChecked = true
            }

            changeList(usdChip.isChecked)

        }
        //прослушиваем LiveData
        viewModel.ListLiveData.observe(viewLifecycleOwner) { responce ->
            when (responce) {
                is Resource.Success -> {
                    main_progress_bar.visibility = View.INVISIBLE
                    responce.data?.let {
                        mainAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    main_progress_bar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    main_progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun changeList(chip:Boolean) {
        if (chip) {
            viewModel.getCryptolistUsd()
        } else {
            viewModel.getCryptolistEur()
            }
        }

    private fun initAdapter() {
        mainAdapter = MainAdapter()
        recycler_main.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}