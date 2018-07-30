package com.kalanidhi.thirukkural.utilities

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kalanidhi.thirukkural.R
import android.util.TypedValue
import com.kalanidhi.thirukkural.db.KuralData
import com.like.LikeButton


/**
 * Created by root on 19/2/18.
 */
class KuralViewAdaptor : BaseAdapter  {
     var data : List<KuralData> = ArrayList<KuralData>()
     var filterData : List<KuralData> = ArrayList<KuralData>()

     var context: Context;
      var favButtonListener : FavButtonListener? = null


    constructor(context: Context, data: List<KuralData>) : super() {
        this.data = data;
        this.context = context;
        this.filterData= data ;
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

        viewHolder.fav.isLiked= if (data[position].fav == 0 )  false  else  true

        viewHolder.fav.setOnClickListener {

            viewHolder.fav.isLiked=if ( viewHolder.fav.isLiked ) false else true ;
            data[position].fav=if ( viewHolder.fav.isLiked ) 1 else 0  ;

            //favButtonListener?.onButtonClickListner(viewHolder.kuralNo.text.toString(),if (viewHolder.fav.isLiked) 1 else 0 )
            favButtonListener?.onButtonClickListner( data[position])

        }

        if (data[position].kuralNo.toString().length == 3 ){
            viewHolder.kuralNo.setTextSize(TypedValue.COMPLEX_UNIT_SP,20F)
        }else if ( data[position].kuralNo.toString().length == 4 )
        {
            viewHolder.kuralNo.setTextSize(TypedValue.COMPLEX_UNIT_SP,16F)

        }

      //  val myCustomFont: Typeface? = ResourcesCompat.getFont(context, R.font.litsansmedium  )

        //viewHolder.kural!!.setTypeface(myCustomFont, Typeface.NORMAL)

        val gra: GradientDrawable = ((viewHolder.kuralNo.background as GradientDrawable));

        //val color = Color.parseColor(context.getColor(R.color.colorPrimary))

        (viewHolder.kuralNo.getBackground() as GradientDrawable).setColor(context.getColor(R.color.colorPrimaryDark))

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
    Bind the new_file data to view holder
     */
    private class ViewHolder(view: View?) {
        var kuralNo: TextView
        var kural: KuralView
        var explanation: KuralView
        var fav : LikeButton



        //  if you target API 26, you should change to:
        init {
            this.kural = view?.findViewById<KuralView>(R.id.kural) as KuralView
            this.kuralNo = view.findViewById<TextView>(R.id.kural_no) as TextView
            this.explanation = view.findViewById<KuralView>(R.id.kural_meaning_ta) as KuralView
            this.fav = view.findViewById<LikeButton>(R.id.fav) as LikeButton



        }
    }

    fun setFavButtonListner(listener: FavButtonListener) {
        this.favButtonListener  = listener
    }

    interface FavButtonListener {
        //fun onButtonClickListner(kuralNo: String,liked : Int)
        fun onButtonClickListner(kuralData: KuralData)
    }


}