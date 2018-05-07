package com.kalanidhi.thirukkural.db

import android.arch.persistence.room.*
import io.reactivex.Flowable


/**
 * Created by root on 10/7/17.
 */

@Dao
interface  KuralDao {

    @Query("select * from kuralData where kuralNo between :start and :end")
    fun getKuralByAdhigaram(start : Int ,end : Int) : Flowable<List<KuralData>>

    @Query("select * from kuralData where kural LIKE :word")
    fun getKuralBySearchWord(word : String) : Flowable<List<KuralData>>


    @Query("SELECT * FROM kuralData WHERE kuralNo IN (:kuralNo)")
    fun getKuralByVideo(kuralNo: IntArray): Flowable<List<KuralData>>

    @Query("SELECT * FROM kuralData ORDER BY RANDOM() LIMIT 1;")
    fun getDailyKural(): Flowable<KuralData>
}
