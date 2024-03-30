package com.example.submissionawalfundamental.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawalfundamental.database.Favorite
import com.example.submissionawalfundamental.databinding.ItemUserBinding
import com.example.submissionawalfundamental.ui.UserDetailActivity

class FavoriteAdapter : ListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FavoriteViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val favorite = getItem(position)
                val intent = Intent(v?.context, UserDetailActivity::class.java)
                intent.putExtra("USERNAME", favorite.username)
                intent.putExtra("AVATARURL", favorite.avatarUrl)
                v?.context?.startActivity(intent)
            }
        }

        fun bind(favorite: Favorite) {
            binding.textUsername.text = favorite.username

            Glide.with(binding.root)
                .load(favorite.avatarUrl)
                .into(binding.imageProfile)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}