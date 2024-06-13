package com.bottender.robar2

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MenuDetailFragment : DialogFragment() {

    private lateinit var menu: Menu
    private var position: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_menu_detail, container, false)

        val menuName = arguments?.getString("menuName") ?: ""
        val water = arguments?.getInt("water") ?: 0
        val cola = arguments?.getInt("cola") ?: 0
        val cider = arguments?.getInt("cider") ?: 0
        val orange = arguments?.getInt("orange") ?: 0
        val isDefault = arguments?.getBoolean("isDefault") ?: false
        position = arguments?.getInt("position") ?: -1

        menu = Menu(menuName, water, cola, cider, orange, isDefault)
        updateUI(view)

        val orderButton: Button = view.findViewById(R.id.orderButton)
        val editButton: Button = view.findViewById(R.id.editButton)

        orderButton.setOnClickListener {
            showOrderConfirmationDialog()
        }

        if (isDefault) {
            editButton.isEnabled = false
        } else {
            editButton.setOnClickListener {
                val editMenuFragment = EditMenuFragment.newInstance(menu, position)
                editMenuFragment.setTargetFragment(this, REQUEST_CODE_EDIT_MENU)
                editMenuFragment.show(parentFragmentManager, "EditMenuFragment")
            }
        }

        return view
    }

    fun onMenuUpdated(updatedMenu: Menu, position: Int) {
        menu = updatedMenu
        updateUI(view!!)
        (activity as? MainActivity)?.updateMenu(updatedMenu, position)
    }

    private fun updateUI(view: View) {
        val menuTextView: TextView = view.findViewById(R.id.menuDetailTextView)
        val waterTextView: TextView = view.findViewById(R.id.waterTextView)
        val colaTextView: TextView = view.findViewById(R.id.colaTextView)
        val ciderTextView: TextView = view.findViewById(R.id.ciderTextView)
        val orangeTextView: TextView = view.findViewById(R.id.orangeTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)

        menuTextView.text = menu.name
        waterTextView.text = "물: ${menu.water} ml"
        colaTextView.text = "콜라: ${menu.cola} ml"
        ciderTextView.text = "사이다: ${menu.cider} ml"
        orangeTextView.text = "오렌지주스: ${menu.orange} ml"
        priceTextView.text = "가격: ${menu.calculatePrice()} 원"
    }

    private fun showOrderConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("결제하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                showOrderCompletionDialog()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showOrderCompletionDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("주문이 완료되었습니다.")
            .setPositiveButton("확인", null)
            .show()
    }

    companion object {
        const val REQUEST_CODE_EDIT_MENU = 2

        fun newInstance(menu: Menu, position: Int): MenuDetailFragment {
            val args = Bundle().apply {
                putString("menuName", menu.name)
                putInt("water", menu.water)
                putInt("cola", menu.cola)
                putInt("cider", menu.cider)
                putInt("orange", menu.orange)
                putBoolean("isDefault", menu.isDefault)
                putInt("position", position)
            }
            return MenuDetailFragment().apply {
                arguments = args
            }
        }
    }
}
