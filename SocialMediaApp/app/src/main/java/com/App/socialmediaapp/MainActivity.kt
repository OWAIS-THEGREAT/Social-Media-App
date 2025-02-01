package com.App.socialmediaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.App.socialmediaapp.navigation.AppNavigation
import com.App.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.App.socialmediaapp.utils.ImagePickerHelper
import com.App.socialmediaapp.viewmodels.ChatBotViewModel
import com.App.socialmediaapp.viewmodels.FollowViewModel
import com.App.socialmediaapp.viewmodels.PostViewModel
import com.App.socialmediaapp.viewmodels.ProfileViewModel
import com.App.socialmediaapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val postViewModel: PostViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()
    private val followViewModel : FollowViewModel by viewModels()
    private val chatBotViewModel : ChatBotViewModel by viewModels()

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagePickerHelper: ImagePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            imagePickerHelper.handleImageResult(data)
        }

        imagePickerHelper = ImagePickerHelper(
            context = this,
            pickImageLauncher = pickImageLauncher
        ) { file ->
            postViewModel.selectedImageFileState.value = null
            file?.let {
                postViewModel.selectedImageFileState.value = it
                postViewModel.selectedImageTimestamp.value = System.currentTimeMillis()
                Log.d("MainActivity", "Image selected: ${file.absolutePath}")
            }
        }

        setContent {
            SocialMediaAppTheme {

                val navController = rememberNavController()

                AppNavigation(navController, userViewModel, postViewModel,profileViewModel,followViewModel,pickImageLauncher,chatBotViewModel,postViewModel.selectedImageFileState)
            }
        }
    }
}

