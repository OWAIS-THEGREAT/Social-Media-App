package com.App.socialmediaapp.screens

import android.content.Intent
import android.util.Log
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.App.socialmediaapp.remote.requestbody.PostRequestBody
import com.App.socialmediaapp.utils.Constants
import com.App.socialmediaapp.utils.S3Uploader
import com.App.socialmediaapp.viewmodels.PostViewModel
import com.App.socialmediaapp.views.AddPostPage
import kotlinx.coroutines.delay
import java.io.File


@Composable
fun AddPostScreen(
    pickImageLauncher: ActivityResultLauncher<Intent>,
    selectedImageFile: File?,
    selectedImageTimestamp: Long,
    userid: String?,
    postViewModel: PostViewModel
) {

    val uploadpost = postViewModel.uploadresponse.collectAsState(null)
    val isuploadLoading = postViewModel.uploadisLoading.collectAsState(false)
    val error = postViewModel.uploaderror.collectAsState("")

    var isS3Loading by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }

    val backPressedDispatcher = LocalContext.current as? OnBackPressedDispatcher

    val isLoading = isuploadLoading.value || isS3Loading

    Log.d("AddPostScreen", "isS3Loading: $isS3Loading, isuploadLoading: ${isuploadLoading.value}")


    LaunchedEffect(Unit) {
        backPressedDispatcher?.onBackPressed()
    }


    val s3Uploader = S3Uploader(
        context = LocalContext.current,
        accessKey = Constants.ACCESS_KEY,
        secretKey = Constants.SECRET_KEY
    )

    AddPostPage(
        selectedImageFile = selectedImageFile,
        selectedImageTimestamp = selectedImageTimestamp,
        onPickImage = {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            pickImageLauncher.launch(intent)
        },
        onUploadPost = { caption ->
            if (selectedImageFile != null) {
                val bucketName = Constants.BUCKET_NAME
                val keyName = "uploads/${selectedImageFile.name}"

                isS3Loading = true

                s3Uploader.uploadFile(
                    bucketName = bucketName,
                    keyName = keyName,
                    file = selectedImageFile,
                    callback = object : S3Uploader.UploadCallback {
                        override fun onSuccess(fileUrl: String) {
                            Log.d("AddPostScreen", "File uploaded successfully: $fileUrl")
                            isS3Loading = false
                            postViewModel.uploadPost(userid ?: "", PostRequestBody(caption = caption, image_url = fileUrl))
                        }

                        override fun onError(e: Exception) {
                            Log.e("AddPostScreen", "File upload failed", e)
                            isS3Loading = false
                        }
                    }
                )
            } else {
                Log.e("AddPostScreen", "No file selected to upload")
            }
        }
    )

    if (isLoading) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (error.value.isNotEmpty() || (uploadpost.value != null && !isuploadLoading.value)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            LaunchedEffect(error.value) {
                showSnackbar = true
                delay(2000)
                showSnackbar = false
            }

            if (showSnackbar) {
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    content = {
                        Text(
                            text = if (error.value.isNotEmpty()) error.value else "Post uploaded successfully!",
                            color = Color.White
                        )
                    },
                    containerColor = if (error.value.isNotEmpty()) Color.Red else Color.Green
                )
            }
        }
    }
}
