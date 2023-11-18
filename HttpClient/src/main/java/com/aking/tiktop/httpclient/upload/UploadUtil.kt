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
import com.tencent.cos.xml.transfer.COSXMLDownloadTask.COSXMLDownloadTaskResult
import com.tencent.cos.xml.transfer.TransferConfig
import com.tencent.cos.xml.transfer.TransferManager


class UploadUtil(context: Context) : CosXmlProgressListener, CosXmlResultListener {

    private var TAG = "UploadUtil"

    var curUploadPath: String ?= null

    var context: Context = context

    fun uploadFile(localPath: String) {
        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        curUploadPath =  localPath
        val transferConfig = TransferConfig.Builder().build()
        var transferManager = TransferManager(
            CosUploadService.getInstance(context).cosXmlService,
            transferConfig);

        // 上传文件
        val cosXmlUploadTask  = transferManager.upload(AppConfig.BUCKETNAME, localPath, localPath, null)
        cosXmlUploadTask.setCosXmlProgressListener(this)
        cosXmlUploadTask.setCosXmlResultListener(this)
    }

    override fun onProgress(progress: Long, total: Long) {
        Log.i(TAG, "onProgress progress = $progress  total = $total")
    }

    override fun onSuccess(request: CosXmlRequest?, result: CosXmlResult?) {
        TODO("Not yet implemented")
        val downloadTaskResult = result as COSXMLDownloadTaskResult
        Log.i(TAG, "onProgress progress = $downloadTaskResult")
    }

    override fun onFail(request: CosXmlRequest?, clientException: CosXmlClientException?, serviceException: CosXmlServiceException?) {
        if (clientException != null) {
            clientException.printStackTrace();
        } else {
            serviceException!!.printStackTrace();
        }

    }
}