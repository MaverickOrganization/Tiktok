package com.aking.tiktop.httpclient.upload

import android.content.Context
import com.aking.tiktop.httpclient.config.AppConfig
import com.tencent.cos.xml.CosXmlService
import com.tencent.cos.xml.CosXmlServiceConfig

class CosUploadService(val cosXmlService: CosXmlService) {

    companion object {
        var instance: CosUploadService? = null
        fun getInstance(context: Context): CosUploadService {
            if (instance == null) {
                synchronized(CosUploadService::class) {
                    if (instance == null) {
                        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
                        val serviceConfig = CosXmlServiceConfig.Builder()
                            .setRegion(AppConfig.BUCKET)
                            .builder();
                        val myCredentialProvider = MySessionCredentialProvider()
                        // 初始化 COS Service，获取实例
                        val cosService = CosXmlService(context, serviceConfig, myCredentialProvider)
                        return CosUploadService(cosService)
                    }
                }
            }
            return instance!!
        }
    }

}