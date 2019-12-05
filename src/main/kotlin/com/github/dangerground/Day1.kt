package com.github.dangerground

import kotlin.math.roundToInt

class Day1 {

    companion object {

        @JvmStatic
        fun calculateFuelForMass(input: Int): Int {
            return kotlin.math.floor(input / 3.0).roundToInt() - 2
        }

        @JvmStatic
        fun calculateFuelForFuel(input: Int): Int {
            val byMass = calculateFuelForMass(input)
            if (byMass <= 0) {
                return 0
            }

            return byMass + calculateFuelForFuel(byMass)
        }

        @JvmStatic
        fun calculateTotalFuelForMass(input: Int): Int {
            val fuelForMass = calculateFuelForMass(input)
            val fuelForFuel = calculateFuelForFuel(fuelForMass)

            return fuelForMass + fuelForFuel
        }
    }
}
