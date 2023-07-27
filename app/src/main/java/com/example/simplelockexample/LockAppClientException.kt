package com.example.simplelockexample

enum class LockAppErrorCode {

}

class LockAppClientException(
    override val message: String? = null,
    val errorCode: LockAppErrorCode,
): IllegalStateException()