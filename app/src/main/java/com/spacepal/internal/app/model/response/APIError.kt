package com.spacepal.internal.app.model.response

import com.google.gson.annotations.SerializedName

class APIError {
    @SerializedName("error_description")
    private var errorDescription: String? = null

    @SerializedName("error")
    private var error: String? = null

    @SerializedName("http_code")
    private var httpCode: Int = 0

    @SerializedName("response_code")
    private var responseCode: String? = null

    @SerializedName("error_code")
    private var errorCode: String? = null

    @SerializedName("response_msg")
    private var responseMsg: String? = null

    @SerializedName("remaining_tries_allowed")
    private var remTryAllowed: Int = 0


    @SerializedName("type")
    private var errorType: String? = null

    @SerializedName("message")
    private var errorMsg: String? = null


    fun getHttpCode(): Int {
        return httpCode
    }

    fun getResponseCode(): String? {
        return responseCode
    }


    fun getErrorCode(): String? {
        return errorCode
    }

    fun setErrorCode(errorCode: String) {
        this.errorCode = errorCode
    }


    fun getRemTryAllowed(): Int {
        return remTryAllowed
    }

    fun setRemTryAllowed(remTryAllowed: Int) {
        this.remTryAllowed = remTryAllowed
    }

    fun getResponseMsg(): String? {
        return responseMsg
    }

    fun setHttpCode(httpCode: Int) {
        this.httpCode = httpCode
    }

    fun setResponseCode(responseCode: String) {
        this.responseCode = responseCode
    }

    fun setResponseMsg(responseMsg: String) {
        this.responseMsg = responseMsg
    }

    fun getErrorDescription(): String? {
        return errorDescription
    }

    fun setErrorDescription(errorDescription: String) {
        this.errorDescription = errorDescription
    }

    fun getError(): String? {
        return error
    }

    fun setError(error: String) {
        this.error = error
    }

    fun getErrorMsg(): String? {
        return errorMsg
    }
}
