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

class AddMenuFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuNameEditText: EditText = view.findViewById(R.id.menuNameEditText)
        val waterEditText: EditText = view.findViewById(R.id.waterEditText)
        val colaEditText: EditText = view.findViewById(R.id.colaEditText)
        val ciderEditText: EditText = view.findViewById(R.id.ciderEditText)
        val orangeEditText: EditText = view.findViewById(R.id.orangeEditText)
        val saveButton: Button = view.findViewById(R.id.saveMenuButton)

        saveButton.setOnClickListener {
            val menuName = menuNameEditText.text.toString()
            val water = waterEditText.text.toString().toIntOrNull() ?: 0
            val cola = colaEditText.text.toString().toIntOrNull() ?: 0
            val cider = ciderEditText.text.toString().toIntOrNull() ?: 0
            val orange = orangeEditText.text.toString().toIntOrNull() ?: 0

            if (menuName.isEmpty()) {
                Toast.makeText(context, "메뉴 이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val total = water + cola + cider + orange

            if (total == 0) {
                Toast.makeText(context, "최소 한 개 이상의 음료를 선택해야 합니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (total < 100) {
                Toast.makeText(context, "최소 음료량은 100ml입니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 메뉴 이름 중복 체크
            val mainActivity = activity as? MainActivity
            if (mainActivity?.isMenuNameDuplicate(menuName) == true) {
                Toast.makeText(context, "중복된 이름입니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newMenu = Menu(menuName, water, cola, cider, orange)
            mainActivity?.addMenu(newMenu)
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
        fun newInstance(): AddMenuFragment {
            return AddMenuFragment()
        }
    }
}
