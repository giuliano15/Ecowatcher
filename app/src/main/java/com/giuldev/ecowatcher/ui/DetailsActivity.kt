package com.giuldev.ecowatcher.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.giuldev.ecowatcher.R
import com.giuldev.ecowatcher.databinding.ActivityDetailsBinding
import com.giuldev.ecowatcher.domain.model.Asset
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val asset = intent.getParcelableExtra<Asset>("EXTRA_ASSET")
        if (asset != null) {
            setupUI(asset)
            setupToolbar(asset.name)
        } else {
            finish()
        }
    }

    private fun setupToolbar(title: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
        binding.toolbar.navigationIcon?.setTint(Color.WHITE)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupUI(asset: Asset) {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

        binding.ivAssetLogo.load(asset.imageUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        binding.tvAssetName.text = asset.name
        binding.tvAssetSymbol.text = asset.symbol
        binding.tvAssetPrice.text = currencyFormat.format(asset.currentPrice)

        // Change 24h
        val isPositive = asset.priceChangePercentage24h >= 0
        val sign = if (isPositive) "+" else ""
        val colorRes = if (isPositive) R.color.price_up else R.color.price_down
        val color = ContextCompat.getColor(this, colorRes)
        
        binding.tvAssetChange.text = String.format(Locale.getDefault(), "%s%.2f%%", sign, asset.priceChangePercentage24h)
        binding.tvAssetChange.setTextColor(color)

        // Details rows
        binding.layoutMarketCap.tvLabel.text = "Capitalização de Mercado"
        binding.layoutMarketCap.tvValue.text = currencyFormat.format(asset.marketCap)

        binding.layoutVolume.tvLabel.text = "Volume Total (24h)"
        binding.layoutVolume.tvValue.text = currencyFormat.format(asset.totalVolume)

        binding.layoutHigh24h.tvLabel.text = "Máxima (24h)"
        binding.layoutHigh24h.tvValue.text = currencyFormat.format(asset.high24h)

        binding.layoutLow24h.tvLabel.text = "Mínima (24h)"
        binding.layoutLow24h.tvValue.text = currencyFormat.format(asset.low24h)
    }
}
