package com.giuldev.ecowatcher.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giuldev.ecowatcher.data.local.dao.AssetDao
import com.giuldev.ecowatcher.data.local.entity.AssetEntity

/**
 * Classe principal do banco de dados Room do EcoWatcher.
 * Configura as entidades da aplicação e expõe os Daos.
 */
@Database(entities = [AssetEntity::class], version = 2, exportSchema = false)
abstract class EcoWatcherDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
}
