package com.kalanidhi.thirukkural.utilities

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by MTAJ-08 on 10/27/2016.
 */
class KuralView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    init {
        this.typeface = Typeface.createFromAsset(context.assets, "font/litsansmedium.otf")
    }

}
