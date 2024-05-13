package com.bottender.robar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserMenuDao {
    @Query("SELECT * FROM userMenu")
    fun getAllUserMenu(): List<UserMenu>

    @Insert
    fun insertUserMenu(userMenu: UserMenu)

    @Update
    fun updateUserMenu(userMenu: UserMenu)

    @Delete
    fun deleteUserMenu(userMenu: UserMenu)
}