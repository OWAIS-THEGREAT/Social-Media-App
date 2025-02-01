package com.App.socialmediaapp.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.App.socialmediaapp.screens.AddPostScreen
import com.App.socialmediaapp.screens.ChatScreen
import com.App.socialmediaapp.screens.LoginScreen
import com.App.socialmediaapp.screens.OtherProfilePageScreen
import com.App.socialmediaapp.screens.ProfilePageScreen
import com.App.socialmediaapp.screens.SignupScreen
import com.App.socialmediaapp.screens.SocialMediaMainScreen
import com.App.socialmediaapp.viewmodels.ChatBotViewModel
import com.App.socialmediaapp.viewmodels.FollowViewModel
import com.App.socialmediaapp.viewmodels.PostViewModel
import com.App.socialmediaapp.viewmodels.ProfileViewModel
import com.App.socialmediaapp.viewmodels.UserViewModel
import java.io.File

@Composable
fun AppNavigation(
    navController: NavHostController,
    userViewModel: UserViewModel,
    postViewModel: PostViewModel,
    profileViewModel: ProfileViewModel,
    followViewModel: FollowViewModel,
    pickImageLauncher: ActivityResultLauncher<Intent>,
    chatBotViewModel: ChatBotViewModel,
    selectedImageFileState: MutableState<File?>
){
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginScreen(userViewModel,navController)
        }
        composable("Signup") {
            SignupScreen(userViewModel, navController)
        }
        composable("SocialMediaMainScreen") {
            SocialMediaMainScreen(userViewModel,postViewModel, navController)
        }
        composable(route = "OtherProfilePageScreen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            OtherProfilePageScreen(userId!!,userViewModel,profileViewModel,followViewModel)
        }
        composable(route = "ProfilePageScreen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ProfilePageScreen(userId!!,userViewModel,profileViewModel)
        }
        composable("AddPostScreen/{userid}",
            arguments = listOf(navArgument("userid") { type = NavType.StringType })
        ){backStackEntry ->
            val userid = backStackEntry.arguments?.getString("userid")
            AddPostScreen(
                pickImageLauncher = pickImageLauncher,
                selectedImageFile = postViewModel.selectedImageFileState.value,
                selectedImageTimestamp = postViewModel.selectedImageTimestamp.value,
                userid,
                postViewModel
            )
        }
        composable("ChatBotScreen"){
            ChatScreen(chatBotViewModel)
        }
    }
}