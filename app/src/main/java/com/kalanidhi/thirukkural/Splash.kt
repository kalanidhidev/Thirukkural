package com.kalanidhi.thirukkural

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kalanidhi.thirukkural.activity.Home
import com.kalanidhi.thirukkural.db.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern
import android.app.AlarmManager
import android.app.PendingIntent
import android.os.SystemClock
import com.kalanidhi.thirukkural.notification.AlarmReceiver
import java.util.*


class Splash : AppCompatActivity() {

   // var db: KuralDatabase? = null
    // private var scheduleClient: ScheduleClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       val myIntent = Intent(this, AlarmReceiver::class.java)

       val isWorking = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_NO_CREATE) != null
       if (isWorking) {
           Log.d("alarm", "is working")
       } else {
           Log.d("alarm", "is not working")
       }

       if (!isWorking) {

           val calendar = Calendar.getInstance()
           calendar.setTimeInMillis(System.currentTimeMillis())
           calendar.set(Calendar.HOUR_OF_DAY, 9)

           val intentAlarm = Intent(this, AlarmReceiver::class.java)
           println("calling Alarm receiver ")
           //set the notification to repeat every fifteen minutes
           //val startTime = (1 * 60 * 1000).toLong() // 2 min
           // set unique id to the pending item, so we can call it when needed
           val pi = PendingIntent.getBroadcast(this, 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
           val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
           //alarmMgr.setInexactRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi)
           alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi)

           /*
           val intentAlarm = Intent(this, AlarmReceiver::class.java)
           println("calling Alarm receiver ")
           val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
           //set the notification to repeat every fifteen minutes
           val startTime = (1 * 60 * 1000).toLong() // 2 min
           // set unique id to the pending item, so we can call it when needed
           val pi = PendingIntent.getBroadcast(this, 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
           alarmManager.setInexactRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi)
           */
       }

        /*

        if (!Storage.loadData("db", applicationContext).equals("y")) {

            db = Room.databaseBuilder(this, KuralDatabase::class.java, "database-name").build();

            Observable.create(ObservableOnSubscribe<ArrayList<KuralData>> { e ->

                var data = ArrayList<KuralData>()
                Log.d("Bks", "Started")
                for (a in 0..1329) {
                    data.add(KuralData(resources.getStringArray(R.array.list_of_kural).get(a), resources.getStringArray(R.array.mu_va).get(a), 0))
                }
                Log.d("Bks", "Stopped")
                Storage.saveData("db", "y", applicationContext)
                e.onNext(data)
                e.onComplete()

            })
                    .subscribeOn(Schedulers.io())
                    //each subscription is going to be on a new thread.
                    .observeOn(AndroidSchedulers.mainThread()).
                    subscribe(
                            object : DisposableObserver<ArrayList<KuralData>>() {

                                override fun onNext(response: ArrayList<KuralData>) {

                                    Single.fromCallable {
                                        db!!.kuralDao().insertAll(response)
                                    }.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe()

                                }

                                override fun onError(e: Throwable) {

                                }

                                override fun onComplete() {
                                    // dialog.dismiss()
                                }

                            })

        }

        // val calendar = Calendar.getInstance()
// 7.00 (7 AM)
        //calendar.set(Calendar.HOUR_OF_DAY, 18)
        // calendar.set(Calendar.MINUTE, 18)
        //  calendar.set(Calendar.SECOND, 0)
        //    scheduleClient = ScheduleClient(this);
        //      scheduleClient!!.doBindService();

//        scheduleClient!!.setAlarmForNotification(calendar);
        // val pi = PendingIntent.getService(applicationContext, 0, Intent(get, Your_Class::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        //val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //      AlarmManager.INTERVAL_DAY, pi)
*/
        var handler: Handler?

        handler = Handler()

        handler.postDelayed(Runnable {
            if (!isFinishing) {

                val intent = Intent(applicationContext, Home::class.java)
                intent.putExtra("adhigaram",1)

                startActivity(intent)
                finish()
            }
        }, 3000);


    }
}