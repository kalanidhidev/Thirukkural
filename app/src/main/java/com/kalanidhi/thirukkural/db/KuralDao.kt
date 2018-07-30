package com.kalanidhi.thirukkural.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Created by Kalanidhi on 10/7/17.
 */

@Dao
interface KuralDao {

    @Query("select * from kuralData where kuralNo between :start and :end")
    fun getKuralByAdhigaram(start: Int, end: Int): Flowable<List<KuralData>>

    @Query("select * from kuralData where kural LIKE :word")
    fun getKuralBySearchWord(word: String): Flowable<List<KuralData>>


    @Query("SELECT * FROM kuralData WHERE kuralNo IN (:kuralNo)")
    fun getKuralByVideo(kuralNo: IntArray): Flowable<List<KuralData>>

    @Query("SELECT * FROM kuralData ORDER BY RANDOM() LIMIT 1;")
    fun getDailyKural(): Flowable<KuralData>


    @Query("SELECT * FROM kuralData WHERE fav = 1;")
    fun getFavKural(): Flowable<List<KuralData>>

    //   @Query("UPDATE   kuralData  SET fav = (:liked) WHERE kuralNo = (:kuralNo)")
    //  fun updateFavKural(kuralNo: Int,liked : Int)

    @Update
    fun updateFavKural(kuralData: KuralData)
}
