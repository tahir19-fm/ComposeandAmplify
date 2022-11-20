package com.team.authwithcomposeandamplify

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amazonaws.mobile.auth.core.signin.AuthException
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.team.authwithcomposeandamplify.ui.theme.AuthWithComposeAndAmplifyTheme
import kotlin.math.log

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.configureAmplify(this)

        setContent {
            AuthWithComposeAndAmplifyTheme {
            AppNavigator()
                viewModel.fetchUser()

            }
        }
    }

@Composable
private fun AppNavigator() {
    val navController = rememberNavController()
    viewModel.navigateTo = {
        navController.navigate(it)
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("login") {
            LoginScreen(viewModel = viewModel)
        }
        composable("signUp") {
            SignUpScreen(viewModel = viewModel)
        }
        composable("verify") {
            VerificationCodeScreen(viewModel = viewModel)
        }
        composable("session") {
            SessionScreen(viewModel = viewModel)
        }
        composable("splash"){
            Splash(viewModel = viewModel)
        }
    }
}

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {

        try{
            Amplify.addPlugin( AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("tag", "Initialized Amplify");
        } catch (e:AmplifyException){
            Log.i("tag", "Initialized ${e.printStackTrace()}");
        }
        super.onCreate(savedInstanceState, persistentState)
    }

}
