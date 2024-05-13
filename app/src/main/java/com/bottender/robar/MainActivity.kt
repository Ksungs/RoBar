package com.bottender.robar
import android.os.Handler
import android.view.LayoutInflater
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bottender.robar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var menus = mutableListOf(
        Menu("칵테일 1", false, false),
        Menu("칵테일 2", false, false),
        Menu("칵테일 3", false, false),
        Menu("칵테일 4", false, false),
        Menu("칵테일 5", false, false),
        Menu("칵테일 6", false, false),
        Menu("칵테일 7", false, false),
        Menu("칵테일 8", false, false),
        Menu("칵테일 9", false, false),
        Menu("칵테일 10", false, false),
        Menu("칵테일 11", false, false),
        Menu("칵테일 12", false, false),
        Menu("칵테일 13", false, false),
        Menu("칵테일 14", false, false),
        Menu("칵테일 15", false, false),
        Menu("칵테일 16", false, false),
        Menu("칵테일 17", false, false),
        Menu("칵테일 18", false, false),
        Menu("칵테일 19", false, false)
    )

    private fun start() {
        setContentView(R.layout.activity_start)
        Handler().postDelayed({
            main()
        }, 2000)
    }

    private fun deleteMenus() {
        val iterator = menus.iterator()
        while (iterator.hasNext()) {
            val menu = iterator.next()
            if (menu.deleteCheck) {
                iterator.remove() // deleteCheck이 true인 항목을 삭제
            }
        }
    }

    private fun main() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    private fun showAddMenuDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_menu, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val menuAddSelectBtn = dialogView.findViewById<Button>(R.id.menu_add_select_btn)
        val menuAddTitle = dialogView.findViewById<EditText>(R.id.menu_add_title)

        menuAddSelectBtn.setOnClickListener {
            val menuTitle = menuAddTitle.text.toString()

            if (menuTitle.isNotEmpty()) {

                //기능 추가
                insertUserMenu(menuTitle)

                val menu = Menu(menuTitle, false, false)
                menus.add(menu)
                Log.d("debug", "Menus after adding: $menus")
                dialog.dismiss() // 다이얼로그 닫기
                (binding.menuList.adapter as? MenuAdapter)?.notifyDataSetChanged()
            }
        }
        dialog.show()
    }

    private fun loadUserMenuList() {
        val db: AppDatabase? = AppDatabase.getDatabase(applicationContext)
        val userMenuList: List<UserMenu> = db?.userMenuDao()!!.getAllUserMenu()
        if(userMenuList.isNotEmpty()) {
            val position: Int = userMenuList.size - 1
            Toast.makeText(this, userMenuList.get(position).menusName+" 등록됨",
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "등록 메뉴 없음",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun initializeViews() {
        binding.menuList.layoutManager = LinearLayoutManager(this)
        binding.menuList.adapter = MenuAdapter(menus)

        var deleteMode = false // 삭제 모드 여부를 저장할 변수

        binding.menuAddButton.setOnClickListener {
            showAddMenuDialog()
        }

        binding.menuDeleteButton.setOnClickListener {
            val adapter = binding.menuList.adapter as MenuAdapter
            if (deleteMode) {
                deleteMode = false // 삭제 모드 비활성화
                binding.menuDeleteButton.text = "메뉴 삭제" // 버튼 텍스트 변경
                adapter.setDeleteMode(false)
            } else {
                deleteMode = true
                adapter.setDeleteMode(true) // 삭제 모드 활성화
                binding.menuDeleteButton.text = "삭제 완료" // 버튼 텍스트 변경
            }
            deleteMenus()
        }
    }

    private fun insertUserMenu(menuTitle: String) {
        val db = AppDatabase.getDatabase(applicationContext)
        val userMenuDao = db?.userMenuDao()

        if (userMenuDao != null) {
            val userMenu = UserMenu(id = 0, menusName = menuTitle)
            Thread {
                userMenuDao.insertUserMenu(userMenu)
                runOnUiThread {
                    Toast.makeText(this, "$menuTitle db에 추가됨", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }
}
