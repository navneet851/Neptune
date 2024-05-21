package com.music.stream.neptune.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import com.music.stream.neptune.MainActivity
import com.music.stream.neptune.R
import com.music.stream.neptune.data.entity.SongsModel
import com.music.stream.neptune.ui.notification.MusicPlayerController

class MusicPlayerService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var notificationManager: NotificationManager
    private var isPlaying = false
    private var currentSong: SongsModel? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
        createNotificationChannel()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> handlePlayPause()
            ACTION_PREVIOUS -> handlePrevious()
            ACTION_NEXT -> handleNext()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun initMediaPlayer() {
        // Initialize the MediaPlayer and set up the listeners
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startForegroundService() {
        val notificationLayout = RemoteViews(packageName , R.layout.notification_remote_view)
        val inflatedView = inflateComposableLayout(notificationLayout)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val playPauseIntent = getPendingIntent(ACTION_PLAY_PAUSE)
        val previousIntent = getPendingIntent(ACTION_PREVIOUS)
        val nextIntent = getPendingIntent(ACTION_NEXT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Now Playing")
            .setContentIntent(pendingIntent)
            .setCustomBigContentView(inflatedView)
            .addAction(R.drawable.ic_player_back, "Previous", previousIntent)
            .addAction(
                if (isPlaying) R.drawable.ic_playing else R.drawable.ic_paused,
                if (isPlaying) "Pause" else "Play",
                playPauseIntent
            )
            .addAction(R.drawable.ic_player_skip, "Next", nextIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun inflateComposableLayout(notificationLayout: RemoteViews): RemoteViews {
        val view = RemoteViews(packageName, R.layout.notification_remote_view)
        val composeView = ComposeView(this)
        composeView.setContent {
            MusicPlayerController(
                songName = currentSong?.title ?: "Unknown Song",
                artistName = currentSong?.singer ?: "Unknown Artist",
                isPlaying = false,
                onPlayPauseClick = ::handlePlayPause,
                onPreviousClick = ::handlePrevious,
                onNextClick = ::handleNext
            )
        }
        view.addView(R.id.compose_view, composeView)
        return view
    }

    private fun handlePlayPause() {
        if (isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
        isPlaying = !isPlaying
        updateNotification()
    }

    private fun handlePrevious() {
        // Handle previous song
        updateNotification()
    }

    private fun handleNext() {
        // Handle next song
        updateNotification()
    }

    private fun updateNotification() {
        startForegroundService()
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MusicPlayerService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        private const val CHANNEL_ID = "music_player_channel"
        private const val NOTIFICATION_ID = 1
        private const val ACTION_PLAY_PAUSE = "action_play_pause"
        private const val ACTION_PREVIOUS = "action_previous"
        private const val ACTION_NEXT = "action_next"
    }
}