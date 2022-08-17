package com.example.androidmviapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidmviapp.R
import com.example.androidmviapp.databinding.ActivityMainBinding
import com.example.androidmviapp.ui.adapter.MainAdapter
import com.example.androidmviapp.ui.intent.MainIntent
import com.example.androidmviapp.ui.viewmodel.MainViewModel
import com.example.androidmviapp.ui.viewstate.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    private val mainAdapter = MainAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observeViewModels()
        handleViewModelIntent()
    }

    private fun handleViewModelIntent() {
        lifecycleScope.launch {
            mainViewModel.mainIntent.send(MainIntent.GetUsers)
        }
    }

    private fun observeViewModels() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainViewState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    is MainViewState.Success -> {
                        binding.progress.visibility = View.GONE
                        mainAdapter.addItems(it.data)
                    }
                    is MainViewState.Error -> {
                        binding.progress.visibility = View.GONE
                    }
                    else -> {
                        // no need to handle this section
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.rvPosts.adapter = mainAdapter
    }
}