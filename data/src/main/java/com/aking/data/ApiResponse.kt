package com.aking.data

import java.io.Serializable

/**
 * Created by Rick at 2023-11-16 22:41.
 * Description: 网络请求结果基类
 */
data class ApiResponse<out T>(
    val data: T,
    val code: String = "",
    val message: String? = "",
) : Serializable {

    val success get() = code == "200"

    /**
     * 拆箱
     */
    fun unwrap(): T {
        return data
    }

    fun isEmpty(): Boolean {
        return data == null || (data is List<*> && (data as List<*>).isEmpty())
    }

    inline fun doOnData(f: (T) -> Unit) {
        data?.let { f(it) }
    }
}