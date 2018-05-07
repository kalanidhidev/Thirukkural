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
import com.kalanidhi.thirukkural.utilities.DataDump
import com.kalanidhi.thirukkural.utilities.ExpandableListAdapter
import com.kalanidhi.thirukkural.utilities.KuralViewAdaptor

import java.util.Arrays
import java.util.HashMap

/**
 * Created by kalanidhi on 4/4/18.
 */


class IyalSearch : Fragment() {

    internal var expandableListView: ExpandableListView? = null
    internal var expandableListAdapter: ExpandableListAdapter? = null
    internal var expandableListTitle: List<String>? = null
    internal var expandableListDetail: HashMap<String, List<String>>? = null


    private var parentView: View? = null

    var parentActivity: Home? = null;

    var list_view: ListView? = null
    var title_view: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater!!.inflate(R.layout.iyal_search_fragment, container, false)

        parentActivity = activity as Home
        title_view = parentView!!.findViewById<View>(R.id.title_name) as TextView
        expandableListView = parentView!!.findViewById<View>(R.id.expandableListView) as ExpandableListView

        if (arguments.getBoolean("isPall")) {
            title_view!!.text="பால்கள்"
            expandableListDetail = DataDump.getData(parentActivity!!.applicationContext, true)
            expandableListTitle = Arrays.asList(*resources.getStringArray(R.array.paal))
        } else {
            title_view!!.text="இயல்கள்"
            expandableListDetail = DataDump.getData(parentActivity!!, false)
            expandableListTitle = Arrays.asList(*resources.getStringArray(R.array.iyal))
        }

        expandableListAdapter = ExpandableListAdapter(parentActivity!!, expandableListTitle!!, expandableListDetail!!)
        expandableListView!!.setAdapter(expandableListAdapter)
        expandableListView!!.setOnGroupExpandListener { groupPosition ->

        }
        expandableListView!!.setOnGroupCollapseListener { groupPosition ->

        }
        expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

            for (data in parentActivity!!.resources.getStringArray(R.array.adhigaaram).indices) {
                if (parentActivity!!.resources.getStringArray(R.array.adhigaaram).get(data).equals(expandableListDetail!![expandableListTitle!![groupPosition]]!!.get(childPosition))) {
                    Log.d("Bks", expandableListDetail!![expandableListTitle!![groupPosition]]!!.get(childPosition))

                    val bundle = Bundle()
                    bundle.putInt("adhigaram", data + 1)
                    var listFragment = ListViewFragment();
                    listFragment.arguments = bundle;

                    Log.d("BKS",data.toString())
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, listFragment, "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                }
            }
            false
        }

        return parentView
    }



}
