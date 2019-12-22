package com.github.dangerground

import com.github.dangerground.util.Direction
import com.github.dangerground.util.HelperUtil
import com.github.dangerground.util.Intcode

class Day11 {

    val robot : Intcode

    val colorMap = HashMap<Panel, Color>()
    var currentPanel = Panel(0,0)
    var currentDirection = Direction.North

    init {
        val file = Day11::class.java.getResource("/day11input.txt").readText()
        robot = Intcode(HelperUtil.convertL(file))
    }

    fun runRobot() : Int {
        colorMap[currentPanel] = Color.White
        do {
            robot.inputCode = arrayOf(getCurrentColor())
            robot.runProgram()
            colorMap[currentPanel] = when (robot.outputs[0]) {
                0L -> Color.Black
                1L -> Color.White
                else -> throw RuntimeException("Should not happen")
            }

            val turnLeft = robot.outputs[1] == 0L

            currentDirection = when (currentDirection) {
                Direction.North -> if (turnLeft) Direction.West else Direction.East
                Direction.East -> if (turnLeft) Direction.North else Direction.South
                Direction.South -> if (turnLeft) Direction.East else Direction.West
                Direction.West -> if (turnLeft) Direction.South else Direction.North
            }

            currentPanel = moveToNextPanel()
        } while (!robot.isHalted())

        return colorMap.size
    }

    fun showColorMap() {
        for (y in 0 .. 8) {
            for (x in 0 .. 40) {

                if (colorMap.containsKey(Panel(x,y)) && colorMap[Panel(x,y)] == Color.White) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    private fun moveToNextPanel(): Panel {
        return when (currentDirection) {
            Direction.North -> Panel(currentPanel.x, currentPanel.y - 1)
            Direction.East -> Panel(currentPanel.x +1, currentPanel.y)
            Direction.South -> Panel(currentPanel.x, currentPanel.y + 1)
            Direction.West -> Panel(currentPanel.x -1, currentPanel.y)
        }
    }

    private fun getCurrentColor(): Long {
        if (colorMap.containsKey(currentPanel)) {
            if (colorMap[currentPanel] == Color.White) {
                return 1L
            }
        }

        return 0L
    }

}

class Panel(val x: Int, val y: Int) {
    override fun equals(o: Any?): Boolean {
        return o is Panel && x == o.x && y == o.y
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

enum class Color {
    White,
    Black
}


fun main(args:Array<String>) {

    val program = Day11()
    program.runRobot()
    program.showColorMap()
}