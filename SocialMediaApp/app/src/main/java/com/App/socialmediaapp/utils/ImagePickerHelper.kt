package com.App.socialmediaapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImagePickerHelper(
    private val context: Context,
    private val pickImageLauncher: ActivityResultLauncher<Intent>,
    private val onImagePicked: (File?) -> Unit
) {

    fun registerImagePicker() {
        pickImageLauncher.launch(Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        })
    }

    private fun uriToFile(uri: Uri): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val fileName = "IMG_$timeStamp.jpg"
        val file = File(context.cacheDir, fileName)

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    }

    fun handleImageResult(result: Intent?) {
        val imageUri: Uri? = result?.data
        imageUri?.let {
            val file = uriToFile(it)
            Log.d("ImagePicker", "Image selected: ${file.absolutePath}")
            onImagePicked(file)
        }
    }
}
