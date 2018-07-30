package com.kalanidhi.thirukkural.activity

import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.kalanidhi.thirukkural.R
import kotlinx.android.synthetic.main.video_player.*
import org.json.JSONObject

class VideoPlayer : AppCompatActivity() {

    private var playbackPosition = 0
    private val rtspUrl= "https://www.youtube.com/watch?v=fVMZ3MuCr4s"
    private lateinit var mediaController: MediaController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_player)


        mediaController = MediaController(this)

        videoView.setOnPreparedListener {
            mediaController.setAnchorView(videoContainer)
            videoView.setMediaController(mediaController)
            videoView.seekTo(playbackPosition)
            videoView.start()
        }

        videoView.setOnInfoListener { player, what, extras ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                progressBar.visibility = View.INVISIBLE
            true
        }
    }

    override fun onStart() {
        super.onStart()
       // val uri = Uri.parse(rtspUrl + intent.extras.getString("no") +".mp4")
         val uri = Uri.parse(rtspUrl)
        videoView.setVideoURI(uri)
        progressBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        videoView.pause()
        playbackPosition = videoView.currentPosition
    }

    override fun onStop() {
        videoView.stopPlayback()

        super.onStop()
    }

}

