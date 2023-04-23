package edu.qc.seclass.floorstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import edu.qc.seclass.floorstore.ui.theme.FloorStoreTheme

class MainActivity2 : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FloorStoreTheme {
                MainScreen(mainViewModel = mainViewModel)
            }
        }
    }
}