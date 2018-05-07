package com.kalanidhi.thirukkural.utilities

import android.content.Context
import android.util.Log
import com.kalanidhi.thirukkural.R
import java.util.*
import kotlin.collections.ArrayList


class DataDump {
    companion object {
        fun getData(context: Context, isPaal: Boolean): HashMap<String, List<String>> {


            val b = HashMap<String, List<String>>()
            if (isPaal) {
                for (data in context.resources.getStringArray(R.array.paal).indices) {

                    if (data == 0) {
                        var a = Arrays.copyOfRange(context.resources.getStringArray(R.array.adhigaaram), 0, 38).asList()
                        b.put(context.resources.getStringArray(R.array.paal).get(data), a);


                    } else {

                        var a = Arrays.copyOfRange(context.resources.getStringArray(R.array.adhigaaram), Configuration.MUPPAL_END[data - 1], (Configuration.MUPPAL_END[data])).asList()
                        b.put(context.resources.getStringArray(R.array.paal).get(data), a);
                    }

                }

            } else {
                for (data in context.resources.getStringArray(R.array.iyal).indices) {

                                     if (data == 0) {
                        var a = Arrays.copyOfRange(context.resources.getStringArray(R.array.adhigaaram), 0, 4).asList()
                        b.put(context.resources.getStringArray(R.array.iyal).get(data), a);


                    } else {
                        var a = Arrays.copyOfRange(context.resources.getStringArray(R.array.adhigaaram), Configuration.IYAL_END[data - 1], (Configuration.IYAL_END[data])).asList()
                        b.put(context.resources.getStringArray(R.array.iyal).get(data), a);
                    }

                }
            }

           return b;

        }
    }

}