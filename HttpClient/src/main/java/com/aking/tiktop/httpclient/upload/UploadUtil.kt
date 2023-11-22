package com.aking.tiktop.httpclient.upload

import android.content.Context
import android.util.Log
import com.aking.tiktop.httpclient.config.AppConfig
import com.tencent.cos.xml.exception.CosXmlClientException
import com.tencent.cos.xml.exception.CosXmlServiceException
import com.tencent.cos.xml.listener.CosXmlProgressListener
import com.tencent.cos.xml.listener.CosXmlResultListener
import com.tencent.cos.xml.model.CosXmlRequest
import com.tencent.cos.xml.model.CosXmlResult
import com.tencent.cos.xml.transfer.TransferConfig
import com.tencent.cos.xml.transfer.TransferManager
import com.tencent.cos.xml.transfer.TransferState
import com.tencent.cos.xml.transfer.TransferStateListener


class UploadUtil(context: Context, uploadListListener: MutableList<UploadListener>? = ArrayList()) : CosXmlProgressListener, CosXmlResultListener,
    TransferStateListener {

    private val TAG = "UploadUtil"

    var curUploadPath: String ?= null

    var mContext: Context = context

    var uploadListListener : MutableList<UploadListener> = ArrayList()

    companion object {
        var instance: UploadUtil? = null
        fun init(context: Context) {
            instance = UploadUtil(context)
        }
    }

    fun uploadFile(localPath: String) {
        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        val transferConfig = TransferConfig.Builder().build()
        val transferManager = TransferManager(
            CosUploadService.getInstance(mContext).cosXmlService,
            transferConfig)
        // 上传文件
        val cosXmlUploadTask  = transferManager.upload(AppConfig.BUCKETNAME, localPath, localPath, null)
        cosXmlUploadTask.setCosXmlProgressListener(this)
        cosXmlUploadTask.setCosXmlResultListener(this)
        cosXmlUploadTask.setTransferStateListener(this)
    }

    fun registerUploadListener(uploadListener: UploadListener) {
        if (!uploadListListener.contains(uploadListener)) {
            uploadListListener.add(uploadListener)
        }
    }

    fun unRegisterUploadListener(uploadListener: UploadListener) {
        if (uploadListListener.contains(uploadListener)) {
            uploadListListener.remove(uploadListener)
        }
    }

    override fun onProgress(progress: Long, total: Long) {
        Log.i(TAG, "onProgress progress = $progress  total = $total")
        uploadListListener.forEach {
            it.onProgress(progress, total)
        }
    }

    override fun onSuccess(request: CosXmlRequest?, result: CosXmlResult?) {
        Log.i(TAG, "onSuccess = $result")
        uploadListListener.forEach {
            it.onFinish(result!!.accessUrl)
        }
    }

    override fun onFail(request: CosXmlRequest?, clientException: CosXmlClientException?, serviceException: CosXmlServiceException?) {
        if (clientException != null) {
            clientException.printStackTrace();
        } else {
            serviceException!!.printStackTrace();
        }
        uploadListListener.forEach {
            it.onFail()
        }
    }

    override fun onStateChanged(state: TransferState?) {
        Log.i(TAG, "onStateChanged = $state")
    }

    interface UploadListener{
        fun onProgress(progress: Long, total: Long)
        fun onFinish(remotePath: String)
        fun onFail()
    }
}