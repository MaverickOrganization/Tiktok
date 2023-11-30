package com.aking.player.exo

import android.content.Context
import android.widget.MediaController
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.common.Player.RepeatMode
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import com.aking.player.features.IPlayer
import com.aking.player.features.IPlayerView
import com.aking.player.features.OnPlayerStateListener
import com.aking.player.features.State
import java.net.URL

/**
 * Created by Rick on 2023-11-29  10:58.<p>
 *
 * Description: 播放器Exo实现
 */
open class ExoMediaPlayer(context: Context, mediaSourceFactory: MediaSource.Factory) : IPlayer {
    val playerHolder = PlayerHolder(context, mediaSourceFactory)
    private val innerPlayer = playerHolder.player
    private val skipStates = listOf(Player.STATE_BUFFERING, Player.STATE_ENDED)

    override var playControl: MediaController.MediaPlayerControl = ExoPlayerController(innerPlayer)
    override var listener: OnPlayerStateListener? = null
        set(value) {
            field = value
            innerPlayer.addListener(playerListener)
        }
    override var url: URL? = null
        set(value) {
            field = value
            innerPlayer.setMediaItem(MediaItem.fromUri("$value"))
        }


    override fun play() {
        innerPlayer.play()
    }

    override fun prepare() {
        innerPlayer.prepare()
    }

    override fun pause() {
        innerPlayer.pause()
    }

    override fun stop() {
        innerPlayer.stop()
    }

    override fun seekTo(position: Long) {
        innerPlayer.seekTo(position)
    }

    override fun release() {
        innerPlayer.release()
    }

    override fun setRepeatMode(@RepeatMode repeatMode: Int) {
        innerPlayer.repeatMode = repeatMode
    }

    override fun attachPlayerView(view: IPlayerView<*>) {
        if (view is PlayerView) {
            view.player = innerPlayer
        } else {
            throw IllegalArgumentException("attachPlayerView view is not PlayerView: ${view.javaClass.simpleName}")
        }
    }

    private val playerListener by lazy {
        object : Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> listener?.onStateChange(State.End)
                    Player.STATE_BUFFERING -> listener?.onStateChange(State.Buffering)
                    Player.STATE_IDLE -> {}
                    Player.STATE_READY -> listener?.onStateChange(State.Ready)
                }
            }

            override fun onRenderedFirstFrame() {
                listener?.onStateChange(State.FirstFrameRendered)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    listener?.onStateChange(State.Playing)
                } else {
                    if (innerPlayer.playbackState !in skipStates && innerPlayer.playerError != null) {
                        listener?.onStateChange(State.Stop)
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                listener?.onStateChange(State.Error(error))
            }

        }
    }
}