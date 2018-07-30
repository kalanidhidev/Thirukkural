package com.kalanidhi.thirukkural.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration

/**
 * Created by kalanidhi on 2/5/18.
*/

@Database(entities = arrayOf(KuralData::class), version = 3 )
abstract class KuralDatabase : RoomDatabase() {
    abstract fun kuralDao(): KuralDao

}
