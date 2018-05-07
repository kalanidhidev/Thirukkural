package com.kalanidhi.thirukkural.utilities

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kalanidhi.thirukkural.R
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.util.TypedValue
import com.kalanidhi.thirukkural.db.KuralData


/**
 * Created by root on 19/2/18.
 */
class KuralViewAdaptor : BaseAdapter {
    private var data : List<KuralData> = ArrayList<KuralData>()
    private var context: Context;

    constructor(context: Context, data: List<KuralData>) : super() {
        this.data = data;
        this.context = context;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View;
        var viewHolder: ViewHolder?

        if (convertView == null) {

            view = LayoutInflater.from(context).inflate(R.layout.kural_card_view, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.kural.text = data[position].kural
        viewHolder.kuralNo.text = data[position].kuralNo.toString()
        viewHolder.explanation.text= data[position].explanation

        if (data[position].kuralNo.toString().length == 3 ){
            viewHolder.kuralNo.setTextSize(TypedValue.COMPLEX_UNIT_SP,20F)
        }else if ( data[position].kuralNo.toString().length == 4 )
        {
            viewHolder.kuralNo.setTextSize(TypedValue.COMPLEX_UNIT_SP,16F)

        }

        val myCustomFont: Typeface? = ResourcesCompat.getFont(context, R.font.litsansmedium  )

        viewHolder.kural!!.setTypeface(myCustomFont, Typeface.NORMAL)

        val gra: GradientDrawable = ((viewHolder.kuralNo.background as GradientDrawable));

        val color = Color.parseColor("#03a9f4")

        (viewHolder.kuralNo.getBackground() as GradientDrawable).setColor(color)

        return view;

    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }


    /**
     * Bind the new data to view holder
     */
    private class ViewHolder(view: View?) {
        var kuralNo: TextView
        var kural: TextView
        var explanation: TextView


        //  if you target API 26, you should change to:
        init {
            this.kural = view?.findViewById<TextView>(R.id.kural) as TextView
            this.kuralNo = view.findViewById<TextView>(R.id.kural_no) as TextView
            this.explanation = view.findViewById<TextView>(R.id.kural_meaning_ta) as TextView

        }
    }

}