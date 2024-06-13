package com.bottender.robar2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class EditMenuFragment : DialogFragment() {

    private var menuName: String? = null
    private var water: Int = 0
    private var cola: Int = 0
    private var cider: Int = 0
    private var orange: Int = 0
    private var position: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuNameEditText: EditText = view.findViewById(R.id.menuNameEditText)
        val waterEditText: EditText = view.findViewById(R.id.waterEditText)
        val colaEditText: EditText = view.findViewById(R.id.colaEditText)
        val ciderEditText: EditText = view.findViewById(R.id.ciderEditText)
        val orangeEditText: EditText = view.findViewById(R.id.orangeEditText)
        val saveButton: Button = view.findViewById(R.id.saveMenuButton)

        arguments?.let {
            menuName = it.getString("menuName")
            water = it.getInt("water")
            cola = it.getInt("cola")
            cider = it.getInt("cider")
            orange = it.getInt("orange")
            position = it.getInt("position", -1)
        }

        menuNameEditText.setText(menuName)
        waterEditText.setText(water.toString())
        colaEditText.setText(cola.toString())
        ciderEditText.setText(cider.toString())
        orangeEditText.setText(orange.toString())

        saveButton.setOnClickListener {
            val updatedMenuName = menuNameEditText.text.toString()
            val updatedWater = waterEditText.text.toString().toIntOrNull() ?: 0
            val updatedCola = colaEditText.text.toString().toIntOrNull() ?: 0
            val updatedCider = ciderEditText.text.toString().toIntOrNull() ?: 0
            val updatedOrange = orangeEditText.text.toString().toIntOrNull() ?: 0

            if (updatedMenuName.isEmpty()) {
                Toast.makeText(context, "메뉴 이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val total = updatedWater + updatedCola + updatedCider + updatedOrange

            if (total == 0) {
                Toast.makeText(context, "최소 한 개 이상의 음료를 선택해야 합니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (total < 100) {
                Toast.makeText(context, "최소 음료량은 100ml입니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 메뉴 이름 중복 체크 (현재 수정 중인 메뉴 제외)
            val mainActivity = activity as? MainActivity
            if (mainActivity?.isMenuNameDuplicate(updatedMenuName, position) == true) {
                Toast.makeText(context, "중복된 이름입니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedMenu = Menu(updatedMenuName, updatedWater, updatedCola, updatedCider, updatedOrange)
            (targetFragment as? MenuDetailFragment)?.onMenuUpdated(updatedMenu, position)
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.setLayout(
            500,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun newInstance(menu: Menu, position: Int): EditMenuFragment {
            val args = Bundle().apply {
                putString("menuName", menu.name)
                putInt("water", menu.water)
                putInt("cola", menu.cola)
                putInt("cider", menu.cider)
                putInt("orange", menu.orange)
                putInt("position", position)
            }
            return EditMenuFragment().apply {
                arguments = args
            }
        }
    }
}
