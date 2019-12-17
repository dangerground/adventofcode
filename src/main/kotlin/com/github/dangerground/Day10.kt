package com.github.dangerground

import com.github.dangerground.util.Vector2D

typealias Coord = Int
typealias Count = Int


class Day10(map: String) {
    private val asteroidList = ArrayList<Asteroid>()

    private var fieldWidth = 0.0
    private var fieldHeight = 0.0

    init {
        val lines = map.split("\n")
        fieldHeight = lines.size.toDouble()
        fieldWidth = lines[0].length.toDouble()
        lines.forEachIndexed { y, line ->
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
                            if (source.blockers.containsKey(dest)) {
                                source.blockers[dest] = ArrayList()
                            }
                            source.blockers[dest]?.add(blocker)
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
                println(it)
            }
        }

        return bestFit
    }

    private fun isBlocking(source: Asteroid, dest: Asteroid, blocker: Asteroid): Boolean {
        if (crossproduct(blocker, source, dest) != 0) {
            return false
        }

        val dotproduct = dotproduct(blocker, source, dest)
        if (dotproduct < 0) {
            return false
        }

        val squaredlength = dotproduct(dest, source, dest)
        return dotproduct <= squaredlength
    }

    private fun crossproduct(blocker: Asteroid, source: Asteroid, dest: Asteroid) =
            (blocker.y - source.y) * (dest.x - source.x) - (blocker.x - source.x) * (dest.y - source.y)

    private fun dotproduct(blocker: Asteroid, source: Asteroid, dest: Asteroid) =
            (blocker.x - source.x) * (dest.x - source.x) + (blocker.y - source.y) * (dest.y - source.y)

    fun destroyAllAsteroids(x: Coord, y: Coord) {
        val start = Asteroid(x, y)
        asteroidList.remove(start)
        val circleList = buildDegreeMap(start)

        var n = 0
        while (asteroidList.isNotEmpty()) {
            circleList.toSortedMap().forEach { (degree, asteroids) ->
                if (!asteroids.isEmpty()) {
                    val toBeDestroyed = asteroids.minBy { it.distanceTo(start) }!!
                    asteroidList.remove(toBeDestroyed)
                    asteroids.remove(toBeDestroyed)
                    n++
                    println("remove $n - $degree - $toBeDestroyed) [${asteroids}]")
                }
            }
        }
    }

    private fun buildDegreeMap(start: Asteroid): HashMap<Float, ArrayList<Asteroid>> {
        val circleList = HashMap<Float, ArrayList<Asteroid>>()
        val startVector = Vector2D(0, 0 - fieldHeight.toInt())
        asteroidList.forEach {
            val tempVector = Vector2D(it.x - start.x, it.y - start.y)

            println("original $it -> $tempVector")
            val degree = Math.toDegrees(startVector.degreeTo(tempVector)).toFloat()
            val realDegree = if (tempVector.x < 0) 360 - degree else degree
            if (!circleList.containsKey(realDegree)) {
                circleList[realDegree] = ArrayList()
            }
            circleList[realDegree]?.add(it)
        }
        return circleList
    }
}

class Asteroid(x: Coord, y: Coord) : Vector2D(x, y) {
    var seeableAsteroids: Count = 0
    val blockers = HashMap<Asteroid, ArrayList<Asteroid>>()

    override fun equals(other: Any?): Boolean {
        return other is Asteroid && x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = seeableAsteroids
        result = 31 * result + blockers.hashCode()
        return result
    }
}