package com.defconapplications.marvel.listfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.defconapplications.marvel.R
import com.defconapplications.marvel.ComicAdapter
import com.defconapplications.marvel.ComicClickListener
import com.defconapplications.marvel.databinding.ListFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        viewModel =
                ViewModelProvider(this).get(ListViewModel::class.java)

        binding.searchWindow.setText(viewModel.searchWord.value)

        binding.recycler.adapter = ComicAdapter(ComicClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(it.id))
        })

        binding.homeIcon.setOnClickListener {
            viewModel.searchActionActive.value = false
        }

        binding.searchIcon.setOnClickListener {
            viewModel.searchActionActive.value = true
        }

        binding.searchWindow.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchWord.value = s.toString()
            }
        })

        binding.cancelInputButton.setOnClickListener {
            binding.searchWindow.setText("")
        }

        viewModel.searchActionActive.observe(viewLifecycleOwner, {
            if (it)
                swapToSearch()
            else
                swapToHome()
        })

        viewModel.searchWord.observe(viewLifecycleOwner, {
            if (it == "" && viewModel.searchActionActive.value!!) {
                viewModel.state.value = ListViewModel.State.HELP_NO_SEARCHWORD
            } else {
                viewModel.state.value = ListViewModel.State.LOADING
            }
            viewModel.fetchComicData()
        })

        viewModel.comicList.observe(viewLifecycleOwner, {
            viewModel.comicListUpdateEvent.value = true
        })

        viewModel.comicListUpdateEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                if (viewModel.comicList.value == null)
                    viewModel.state.value = ListViewModel.State.LOADING
                else if (viewModel.searchWord.value == "")
                    viewModel.state.value = ListViewModel.State.HELP_NO_SEARCHWORD
                else if (viewModel.comicList.value!!.isEmpty() && viewModel.searchWord.value != null)
                    viewModel.state.value = ListViewModel.State.HELP_NO_COMICS
                else if (viewModel.comicList.value!!.isNotEmpty()) {
                    (binding.recycler.adapter as ComicAdapter).submitList(viewModel.comicList.value)
                    viewModel.state.value = ListViewModel.State.LISTING
                }
                viewModel.comicListUpdateEvent.value = false
            }
        })

        viewModel.state.observe(viewLifecycleOwner, {
            when(it) {
                ListViewModel.State.LISTING -> {
                    binding.spinner.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                    binding.help.visibility = View.GONE
                }
                ListViewModel.State.LOADING -> {
                    binding.spinner.visibility = View.VISIBLE
                    binding.recycler.visibility = View.GONE
                    binding.help.visibility = View.GONE
                }
                ListViewModel.State.HELP_NO_COMICS -> {
                    binding.spinner.visibility = View.GONE
                    binding.recycler.visibility = View.GONE
                    binding.help.visibility = View.VISIBLE
                    binding.helpText.text = getString(R.string.help_no_comics, viewModel.searchWord.value)
                }
                ListViewModel.State.HELP_NO_SEARCHWORD -> {
                    binding.spinner.visibility = View.GONE
                    binding.recycler.visibility = View.GONE
                    binding.help.visibility = View.VISIBLE
                    binding.helpText.text = getString(R.string.help_no_search_word)
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                    .show()
            }
        })

        return binding.root
    }

    fun swapToHome() {
        binding.searchView.visibility = View.GONE
        binding.homeIcon.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.secondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.searchIcon.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
        viewModel.searchWord.value = null
    }

    fun swapToSearch() {
        binding.searchView.visibility = View.VISIBLE
        binding.homeIcon.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
        binding.searchIcon.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.secondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);
        viewModel.searchWord.value = binding.searchWindow.text.toString()
    }
}