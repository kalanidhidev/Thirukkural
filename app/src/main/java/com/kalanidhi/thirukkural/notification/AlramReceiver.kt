package com.kalanidhi.thirukkural.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast

import com.kalanidhi.thirukkural.R
import android.app.AlarmManager
import android.os.SystemClock
import android.support.v4.app.FragmentTransaction
import android.text.Html
import android.util.Log
import com.huma.room_for_asset.RoomAsset
import com.kalanidhi.thirukkural.activity.Home
import com.kalanidhi.thirukkural.db.KuralDatabase
import com.kalanidhi.thirukkural.utilities.KuralViewAdaptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by kalanidhi on 5/5/18.
 */

class AlarmReceiver : BroadcastReceiver() {

    internal var mNotificationId = 1

    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {

            val calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            calendar.set(Calendar.HOUR_OF_DAY, 9)

            val intentAlarm = Intent(context, AlarmReceiver::class.java)
            println("calling Alarm receiver ")
            //set the notification to repeat every fifteen minutes
            //val startTime = (1 * 60 * 1000).toLong() // 2 min
            // set unique id to the pending item, so we can call it when needed
            val pi = PendingIntent.getBroadcast(context, 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            //alarmMgr.setInexactRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi)
            alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi)

        } else {

            RoomAsset.databaseBuilder(context, KuralDatabase::class.java, "thirukkural.db").build()
                    .kuralDao()
                    .getDailyKural().subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe { kuralData ->

                        val mBuilder = NotificationCompat.Builder(context)
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher) // notification icon
                                .setContentTitle(context.resources.getText(R.string.app_name))
                                .setContentText(kuralData.kural)
                                .setStyle(NotificationCompat.BigTextStyle().bigText(kuralData.kural))
                                .setStyle(NotificationCompat.BigTextStyle().bigText(kuralData.kural + "\nவிளக்கம்:\n" + kuralData.explanation))
                                .setAutoCancel(true)
                                .setOngoing(true);

                        var adhigaram=(kuralData.kuralNo+(10-(kuralData.kuralNo%10)))/10
                        // clear notification after click

                        val resultIntent = Intent(context, Home::class.java)
                        resultIntent.putExtra("adhigaram",adhigaram)
                        val resultPendingIntent = PendingIntent.getActivity(context, mNotificationId, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                        mBuilder.setContentIntent(resultPendingIntent)
                        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        mNotificationManager.notify(mNotificationId, mBuilder.build())

                    }

            //   Toast.makeText(context, "AlarmReceiver", Toast.LENGTH_LONG).show()
            // Gets an instance of the NotificationManager service


        }
    }
}