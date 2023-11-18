package com.aking.tiktop.httpclient.upload

import com.aking.tiktop.httpclient.config.AppConfig
import com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider
import com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
import com.tencent.qcloud.core.auth.SessionQCloudCredentials

class MySessionCredentialProvider : BasicLifecycleCredentialProvider() {
    override fun fetchNewCredentials(): QCloudLifecycleCredentials {
        // 首先从您的临时密钥服务器获取包含了密钥信息的响应

        // 然后解析响应，获取临时密钥信息
        return SessionQCloudCredentials(AppConfig.SECRETID, AppConfig.SECRETKEY, "SESSIONTOKEN", 1556183496L, 1556182000L)
    }
}