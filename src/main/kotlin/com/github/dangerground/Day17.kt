package com.github.dangerground

import com.github.dangerground.util.Intcode
import java.util.*


fun main() {
    val program = Intcode.ofFile("/day17input.txt")
    program.runProgram()

    var x = 0L
    var y = 0L
    val map = TreeMap<Coord2D, Long>()
    var xMax = 0L
    var yMax = 0L
    program.outputs.forEach {
        map[Coord2D(x, y)] = it

        if (it == 10L) {
            y++
            x = 0
            yMax = y
        } else {
            x++
            xMax = x
        }

    }

    var total = 0L
    map.forEach { (c, u) ->
        val x = c.x
        val y = c.y
        if (u == 35L &&x > 0 && y > 0 && x < xMax && y < yMax) {
            if (map[Coord2D(x, y - 1)] == 35L && map[Coord2D(x, y + 1)] == 35L
                    && map[Coord2D(x - 1, y)] == 35L && map[Coord2D(x + 1, y)] == 35L) {
                print ("O")
                total += x*y

                return@forEach
            }
        }
        when (u) {
            35L -> print("#")
            46L -> print(".")
            10L -> println()
        }
    }

    println(total)
}