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

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import android.graphics.Rect
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
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
                    val scale = resources.displayMetrics.density
                    val game = Game(GlobalScope, screenW, screenH,scale, this)
                    Start(m= Modifier.padding(innerPadding),game,screenW)

                    var mper1 = MediaPlayer.create(this, R.raw.lastletter)
                    mper1.start()
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

    val boyImage = arrayListOf(R.drawable.boy1, R.drawable.boy2,
        R.drawable.boy3, R.drawable.boy4, R.drawable.boy5,
        R.drawable.boy6, R.drawable.boy7, R.drawable.boy8)

    Image(
        painter = painterResource(id = boyImage[game.boy.pictNo]),
        contentDescription = "小男孩",
        modifier = Modifier
            .width(100.dp)
            .height(220.dp)
            .offset { IntOffset(game.boy.x, game.boy.y) }

    )

    //繪製病毒
    val virusImage = arrayListOf(R.drawable.virus1, R.drawable.virus2)
    Image(
        painter = painterResource(id = virusImage[game.virus.pictNo]),
        contentDescription = "病毒",
        modifier = Modifier
            .size(80.dp)
            .offset { IntOffset(game.virus.x, game.virus.y) }
            .pointerInput(Unit) { //觸控病毒往上，扣一秒鐘
                detectTapGestures(
                    onTap = {
                        game.virus.y -= 40
                        game.counter -= 25

                    }
                )
            }
    )

    Image(
        painter = painterResource(id = virusImage[game.virus.pictNo]),
        contentDescription = "病毒2",
        modifier = Modifier
            .size(80.dp)
            .offset { IntOffset(game.virus2.x, game.virus2.y) }
            .pointerInput(Unit) { //觸控病毒往上，扣一秒鐘
                detectTapGestures(
                    onTap = {
                        game.virus.y -= 40
                        game.counter -= 25

                    }
                )
            }
    )

    if (msg == "遊戲暫停" && !game.isPlaying){
        msg = "遊戲結束，按此按鍵重新開始遊戲"
    }
    Row {
        Button(
            onClick = {
                if (msg == "遊戲開始"|| msg =="遊戲繼續") {
                    msg = "遊戲暫停"
                    game.Play()
                }
                else if (msg == "遊戲暫停") {
                    msg = "遊戲繼續"
                    game.isPlaying = false
                }
                else{ //重新開始遊戲
                    msg = "遊戲暫停"
                    game.Restart()
                }

            }
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

    val activity = (LocalContext.current as? Activity)
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            onClick = {
                game.mper1.stop()
                game.mper2.stop()
                activity?.finish()
            }

        ) {

            Text("結束App")

        }

    }

}




