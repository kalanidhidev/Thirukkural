package com.kalanidhi.thirukkural.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


/**
 * Created by root on 10/7/17.
 */


@Entity
class KuralData(@field:ColumnInfo(name = "kural") var kural: String,
                @field:ColumnInfo(name = "explanation") var explanation: String, @PrimaryKey var kuralNo: Int , @field:ColumnInfo(name = "fav") var fav: Int ) {

}



