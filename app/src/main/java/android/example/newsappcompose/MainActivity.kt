package android.example.newsappcompose

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.example.newsappcompose.presentation.navgraph.NavGraph
import android.example.newsappcompose.presentation.navgraph.Route
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        checkAndRequestPermissions()
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.splashCondition }
        }

        enableEdgeToEdge()

        setContent {
            val isSystemInDarkModeTheme = isSystemInDarkTheme()
            val systemController = rememberSystemUiController()

            SideEffect {
                systemController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = !isSystemInDarkModeTheme
                )
            }

            NewsAppComposeTheme {
                val startDestination = viewModel.startDestination
                NavGraph(startDestination = startDestination)
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted
            }
            shouldShowRequestPermissionRationale(permission) -> {
                // Show an explanation to the user
                requestPermissionLauncher.launch(permission)
            }
            else -> {
                // Directly request the permission
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted
        } else {
            // Permission is denied
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    NewsAppComposeTheme {
        NavGraph(startDestination = Route.OnboardingScreen.route)
    }
}