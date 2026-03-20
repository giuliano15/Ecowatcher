package com.giuldev.ecowatcher.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.giuldev.ecowatcher.databinding.ActivityMainBinding
import com.giuldev.ecowatcher.ui.adapter.AssetAdapter
import com.giuldev.ecowatcher.ui.viewmodel.AssetState
import com.giuldev.ecowatcher.ui.viewmodel.AssetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Activity Principal do app.
 * Arquitetura reativa: Apenas observa o StateFlow da ViewModel e reage desenhando a tela.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AssetViewModel by viewModels()
    private val assetAdapter by lazy {
        AssetAdapter { asset ->
            // Clear search field as requested
            binding.etSearch.text?.clear()
            
            val intent = android.content.Intent(this, DetailsActivity::class.java).apply {
                putExtra("EXTRA_ASSET", asset)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvAssets.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = assetAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadAssets()
        }

        binding.btnRetry.setOnClickListener {
            viewModel.loadAssets()
        }

        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is AssetState.Loading -> showLoading()
                        is AssetState.Success -> showSuccess(state)
                        is AssetState.Error -> showError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        if (!binding.swipeRefreshLayout.isRefreshing) {
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
            binding.rvAssets.visibility = View.GONE
        }
        binding.layoutEmptyState.visibility = View.GONE
    }

    private fun showSuccess(state: AssetState.Success) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE

        if (state.assets.isEmpty()) {
            binding.rvAssets.visibility = View.GONE
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.tvEmptyStateMessage.text = "Nenhum ativo atualizado encontrado."
        } else {
            binding.rvAssets.visibility = View.VISIBLE
            binding.layoutEmptyState.visibility = View.GONE
            assetAdapter.submitList(state.assets)
        }
    }

    private fun showError(message: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
        binding.rvAssets.visibility = View.GONE

        binding.layoutEmptyState.visibility = View.VISIBLE
        binding.tvEmptyStateMessage.text = message
    }
}
