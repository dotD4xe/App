package com.example.applicationforcustomerinformation.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query("SELECT * from item ORDER BY id DESC")
    fun getItems(): Flow<List<Item>>
}