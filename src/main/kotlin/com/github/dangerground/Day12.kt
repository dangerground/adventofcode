package com.github.dangerground

import kotlin.math.absoluteValue

class Day12(val moons: Array<Moon>) {

    private val initialMoons: Array<Moon>

    init {
        initialMoons = moons.copy()
    }

    fun Array<Moon>.copy() = Array(size) { get(it).clone() }

    fun simulateeTime() {
        moons.forEach { moon1 ->
            moons.forEach { moon2 ->
                moon1.applyGravity(moon2)
            }
        }
        moons.forEach {
            it.applyVelocity()
        }
    }

    fun simulate(steps: Int): Int {
        for (i in 1..steps) {
            simulateeTime()
        }

        var totalEnergy = 0
        moons.forEach { totalEnergy += it.totalEnergy() }

        return totalEnergy
    }

    fun findPreviousPointInTime(): Int {
        var n = 2500000
        var steps = 0
        do {
            steps++
            simulateeTime()
        } while (!hasTimeBeenAlready())

        return steps
    }

    private fun hasTimeBeenAlready(): Boolean {
        return initialMoons.contentDeepEquals(moons)
    }
}

class Moon(var x: Int, var y: Int, var z: Int) {
    private var vX = 0
    private var vY = 0
    private var vZ = 0

    fun clone(): Moon {
        val m = Moon(x, y, z)
        m.vX = vX
        m.vY = vY
        m.vZ = vZ

        return m
    }

    fun applyGravity(m: Moon) {
        vX += if (m.x < x) -1 else if (m.x > x) 1 else 0
        vY += if (m.y < y) -1 else if (m.y > y) 1 else 0
        vZ += if (m.z < z) -1 else if (m.z > z) 1 else 0
    }

    fun applyVelocity() {
        x += vX
        y += vY
        z += vZ
    }

    private fun potentialEnergy() = x.absoluteValue + y.absoluteValue + z.absoluteValue
    private fun kineticnergy() = vX.absoluteValue + vY.absoluteValue + vZ.absoluteValue
    fun totalEnergy() = potentialEnergy() * kineticnergy()


    override fun toString() = "pos=<x=$x, y=$y, z=$z>, vel=<x=$vX, y=$vY, z=$vZ>"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Moon

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false
        if (vX != other.vX) return false
        if (vY != other.vY) return false
        if (vZ != other.vZ) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        result = 31 * result + vX
        result = 31 * result + vY
        result = 31 * result + vZ
        return result
    }

}