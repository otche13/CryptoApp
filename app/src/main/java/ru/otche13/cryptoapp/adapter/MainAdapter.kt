package ru.otche13.cryptoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_cryptoinfo.view.*
import ru.otche13.cryptoapp.R
import ru.otche13.cryptoapp.models.CryptoItem

class MainAdapter: RecyclerView.Adapter<MainAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View): RecyclerView.ViewHolder(view)

    private val callback = object : DiffUtil.ItemCallback<CryptoItem>() {
        override fun areItemsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cryptoinfo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val crypto = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(crypto.image)
                .apply(RequestOptions.bitmapTransform( CircleCrop()))
                .into(crypto_image)

            crypto_name.text = crypto.name
            crypto_symbol.text = crypto.symbol
            crypto_change.text= crypto.ath_change_percentage.toString()
            crypto_price.text=crypto.current_price.toString()

            setOnClickListener {
                onItemClickListener?.let { it(crypto) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((CryptoItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (CryptoItem) -> Unit) {
        onItemClickListener = listener
    }

}