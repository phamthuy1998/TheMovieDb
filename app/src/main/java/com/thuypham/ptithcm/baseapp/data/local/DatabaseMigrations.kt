package com.thuypham.ptithcm.baseapp.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    const val DB_VERSION = 1

    private val MIGRATION_1_2: Migration = object : Migration(8, 9) {
        override fun migrate(database: SupportSQLiteDatabase) {

        }
    }
    val MIGRATIONS: Array<Migration> = arrayOf(MIGRATION_1_2)

}