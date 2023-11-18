package com.aking.tiktop.httpclient.enums




enum class ResCode(private val code: Int, private val message:String) {

    /**
     * 响应成功
     */
    OK(200,"响应成功");

    fun getCode(): Int {
        return code
    }

    fun getMessage():String{
        return message
    }

}