package com.giuldev.ecowatcher.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giuldev.ecowatcher.data.local.entity.AssetEntity

/**
 * Data Access Object (DAO) para a tabela de 'assets'.
 * Define as operações de banco de dados para os ativos no Room.
 */
@Dao
interface AssetDao {
    @Query("SELECT * FROM assets")
    suspend fun getAllAssets(): List<AssetEntity>

    @Query("SELECT * FROM assets WHERE name LIKE '%' || :query || '%' OR symbol LIKE '%' || :query || '%'")
    suspend fun searchAssets(query: String): List<AssetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssets(assets: List<AssetEntity>)

    @Query("DELETE FROM assets")
    suspend fun clearAssets()
}
