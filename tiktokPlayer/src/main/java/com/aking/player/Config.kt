package com.aking.player

/**
 * Created by Rick on 2023-11-29  11:17.<p>
 *
 * Description:
 */
object Config {
    /**
     * 循环复用的播放器的总数量
     */
    const val LOOP_PLAYER_SIZE = 3

    /**
     * 视频缓存目录
     */
    const val PLAYER_CACHE_DIR = "media3_cache"

    /**
     * 缓冲参数
     */
    const val MIN_BUFFER_MS = 5_000 // 最小缓冲时间，
    const val MAX_BUFFER_MS = 7_000 // 最大缓冲时间
    const val PLAYBACK_BUFFER_MS = 700 // 最小播放缓冲时间，只有缓冲到达这个时间后才是可播放状态(ExoPlayer 将在至少拥有此数量的缓冲数据才能开始播放)
    const val RE_BUFFER_MS = 1_000 // 当缓冲用完，再次缓冲的时间
}