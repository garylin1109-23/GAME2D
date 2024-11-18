package com.example.game2dtheme

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.game2dtheme.ui.theme.Game2DThemeTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Game2DThemeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                    var screenW = resources.displayMetrics.widthPixels
                    var screenH = resources.displayMetrics.heightPixels

                    val game = Game(GlobalScope, screenW, screenH)
                    Start(m= Modifier.padding(innerPadding),game,screenW)

                }
            }
        }
    }
}

@Composable
fun Start(m:Modifier, game:Game, screenW:Int) {
    val counter by game.state.collectAsState()
    var counter2 by remember { mutableStateOf(0) }
    var msg by remember { mutableStateOf("遊戲開始") }

    Image(
        painter = painterResource(id = R.drawable.forest),
        contentDescription = "背景圖",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .offset { IntOffset(game.background.x1, 0) }
    )

    Image(
        painter = painterResource(id = R.drawable.forest),
        contentDescription = "背景圖2",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .offset { IntOffset(game.background.x2, 0) }
    )

    Row {
        Button(
            onClick = {
                if (msg == "遊戲開始") {
                    msg = "遊戲暫停"
                    game.Play()
                } else {
                    msg = "遊戲開始"
                    game.isPlaying = false
                }

            },
            modifier = m
        ) {
            Text(text = msg)
        }

        Text(text = counter.toString(), modifier = m)

        Button(
            onClick = {
                counter2++
            },
            modifier = m
        ) {
            Text(text = "加1")
        }

        Text(text = counter2.toString(), modifier = m)
    }


}




