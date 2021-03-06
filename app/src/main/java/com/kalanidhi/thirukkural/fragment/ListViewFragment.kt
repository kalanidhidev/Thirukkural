package com.kalanidhi.thirukkural.fragment

import android.app.Activity
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kalanidhi.thirukkural.R
import com.kalanidhi.thirukkural.activity.Home
import com.kalanidhi.thirukkural.utilities.KuralViewAdaptor
import android.speech.tts.TextToSpeech
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.huma.room_for_asset.RoomAsset
import com.kalanidhi.thirukkural.db.*
import io.reactivex.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


class ListViewFragment : Fragment(), KuralViewAdaptor.FavButtonListener {

    var currentAdhigaram = 1;
    private var parentView: View? = null
    var kuralViewAdaptor: KuralViewAdaptor? = null
    var parentActivity: Home? = null;

    var list_view: ListView? = null;
    var title_view: TextView? = null;
    var tts: TextToSpeech? = null


    var database: KuralDatabase? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater!!.inflate(R.layout.activity_list_view_fragment, container, false)
        setRetainInstance(true);

        currentAdhigaram = arguments.getInt("adhigaram")
        parentActivity = activity as Home
        title_view = parentView!!.findViewById<View>(R.id.title_name) as TextView
        list_view = parentView!!.findViewById<View>(R.id.list_view) as ListView


        val MIGRATION_1_2 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("BKS", "CALEED")
                database.execSQL("ALTER TABLE 'kuraldata' ADD COLUMN 'fav' INTEGER NOT NULL DEFAULT 0")
                Log.d("BKS", "CALEED")
            }
        }
        database = RoomAsset.databaseBuilder(parentActivity!!.applicationContext, KuralDatabase::class.java, "thirukkural.db").addMigrations(MIGRATION_1_2).allowMainThreadQueries().build()

        if ( currentAdhigaram == 135 ) getFavKural() else  getKural(currentAdhigaram)

        val navigation = parentView!!.findViewById<View>(R.id.navigation) as BottomNavigationView

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        lateinit var mAdView: AdView

        MobileAds.initialize(parentActivity, "ca-app-pub-8220895136296603~2956082742")

        mAdView = parentView!!.findViewById(R.id.adView)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {

                Log.d("BKS", "Add loaded")
            }

            override fun onAdFailedToLoad(errorCode: Int) {

                mAdView.visibility = View.GONE
                var linear = parentView!!.findViewById<View>(R.id.footerlayout) as LinearLayout
                var lp = linear.layoutParams as RelativeLayout.LayoutParams
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                linear.setLayoutParams(lp);


                Log.d("BKS", "Add Error" + errorCode)


                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                Log.d("BKS", "Add Opended")

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                Log.d("BKS", "Add Left")

                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                Log.d("BKS", "Add Closed")

                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        }


        /**
         * On Item click listener , When click the any one of thirukkural, this function
         * Will be fired and do the further operation
         */

        list_view!!.setOnItemClickListener { adapterView, view, i, l ->

            val kuralMeaning: TextView = (view.findViewById<TextView>(R.id.kural_meaning_ta) as TextView);
            val arrowImage: ImageView = (view.findViewById<ImageView>(R.id.arrow) as ImageView);

            if (kuralMeaning.visibility == View.VISIBLE) {
                arrowImage.setImageResource(R.drawable.down)
                kuralMeaning.visibility = View.GONE
            } else {
                arrowImage.setImageResource(R.drawable.up)
                kuralMeaning.visibility = View.VISIBLE
            }
        }

        return parentView
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        parentActivity = activity as Home

        when (item.itemId) {

            R.id.pre -> {

                if (currentAdhigaram != 1) {
                    currentAdhigaram--;
                    getKural(currentAdhigaram)
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.search -> {

                listening()
                return@OnNavigationItemSelectedListener true
            }
            R.id.next -> {

                if (currentAdhigaram < 133) {
                    currentAdhigaram++;
                    getKural(currentAdhigaram)
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    fun listening() {

        var parentActivity = activity as Home

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN")

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "குறளின் ஏதேனும் ஒரு வார்த்தை...")

        try {
            startActivityForResult(intent, 1)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(parentActivity, "Speech not support", Toast.LENGTH_SHORT).show()
        }

    }


    /**
     * Receiving speech input
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        parentActivity = activity as Home

        when (requestCode) {

            1 -> {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    getKuralBySearchWord(result[0])

                }
            }
        }
    }

    fun getKural(adhigaram: Int) {


        val string = adhigaram.toString() + ".";

        title_view!!.setText("$string" + activity.resources.getStringArray(R.array.adhigaaram).get(adhigaram - 1))
        val start: Int = (adhigaram * 10) - 9

        val end: Int = (adhigaram * 10)


        val observalble: Flowable<List<KuralData>>? = database!!.kuralDao()
                .getKuralByAdhigaram(start, end).subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())

        observalble?.subscribe { kuralData ->

            // Log.d("BKS",kuralData.get(0).kural)

            kuralViewAdaptor = KuralViewAdaptor(parentActivity!!, kuralData)
            kuralViewAdaptor?.setFavButtonListner(this)
            list_view!!.adapter = kuralViewAdaptor
            list_view!!.divider = null


        }
    }


        fun getFavKural() {

            title_view!!.setText("பிடித்தவை")


            val observalble: Flowable<List<KuralData>>? = database!!.kuralDao()
                    .getFavKural().subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())

            observalble?.subscribe { kuralData ->

                if ( kuralData.size == 0 ) {
                    Toast.makeText(context, "பிடித்தவை பட்டியல் காலியாக உள்ளது...", Toast.LENGTH_SHORT).show()
                    return@subscribe
                }


                kuralViewAdaptor = KuralViewAdaptor(parentActivity!!, kuralData)
                kuralViewAdaptor?.setFavButtonListner(this)
                list_view!!.adapter = kuralViewAdaptor
                list_view!!.divider = null


            }
        }


        /*
        database!!.kuralDao()
                .getKuralByAdhigaram(start, end).subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { kuralData ->

                  //  var getKuralViewModel = ViewModelProviders.of(parentActivity!!).get(GetKuralViewModel::class.java);
                   // getKuralViewModel.select(kuralData)
                    kuralViewAdaptor = KuralViewAdaptor(parentActivity!!, kuralData)
                    //kuralViewAdaptor = KuralViewAdaptor(activity, getKuralViewModel.getKuralData(kuralData))

                    list_view!!.adapter = kuralViewAdaptor
                    list_view!!.divider = null

                }*/

    fun getKuralBySearchWord(word: String) {

        title_view!!.setText("\"" + word + "\"")
        var searchWord = "%" + word + "%"

        database!!.kuralDao()
                .getKuralBySearchWord(searchWord).subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { kuralData ->
                    kuralViewAdaptor = KuralViewAdaptor(parentActivity!!, kuralData)
                    list_view!!.adapter = kuralViewAdaptor
                    list_view!!.divider = null

                }
    }


    override fun onButtonClickListner(kuralData: KuralData) {

        if ( kuralData.fav == 1 ) {
            Toast.makeText(context, "பிடித்தவை பட்டியலில் சேர்க்கப்பட்டுள்ளது",Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(context, "பிடித்தவை பட்டியலில் இருந்து நீக்கப்பட்டுவிட்டது",Toast.LENGTH_SHORT).show()
        }

        database?.kuralDao()?.updateFavKural(kuralData)

    }



}


