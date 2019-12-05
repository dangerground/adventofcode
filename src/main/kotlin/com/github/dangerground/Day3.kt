package com.github.dangerground

import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.absoluteValue

class Day3(line1input: Array<String>, line2input: Array<String>) {

    val line1 = ArrayList<Line>()
    val line2 = ArrayList<Line>()

    init {
        convertToLine(line1input, line1)
        convertToLine(line2input, line2)
    }

    private fun convertToLine(inputs: Array<String>, lines: ArrayList<Line>) {
        var x = 0
        var y = 0

        for (input in inputs) {
            val p1 = Point(x, y)
            val num = input.substring(1).toInt()
            when (input[0]) {
                'R' -> x += num
                'L' -> x -= num
                'U' -> y += num
                'D' -> y -= num
            }
            val p2 = Point(x, y)
            lines.add(Line(p1, p2))
        }
    }

    fun findNearestIntersection(): Int {
        var nearestDistance = Int.MAX_VALUE
        findIntersections().forEach {
            val distance = it.x.absoluteValue + it.y.absoluteValue
            if (distance < nearestDistance) {
                nearestDistance = distance
            }
        }

        return nearestDistance
    }

    fun findIntersections(): ArrayList<Point> {
        val intersections = ArrayList<Point>()
        for (section1 in line1) {
            for (section2 in line2) {
                val intersection = intersection(section1, section2)
                if (intersection != null && (intersection.x != 0 || intersection.y != 0)) {
                    intersections.add(intersection)
                }
            }
        }

        return intersections
    }

    fun intersection(l1: Line, l2: Line) : Point? {
        val intersection = findHorizontalIntersection(l1, l2)
        if (intersection != null) {
            return intersection
        }

        return findHorizontalIntersection(l2, l1)
    }

    private fun findHorizontalIntersection(l1: Line, l2: Line): Point? {
        if (l1.p1.x == l1.p2.x && l2.p1.y == l2.p2.y) {

            val y1range = lineToYRange(l1)
            val x2range = lineToXRange(l2)

            val x = l1.p1.x
            val y = l2.p1.y

            if (x == 0 && y == 0) {
                return null;
            }

            if (x2range.contains(x) && y1range.contains(y)) {
                return Point(l1.p1.x, l2.p1.y)
            }
        }
        return null
    }

    private fun lineToXRange(l: Line) = IntRange(min(l.p1.x, l.p2.x), max(l.p1.x, l.p2.x))
    private fun lineToYRange(l: Line) = IntRange(min(l.p1.y, l.p2.y), max(l.p1.y, l.p2.y))

    fun findShortestStepCount() : Int {
        var shortestCount = Int.MAX_VALUE
        findIntersections().forEach {point ->
            var stepCount = 0
            stepCount += countStepsToPoint(point, line1)
            stepCount += countStepsToPoint(point, line2)

            if (stepCount < shortestCount) {
                shortestCount = stepCount
            }
        }

        return shortestCount
    }

    private fun countStepsToPoint(p: Point, l: ArrayList<Line>) : Int {
        var steps = 0
        l.forEach {
            val xRange = lineToXRange(it)
            val yRange = lineToYRange(it)

            if (xRange.contains(p.x) && yRange.contains(p.y)) {
                if (xRange.count() == 1) {
                    steps += (it.p1.y - p.y).absoluteValue
                } else {
                    steps += (it.p1.x - p.x).absoluteValue
                }
                return steps
            } else {
                if (xRange.count() == 1) {
                    steps += yRange.count() - 1
                } else {
                    steps += xRange.count() - 1
                }
            }
        }

        return -1
    }


    class Point(val x: Int, val y: Int)
    class Line(val p1: Point, val p2: Point) {
        override fun toString(): String {
            return "(${p1.x},${p1.y})-(${p2.x},${p2.y})"
        }
    }
}