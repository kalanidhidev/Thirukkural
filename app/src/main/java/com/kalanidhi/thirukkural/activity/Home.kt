package com.kalanidhi.thirukkural.activity;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import com.special.ResideMenu.ResideMenu
import com.special.ResideMenu.ResideMenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.kalanidhi.thirukkural.R
import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import com.kalanidhi.thirukkural.fragment.*


class Home : FragmentActivity(), View.OnClickListener {

    var resideMenu: ResideMenu? = null
    var isBack = false
    private var mContext: Home? = null
    private var objAdhigaram: ResideMenuItem? = null
    private var objIyal: ResideMenuItem? = null
    private var objPaal: ResideMenuItem? = null
    private var objVideo: ResideMenuItem? = null
    private var objUpdate: ResideMenuItem? = null
    private var objFav: ResideMenuItem? = null



    /**
     * Called when the activity is first created.
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mContext = this
        setUpMenu()

        val bundle = Bundle()
        bundle.putInt("adhigaram", intent.extras.getInt("adhigaram"))
        var listFragment = ListViewFragment();
        listFragment.arguments = bundle;
        changeFragment(listFragment)

    }

    private fun setUpMenu() {


        resideMenu = ResideMenu(this)

        resideMenu!!.setBackground(R.drawable.menu_background)
        resideMenu!!.attachToActivity(this)
        resideMenu!!.setScaleValue(0.6f)

            // create menu items;
        objAdhigaram = ResideMenuItem(this, R.drawable.adhigaram, "அதிகாரங்கள்")
        objPaal = ResideMenuItem(this, R.drawable.paal, "பால்கள்")
        objIyal = ResideMenuItem(this, R.drawable.iyal, "இயல்கள்")
        objVideo = ResideMenuItem(this, R.drawable.video, "காணொளி")
        objFav = ResideMenuItem(this, R.drawable.fav, "பிடித்தவை")
        objUpdate = ResideMenuItem(this, R.drawable.update, "புதுப்பித்தல்")




        // itemSettings = ResideMenuItem(this, R.drawable.header_icon, "குறள் எண் தேடல்")
        objAdhigaram?.setOnClickListener(this)
        objIyal?.setOnClickListener(this)
        objPaal?.setOnClickListener(this)
        objVideo?.setOnClickListener(this)
        objUpdate?.setOnClickListener(this)
        objFav?.setOnClickListener(this)





        // itemSettings!!.setOnClickListener(this)

        resideMenu!!.addMenuItem(objAdhigaram, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(objIyal, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(objPaal, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(objVideo, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(objUpdate, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(objFav, ResideMenu.DIRECTION_LEFT)






        findViewById<View>(R.id.title_bar_left_menu).setOnClickListener { resideMenu!!.openMenu(ResideMenu.DIRECTION_LEFT) }

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return resideMenu!!.dispatchTouchEvent(ev)
    }

    override fun onClick(view: View) {

        if (view === objAdhigaram) {

            changeFragment(AdhigarmSearchFragment())

        } else if (view === objIyal) {
            val bundle = Bundle()
            bundle.putBoolean("isPall", false)
            var iyal = IyalSearch();
            iyal.arguments = bundle;
            changeFragment(iyal)

        } else if (view === objPaal) {
            val bundle = Bundle()
            bundle.putBoolean("isPall", true)
            var iyal = IyalSearch();
            iyal.arguments = bundle;
            changeFragment(iyal)

        } else if (view === objVideo) {
            changeFragment(VideoFragment())

        }
        else if (view === objFav) {

            val bundle = Bundle()
            bundle.putInt("adhigaram", 135)
            val listFragment = ListViewFragment();
            listFragment.arguments = bundle;
            changeFragment(listFragment)
        }
        else if (view === objUpdate) {

            try {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + mContext?.getPackageName())))
                //startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + mContext?.getPackageName())))
            }

        }

        resideMenu!!.closeMenu()
    }

    private fun changeFragment(targetFragment: Fragment) {
        resideMenu!!.clearIgnoredViewList()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    /**
     * When pressed back button, this function will be fired...
     */
    override fun onBackPressed() {
        if (isBack)
            android.os.Process.killProcess(android.os.Process.myPid());
        else {
            Toast.makeText(mContext, "வெளியேறுவதற்கு மீண்டும் அழுத்தவும்...", Toast.LENGTH_SHORT).show()
            isBack = true
        }

    }

}