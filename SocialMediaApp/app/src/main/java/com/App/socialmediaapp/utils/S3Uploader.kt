package com.App.socialmediaapp.utils

import android.content.Context
import android.util.Log
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import java.io.File

class S3Uploader(context: Context, accessKey: String, secretKey: String) {
    private val s3Client: AmazonS3Client
    private val transferUtility: TransferUtility

    init {
        val credentials = BasicAWSCredentials(accessKey, secretKey)
        s3Client = AmazonS3Client(credentials)

        TransferNetworkLossHandler.getInstance(context)

        transferUtility = TransferUtility.builder()
            .context(context)
            .s3Client(s3Client)
            .build()
    }

    fun uploadFile(
        bucketName: String,
        keyName: String,
        file: File,
        callback: UploadCallback
    ) {
        val observer: TransferObserver = transferUtility.upload(bucketName, keyName, file)

        observer.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    val fileUrl = s3Client.getResourceUrl(bucketName, keyName)
                    callback.onSuccess(fileUrl)
                } else if (state == TransferState.FAILED) {
                    callback.onError(Exception("Upload failed"))
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val progress = (100.0 * bytesCurrent / bytesTotal)
                Log.d("S3Uploader", "Progress: $progress%")
            }

            override fun onError(id: Int, ex: Exception?) {
                callback.onError(ex ?: Exception("Unknown error"))
            }
        })
    }

    interface UploadCallback {
        fun onSuccess(fileUrl: String)
        fun onError(e: Exception)
    }
}
