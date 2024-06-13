package com.bottender.robar2

import android.content.Context
import android.widget.Toast
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException

class OrderManager(private val context: Context) {

    fun createOrderFile(menuName: String, drinks: List<Int>) {
        val jsonObject = JSONObject()
        jsonObject.put("menu", menuName)
        for (i in drinks.indices) {
            jsonObject.put("drink${i + 1}", drinks[i])
        }

        val orderFile = File(context.filesDir, "order.txt")
        try {
            FileWriter(orderFile).use { it.write(jsonObject.toString()) }
            Toast.makeText(context, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            // 블루투스 통신으로 파일 송신 로직 추가
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "파일 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
