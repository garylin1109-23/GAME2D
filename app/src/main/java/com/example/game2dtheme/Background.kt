package com.example.game2dtheme

import kotlin.math.abs
class Background (val screenW:Int){
    var x1 = 0
    var x2 = screenW

    fun Roll(){
        x1 --
        if (abs(x1) > screenW){
            x1 = 0
        }
        x2 = x1 + screenW
    }
}