package com.example.game2dtheme

import android.content.Context
import android.icu.number.Scale
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game (val scope: CoroutineScope,val screenW:Int, val screenH:Int,scale:Float, context:Context) {
    var counter = 0
    var state = MutableStateFlow(0)
    var background = Background(screenW)
    var isPlaying = true
    val boy = Boy(screenH,scale)
    val virus = Virus(screenW, screenH, scale)
    val virus2 = Virus(screenW, screenH, scale)
    var mper1 = MediaPlayer.create(context, R.raw.lastletter)
    var mper2 = MediaPlayer.create(context, R.raw.gameover)


    fun Play() {
        scope.launch {
            //counter = 0
            isPlaying = true
            virus2.y=0
            while (isPlaying) {
                mper1.start() //播放背景音樂
                delay(40)
                background.Roll()

                if (counter % 3 == 0) {
                    boy.Walk()
                    virus.Fly()
                    virus2.Fly()
                }

                //判斷是否碰撞，結束遊戲
                if(boy.getRect().intersect(virus.getRect())) {
                    isPlaying = false
                    //遊戲結束音效
                    mper1.pause()
                    mper2.start()

                }

                counter++
                state.emit(counter)
            }
        }
    }

    fun Restart(){
        virus.Reset()
        counter = 0
        isPlaying = true
        Play()
    }
}


