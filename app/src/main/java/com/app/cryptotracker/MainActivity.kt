package com.app.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.app.presentation.theme.CryptoTrackerTheme
import com.app.presentation.ui.cryptolist.CryptoListDestination
import dev.enro.core.compose.rememberNavigationContainer
import dev.enro.core.container.EmptyBehavior

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val container = rememberNavigationContainer(
                root = CryptoListDestination,
                emptyBehavior = EmptyBehavior.CloseParent,
            )
            CryptoTrackerTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    container.Render()
                }
            }
        }
    }
}
