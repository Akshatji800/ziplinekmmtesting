package com.hyperboot.ziplinekmmtesting.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyperboot.ziplinekmmtesting.WorldClockAndroid
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

@NoLiveLiterals
class WorldClockActivity : ComponentActivity() {
    private val scope = MainScope()
    private lateinit var worldClockAndroid: WorldClockAndroid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val model = worldClockAndroid.models.collectAsState()
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = model.value.label,
                    fontSize = 38.sp,
                    textAlign = TextAlign.Left,
                )
            }
        }

        worldClockAndroid = WorldClockAndroid(applicationContext, scope)
        worldClockAndroid.start()
    }

    override fun onDestroy() {
        worldClockAndroid.close()
        scope.cancel()
        super.onDestroy()
    }
}
