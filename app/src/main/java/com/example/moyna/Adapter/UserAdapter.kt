package com.example.moyna.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moyna.R
import com.example.moyna.databinding.ProfileItemBinding
import com.example.moyna.model.User

class UserAdapter(var context: Context, var userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding:ProfileItemBinding = ProfileItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.profile_item,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.profileName.text = user.name
        Glide.with(context).load(user.profileImage).placeholder(R.drawable.baseline_person_24).into(holder.binding.profileImage)
    }
}