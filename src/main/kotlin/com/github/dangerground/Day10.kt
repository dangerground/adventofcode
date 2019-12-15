package com.github.dangerground

import kotlin.math.sqrt


typealias Coord = Int
typealias Count = Int


class Day10 {
    private val asteroidList = ArrayList<Asteroid>()


    fun readMap(map: String) {
        map.split("\n").forEachIndexed { y, line ->
            line.toCharArray().forEachIndexed { x, point ->
                if (point == '#') {
                    asteroidList.add(Asteroid(x, y))
                }
            }
        }

        countSeeableAsteroids()
    }

    fun countSeeableAsteroids() {
        asteroidList.forEachIndexed { si, source ->
            var seeableAsteroids = asteroidList.size - 1
            asteroidList.forEach { dest ->
                for (blocker in asteroidList) {
                    if (!(source == dest || source == blocker || dest == blocker)) {
                        if (isBlocking(source, dest, blocker)) {
                            seeableAsteroids--
                            break
                        }
                    }
                }
            }
            source.seeableAsteroids = seeableAsteroids
        }

    }

    fun findBestAsteroid(): Int {
        var bestFit = Int.MIN_VALUE
        asteroidList.forEach {
            if (it.seeableAsteroids > bestFit) {
                bestFit = it.seeableAsteroids
            }
        }

        return bestFit
    }

    private fun isBlocking(source: Asteroid, dest: Asteroid, blocker: Asteroid): Boolean {
        val crossproduct = (blocker.y - source.y) * (dest.x - source.x) - (blocker.x - source.x) * (dest.y - source.y)
        if (crossproduct != 0) {
            return false
        }

        val dotproduct = (blocker.x - source.x) * (dest.x - source.x) + (blocker.y - source.y) * (dest.y - source.y)
        if (dotproduct < 0) {
            return false
        }

        val squaredlength = (dest.x - source.x) * (dest.x - source.x) + (dest.y - source.y) * (dest.y - source.y)
        return dotproduct <= squaredlength
    }
}

class Asteroid(val x: Coord, val y: Coord) {
    var seeableAsteroids: Count = 0

    // return the Euclidean distance between the two points
    fun distanceTo(other: Asteroid): Double {
        val dx: Double = x.toDouble() - other.x
        val dy: Double = y.toDouble() - other.y
        return sqrt(dx * dx + dy * dy)
    }

    override fun toString(): String {
        return "($y,$x;$seeableAsteroids)"
    }
}