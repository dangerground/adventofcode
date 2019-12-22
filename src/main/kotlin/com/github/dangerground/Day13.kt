package com.github.dangerground

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

class Coord2D(val x: Long, val y: Long) : Comparable<Coord2D> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord2D

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString(): String {
        return "Coord2D(x=$x, y=$y)"
    }

    override fun compareTo(other: Coord2D): Int {
        val o1 = y.compareTo(other.y)
        if (o1 != 0) return o1
        return x.compareTo(other.x)
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
