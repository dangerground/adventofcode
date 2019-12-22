package com.github.dangerground

import com.github.dangerground.util.Coord2D
import com.github.dangerground.util.Direction
import com.github.dangerground.util.Intcode
import kotlin.math.min
import kotlin.math.max

class RepairDroid {

    private val program = Intcode.ofFile("/day15input.txt")

    var minX = 0L
    var maxX = 0L
    var minY = 0L
    var maxY = 0L
    val map = HashMap<Coord2D, Long>()
    val nodes = HashMap<Coord2D, Node>()
    var x = 0L
    var y = 0L
    fun run() {
        var n = 110

        var direction = Direction.North
        do {
            println("($x,$y) - $direction")
            val status = remoteControl(direction)
            when (status) {
                0L -> {
                    markWall(direction)
                    direction = changeDirection(x, y, direction)
                }
                1L -> updatePosition(direction)
                //2L -> break
            }

        } while (status != 2L && n-- > 0)

        printMap()
    }

    private fun changeDirection(x: Long, y: Long, direction: Direction): Direction {
        val newDirection = when {
            !isWall(x, y-1) -> Direction.North
            !isWall(x, y+1) -> Direction.South
            !isWall(x-1, y) -> Direction.East
            !isWall(x+1, y) -> Direction.West
            else -> println("WTF")
        }

        if (newDirection != direction) {
            println("xxx")
            return direction
        }

        return when (newDirection) {
            Direction.North -> Direction.South
            Direction.South -> Direction.North
            Direction.West -> Direction.East
            Direction.East -> Direction.West
            else -> {println("WTF"); Direction.West}
        }
    }

    private fun isWall(x: Long, y: Long) : Boolean {
        val coord = Coord2D(x, y)
        if (!map.containsKey(coord)) {
            println("yy $x, $y")
            return false
        }
        return map[coord] == 0L
    }

    private fun markWall(direction: Direction) {
        var tX = x
        var tY = y
        when (direction) {
            Direction.North -> tY--
            Direction.South -> tY++
            Direction.West -> tX--
            Direction.East -> tX++
        }
        setStatus(tX, tY, 0L)
    }

    private fun updatePosition(direction: Direction) {
        when (direction) {
            Direction.North -> y--
            Direction.South -> y++
            Direction.West -> x--
            Direction.East -> x++
        }
        setStatus(x, y, 1L)
    }

    private fun remoteControl(direction: Direction): Long {
        program.inputCode = arrayOf(when (direction) {
            Direction.North -> 1L
            Direction.South -> 2L
            Direction.West -> 3L
            Direction.East -> 4L
        })
        program.runProgram()
        return program.lastOutput
    }

    private fun setStatus(x: Long, y: Long, status: Long) {
        map[Coord2D(x, y)] = getNode(x, y, status)

        minX = min(x, minX)
        minY = min(y, minY)
        maxX = max(x, maxX)
        maxY = max(y, maxY)
    }

    private fun getNode(x: Long, y: Long, status: Long, direction: Direction): Long {
        val coord = Coord2D(x, y)
        if (!nodes.containsKey(coord)) {
            nodes[coord] = Node(status)
        }


        return nodes[coord]
    }

    private fun printMap() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (x == 0L && y == 0L) {
                    print("X")
                    continue
                }
                if (x == this.x && y == this.y) {
                    print("+")
                    continue
                }
                print(when (map[Coord2D(x, y)]) {
                    0L -> "#"
                    1L -> "."
                    2L -> "o"
                    else -> " "
                })
            }
            println()
        }
    }
}

class Node(status: Long) {

}

fun main() {
    RepairDroid().run()
}