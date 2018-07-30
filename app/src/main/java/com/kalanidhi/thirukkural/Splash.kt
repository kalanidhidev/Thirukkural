package com.kalanidhi.thirukkural

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kalanidhi.thirukkural.activity.Home

import android.app.AlarmManager
import android.app.PendingIntent
import com.kalanidhi.thirukkural.notification.AlarmReceiver
import java.util.*
import android.content.res.Configuration
import android.os.Build



class Splash : AppCompatActivity() {

    // var db: KuralDatabase? = null
    // private var scheduleClient: ScheduleClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val locale = Locale("ta")
        Locale.setDefault(locale)

        val res = getResources()
        val config = Configuration(res.getConfiguration())
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.getDisplayMetrics())
        }

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
            alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi)


        }


        var handler: Handler?

        handler = Handler()

        handler.postDelayed(Runnable {
            if (!isFinishing) {

                val intent = Intent(applicationContext, Home::class.java)
                intent.putExtra("adhigaram", 1)

                startActivity(intent)
                finish()
            }
        }, 3000);


    }

}