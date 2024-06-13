package com.bottender.robar2

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val menus: MutableList<Menu>, private val listener: OnMenuInteractionListener) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    interface OnMenuInteractionListener {
        fun onMenuEdit(position: Int, menu: Menu)
        fun onMenuDelete(position: Int)
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuTextView: TextView = itemView.findViewById(R.id.menuTextView)
        val menuPriceTextView: TextView = itemView.findViewById(R.id.menuPriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menus[position]
        holder.menuTextView.text = menu.name
        holder.menuPriceTextView.text = "${menu.calculatePrice()} 원"

        holder.itemView.setOnClickListener {
            listener.onMenuEdit(position, menu)
        }

        holder.itemView.setOnLongClickListener {
            if (menu.isDefault) {
                AlertDialog.Builder(holder.itemView.context)
                    .setMessage("기본 메뉴는 삭제할 수 없습니다!")
                    .setPositiveButton("확인", null)
                    .show()
            } else {
                AlertDialog.Builder(holder.itemView.context)
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        listener.onMenuDelete(position)
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
            true
        }
    }

    override fun getItemCount() = menus.size
}
