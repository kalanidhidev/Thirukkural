package com.kalanidhi.thirukkural.fragment

import android.content.Intent

import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ListView
import android.widget.TextView
import com.huma.room_for_asset.RoomAsset

import com.kalanidhi.thirukkural.R
import com.kalanidhi.thirukkural.activity.Home
import com.kalanidhi.thirukkural.activity.VideoPlayer
import com.kalanidhi.thirukkural.db.KuralDatabase
import com.kalanidhi.thirukkural.utilities.Configuration
import com.kalanidhi.thirukkural.utilities.KuralViewAdaptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class VideoFragment : Fragment() {

    var kuralViewAdaptor: KuralViewAdaptor? = null
    var parentActivity: Home? = null;
    private var parentView: View? = null
    var list_view: ListView? = null;


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater!!.inflate(R.layout.fragment_video, container, false)


        parentActivity = activity as Home
        list_view = parentView!!.findViewById<View>(R.id.list_view) as ListView


        getKuralByVideo()

        list_view!!.setOnItemClickListener { adapterView, view, i, l ->


            var no = (view.findViewById<TextView>(R.id.kural_no) as TextView).text.toString()

            startActivity(Intent(parentActivity, VideoPlayer::class.java).putExtra("no",no))
           // startActivity(Intent(parentActivity, NotificationView::class.java).putExtra("no",no))
        }
        return parentView
    }

    fun getKuralByVideo() {

        RoomAsset.databaseBuilder(parentActivity!!.applicationContext, KuralDatabase::class.java, "thirukkural.db").build().kuralDao()
        .getKuralByVideo(Configuration.VIDEO).subscribeOn(Schedulers.io())
         ?.observeOn(AndroidSchedulers.mainThread())
          ?.subscribe { kuralData ->
                    kuralViewAdaptor = KuralViewAdaptor(parentActivity!!, kuralData)
                    list_view!!.adapter = kuralViewAdaptor
                    list_view!!.divider = null
                }
    }


}// Required empty public constructor
