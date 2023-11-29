package com.aking.player.exo

import android.annotation.SuppressLint
import android.widget.MediaController.MediaPlayerControl
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

/**
 * Created by Rick on 2023-11-29  11:40.<p>
 *
 * Description:
 */
class ExoPlayerController(private val player: ExoPlayer) : MediaPlayerControl {
    override fun start() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun getDuration(): Int {
        return player.duration.toInt()
    }

    override fun getCurrentPosition(): Int {
        return player.currentPosition.toInt()
    }

    override fun seekTo(pos: Int) {
        player.seekTo(pos.toLong())
    }

    override fun isPlaying(): Boolean {
        return player.isPlaying
    }

    override fun getBufferPercentage(): Int {
        return player.bufferedPercentage
    }

    override fun canPause(): Boolean {
        return player.isCommandAvailable(Player.COMMAND_PLAY_PAUSE)
    }

    override fun canSeekBackward(): Boolean {
        return player.isCommandAvailable(Player.COMMAND_SEEK_BACK)
    }

    override fun canSeekForward(): Boolean {
        return player.isCommandAvailable(Player.COMMAND_SEEK_FORWARD)
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun getAudioSessionId(): Int {
        return player.audioSessionId
    }
}