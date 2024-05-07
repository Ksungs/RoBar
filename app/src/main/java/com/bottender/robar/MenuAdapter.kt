package com.bottender.robar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bottender.robar.databinding.ItemMenuBinding

class MenuAdapter(private val menus: List<Menu>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        Log.d("debug","onCreateViewHolder")
        val binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuViewHolder(binding).also { holder ->
            binding.deleteCheck.setOnCheckedChangeListener{buttonView, isChecked ->
                Log.d("debug", "checked clicked $isChecked")
                menus.getOrNull(holder.adapterPosition)?.deleteCheck = isChecked
            }

        }
    }

    fun setDeleteMode(deleteMode: Boolean) {
        for (menu in menus) {
            menu.deleteMode = deleteMode
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menus[position]
        holder.bind(menu)

        if (menu.deleteMode) {
            holder.binding.deleteCheck.visibility = View.VISIBLE
        } else {
            holder.binding.deleteCheck.visibility = View.GONE
        }
    }
    class MenuViewHolder(val binding:ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(menu: Menu) {
                binding.menuTitleText.text = menu.title
                binding.deleteCheck.isChecked = menu.deleteCheck
            }
    }
}
