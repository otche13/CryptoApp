package ru.otche13.cryptoapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*
import ru.otche13.cryptoapp.databinding.FragmentDetailsBinding
import ru.otche13.cryptoapp.utils.Resource

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CryptoViewModel>()
    private val bundleArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cryptoItemArgs = bundleArgs.cryptoItem

        activity?.toolbar?.title = cryptoItemArgs.name
        activity?.appbar?.elevation=4F

        if (savedInstanceState==null){
            checkNetwork()
        }

        binding.buttonErrorDetails.setOnClickListener {
                checkNetwork()
            viewModel.getCryptoInfo(cryptoItemArgs.id)
        }

        viewModel.getCryptoInfo(cryptoItemArgs.id)

        viewModel.InfoLiveData.observe(viewLifecycleOwner) { responce ->
            when (responce) {
                is Resource.Success -> {
                    progress_bar.visibility = View.INVISIBLE
                    responce.data?.let { cryptoInfo ->

                        cryptoInfo.image.large.let {

                            Glide.with(this).load(cryptoInfo.image.large)
                                .apply(RequestOptions.bitmapTransform( CircleCrop()))
                                .into(binding.detailsImage)
                        }
                        binding.detailsCryptInformation.text= replaceString(cryptoInfo.description.en)
                        binding.detailsCryptCategories.text=cryptoInfo.categories.joinToString()
                    }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }

    }

    fun replaceString(args: String):String{
        val info= mutableListOf<String>()
        args.forEach { info.add(it.toString()) }
        val newInfo= mutableListOf<String>()
        var const=0

        for (i in 0 until info.size){

            if (info[i]=="<"){
                const++
            }

            if(const==0){
                newInfo.add(info[i])
            }

            if (info[i]==">"){
                const--
            }
        }

        return newInfo.joinToString("","","")
    }

    private fun checkNetwork(){
        val state = viewModel.isNetworkAvailable(context)
        if (!state) {
            dialog_error_details.visibility = View.VISIBLE
            scroll_details.visibility=View.INVISIBLE
        } else {
            dialog_error_details.visibility = View.INVISIBLE
            scroll_details.visibility=View.VISIBLE

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}
