package com.kalanidhi.thirukkural.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by kalanidhi on 2/5/18.
*/

@Database(entities = arrayOf(KuralData::class), version = 2)
abstract class KuralDatabase : RoomDatabase() {
    abstract fun kuralDao(): KuralDao

}
