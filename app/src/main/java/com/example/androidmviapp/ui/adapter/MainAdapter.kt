package com.example.androidmviapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmviapp.data.models.UserApiResponse
import com.example.androidmviapp.databinding.ViewHolderMainBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var list = mutableListOf<UserApiResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(list: List<UserApiResponse>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderMainBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ViewHolderMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = holder.viewDataBinding
        val item = this.list[position]

        binding.tvTitle.text = item.name
        binding.tvDesc.text = item.email

    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}