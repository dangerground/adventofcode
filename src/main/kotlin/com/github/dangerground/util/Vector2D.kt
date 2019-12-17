package com.github.dangerground.util

import kotlin.math.acos
import kotlin.math.sqrt

open class Vector2D(val x: Int, val y: Int) {

    // return the Euclidean distance between the two points
    fun distanceTo(other: Vector2D): Double {
        val dx: Double = x.toDouble() - other.x
        val dy: Double = y.toDouble() - other.y
        return sqrt(dx * dx + dy * dy)
    }

    fun scalarproduct(other: Vector2D) = (x * other.x) + (y * other.y)
    fun length() = sqrt((x * x) + (y * y).toDouble())
    fun degreeTo(other: Vector2D) = acos(scalarproduct(other) / (length() * other.length()))

    override fun toString(): String {
        return "A($x,$y)"
    }
}