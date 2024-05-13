package com.bottender.robar

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@Entity(tableName = "userMenu")
data class UserMenu(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name="menus_name")
    val menusName: String?
)