package com.example.game2dtheme

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game (val scope: CoroutineScope,val screenW:Int, val screenH:Int) {
    var counter = 0
    var state = MutableStateFlow(0)
    var background = Background(screenW)
    var isPlaying = true

    fun Play() {
        scope.launch {
            //counter = 0
            isPlaying = true
            while (isPlaying) {
                delay(4)

                background.Roll()


                counter++
                state.emit(counter)
            }
        }
    }

}


