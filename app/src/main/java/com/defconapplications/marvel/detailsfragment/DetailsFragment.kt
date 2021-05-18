package com.defconapplications.marvel.detailsfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.defconapplications.marvel.R
import com.defconapplications.marvel.databinding.DetailsFragmentBinding
import com.defconapplications.marvel.getCreators
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.comic.observe(viewLifecycleOwner, {
            it?.let { comic ->
                val imgUri = comic.thumbnailPath.toUri().buildUpon().scheme("https").build()
                Glide.with(binding.background?.context)
                        .load(imgUri)
                        .into(binding.background)
                binding.title.text = it.title
                binding.description.text = it.description
                binding.writers.text = it.creators.getCreators()
                binding.moreButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(comic.url)
                    startActivity(intent)
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        })

        binding.toolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

}