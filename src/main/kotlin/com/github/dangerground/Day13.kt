package com.github.dangerground

import com.github.dangerground.util.Coord2D
import com.github.dangerground.util.Intcode

class Arcade {

    private var robot: Intcode = Intcode.ofFile("/day13input.txt")

    val map = HashMap<Coord2D, Long>()
    var blockCount = 0
    fun run() {
        robot.runProgram()
        val data = robot.outputs

        for (t in 0 until data.size step 3) {
            val x = data[t]
            val y = data[t + 1]
            val tile = data[t + 2]
            map[Coord2D(x, y)] = tile
            if (tile == 2L) {
                blockCount++
            }
        }
    }

    var currentScore = -1L
    fun playGame() {
        robot.setMem(0, 2)

        var ballX = 0L
        var paddleX = 0L
        do {
            robot.inputCode = arrayOf(if (paddleX > ballX) 1L else if (paddleX < ballX) -1L else 0L)
            robot.runProgram()
            val data = robot.outputs

            for (t in 0 until data.size step 3) {
                val x = data[t]
                val y = data[t + 1]
                val tile = data[t + 2]
                if (x == -1L && y == 0L) {
                    currentScore = tile
                } else {
                    map[Coord2D(x, y)] = tile
                    if (tile == 3L) {
                        ballX = x
                    } else if (tile == 4L) {
                        paddleX = x
                    }
                }
            }
            blockCount = map.filter { entry -> entry.value == 2L }.size

/*
            for (y in 0 until 20L) {
                for (x in 0 until 42L) {
                    print(when (map[Coord2D(x, y)]) {
                        0L -> " "
                        1L -> "|"
                        2L -> "#"
                        3L -> "-"
                        4L -> "o"
                        else -> "E"
                    })
                }
                println()
            }*/
        } while (blockCount > 0)
    }

}

fun main() {
    /*
    // part1
    val arcade1 = Arcade()
    arcade1.run()
    println(arcade1.blockCount)
     */

    // part2
    val arcade2 = Arcade()
    arcade2.playGame()
    println(arcade2.currentScore)
}
