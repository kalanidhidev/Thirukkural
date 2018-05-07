package com.kalanidhi.thirukkural.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kalanidhi.thirukkural.R
import com.kalanidhi.thirukkural.activity.Home


/**
 * Created by kalanidhi on 4/4/18.
 */

class AdhigarmSearchFragment : Fragment() {

    private var parentView: View? = null
    var parentActivity: Home? = null;
    var list_view: ListView? = null
    var title_view: TextView? = null;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater!!.inflate(R.layout.adhigaram_search_fragment, container, false)

        parentActivity = activity as Home
        title_view = parentView!!.findViewById<View>(R.id.title_name) as TextView

        val adapter = ArrayAdapter<String>(parentActivity, android.R.layout.simple_list_item_1, parentActivity!!.resources.getStringArray(R.array.adhigaaram))

        list_view = parentView!!.findViewById<View>(R.id.list_view) as ListView
        list_view!!.adapter = adapter;

        /**
         * On Item click listener , When click the any one of thirukkural, this function
         * Will be fired and do the further operation
         */

        list_view!!.setOnItemClickListener { adapterView, view, i, l ->

            val bundle = Bundle()
            bundle.putInt("adhigaram", i + 1)
            var listFragment = ListViewFragment();
            listFragment.arguments = bundle;

            Log.d("BKS", i.toString())
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, listFragment, "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()

        }

        return parentView
    }

}