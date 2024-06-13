package com.bottender.robar2

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), MenuAdapter.OnMenuInteractionListener {

    private val menus = mutableListOf(
        Menu("물콜라", 50, 100, 0, 0, true),
        Menu("콜라사이다", 0, 100, 75, 0, true),
        Menu("오렌지물콜라", 50, 100, 0, 50, true),
        Menu("오렌지사이다", 0, 0, 100, 100, true)
    )

    private lateinit var adapter: MenuAdapter
    private lateinit var addMenuLauncher: ActivityResultLauncher<Intent>
    private lateinit var editMenuLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MenuAdapter(menus, this)
        recyclerView.adapter = adapter

        // 구분선 추가
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val addMenuButton: FloatingActionButton = findViewById(R.id.addMenuButton)
        addMenuButton.setOnClickListener {
            val addMenuFragment = AddMenuFragment.newInstance()
            addMenuFragment.show(supportFragmentManager, "AddMenuFragment")
        }

        addMenuLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    val menuName = it.getStringExtra("menuName") ?: return@let
                    val water = it.getIntExtra("water", 0)
                    val cola = it.getIntExtra("cola", 0)
                    val cider = it.getIntExtra("cider", 0)
                    val orange = it.getIntExtra("orange", 0)

                    val newMenu = Menu(menuName, water, cola, cider, orange)
                    menus.add(newMenu)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        editMenuLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    val menuName = it.getStringExtra("menuName") ?: return@let
                    val water = it.getIntExtra("water", 0)
                    val cola = it.getIntExtra("cola", 0)
                    val cider = it.getIntExtra("cider", 0)
                    val orange = it.getIntExtra("orange", 0)
                    val position = it.getIntExtra("position", -1)

                    if (position != -1) {
                        val updatedMenu = Menu(menuName, water, cola, cider, orange)
                        menus[position] = updatedMenu
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        }
    }

    override fun onMenuEdit(position: Int, menu: Menu) {
        val menuDetailFragment = MenuDetailFragment.newInstance(menu, position)
        menuDetailFragment.show(supportFragmentManager, "MenuDetailFragment")
    }

    override fun onMenuDelete(position: Int) {
        menus.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    fun addMenu(newMenu: Menu) {
        menus.add(newMenu)
        adapter.notifyDataSetChanged()
    }

    fun updateMenu(updatedMenu: Menu, position: Int) {
        menus[position] = updatedMenu
        adapter.notifyItemChanged(position)
    }

    // 메뉴 이름 중복 체크 메서드
    fun isMenuNameDuplicate(name: String, ignorePosition: Int? = null): Boolean {
        return menus.any { it.name == name && (ignorePosition == null || menus.indexOf(it) != ignorePosition) }
    }
}
