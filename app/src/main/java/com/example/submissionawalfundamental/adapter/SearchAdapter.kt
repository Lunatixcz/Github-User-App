package com.example.submissionawalfundamental.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawalfundamental.data.response.ItemsItem
import com.example.submissionawalfundamental.databinding.ItemUserBinding
import com.example.submissionawalfundamental.ui.UserDetailAcitivty

class SearchAdapter  :
    ListAdapter<ItemsItem, SearchAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent,false )
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, UserDetailAcitivty::class.java)
            intent.putExtra("ITEM_DETAIL", user)
            holder.itemView.context.startActivity(intent)
        }
    }
    class MyViewHolder (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            binding.textUsername.text = "${user.login}"
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.imageProfile)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}