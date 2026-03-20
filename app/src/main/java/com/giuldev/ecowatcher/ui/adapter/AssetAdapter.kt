package com.giuldev.ecowatcher.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.giuldev.ecowatcher.databinding.ItemAssetBinding
import com.giuldev.ecowatcher.domain.model.Asset
import java.text.NumberFormat
import java.util.Locale

/**
 * Adapter do RecyclerView para a lista de ativos.
 * Usa ListAdapter e DiffUtil para animações fluidas e alta performance na atualização da lista.
 */
class AssetAdapter(
    private val onItemClick: (Asset) -> Unit
) : ListAdapter<Asset, AssetAdapter.AssetViewHolder>(AssetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssetViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AssetViewHolder(
        private val binding: ItemAssetBinding,
        private val onItemClick: (Asset) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asset: Asset) {
            binding.root.setOnClickListener { onItemClick(asset) }
            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

            // Carregamento de imagem eficiente com Coil e corte circular
            binding.ivAssetLogo.load(asset.imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            binding.tvAssetName.text = asset.name
            binding.tvAssetSymbol.text = asset.symbol
            binding.tvAssetPrice.text = currencyFormat.format(asset.currentPrice)

            // Lógica visual: Verde para alta, Vermelho para queda usando as novas cores do tema
            val isPositive = asset.priceChangePercentage24h >= 0
            val sign = if (isPositive) "+" else ""
            val colorRes = if (isPositive) com.giuldev.ecowatcher.R.color.price_up else com.giuldev.ecowatcher.R.color.price_down
            val color = androidx.core.content.ContextCompat.getColor(binding.root.context, colorRes)

            binding.tvAssetChange.text = String.format(Locale.getDefault(), "%s%.2f%%", sign, asset.priceChangePercentage24h)
            binding.tvAssetChange.setTextColor(color)
        }
    }

    class AssetDiffCallback : DiffUtil.ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem == newItem
        }
    }
}
