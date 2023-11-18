package com.aking.tiktop.httpclient.ext

import android.util.Log
import com.aking.data.ApiResponse
import com.aking.tiktop.httpclient.enums.ResCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 *  http 请求扩展
 */
fun <T> CoroutineScope.loadHttp(
    start: () -> Unit = {},
    request: suspend CoroutineScope.() -> ApiResponse<T>,
    resp: (T?) -> Unit,
    err: (String) -> Unit = {},
    end: () -> Unit = {}
) {
    launch {
        try {
            start()
            val data = request()
            Log.i("HttpClient", "data:$data")
            if (data.code == ResCode.OK.getCode()) {
                resp(data.data)
            } else {
                data.msg?.let { err(it) }
            }
        } catch (e: Exception) {
            err(e.message ?: "")  //可根据具体异常显示具体错误提示
            Log.e("HttpClient", "异常2:" + e.toString())
        } finally {
            end()
        }
    }
}

/**
 *  http 请求扩展
 */
fun <T> CoroutineScope.http(
    start: () -> Unit = {},
    request: suspend CoroutineScope.() -> ApiResponse<T>,
    resp: (T?) -> Unit,
    err: (String) -> Unit = {},
    end: () -> Unit = {}
):Job {
  return  launch {
        try {
            start()
            val data = request()
            if (data.code == ResCode.OK.getCode()) {
                resp(data.data)
            } else {
                data.msg?.let { err(it) }
            }
        } catch (e: Exception) {
            err(e.message ?: "")  //可根据具体异常显示具体错误提示
            Log.e("HttpClient", "异常2:$e")
        } finally {
            end()
        }
    }
}

