package com.github.dangerground

import spock.lang.Specification

class Day1Test extends Specification {

    def "CalculateMass"(int mass, int fuel) {
        when:
        def result = Day1.calculateFuelForMass(mass)

        then:
        result == fuel

        where:
        mass   | fuel
        12     | 2
        14     | 2
        1969   | 654
        100756 | 33583
    }

    def "calculate spaceship fuel for all modules required"() {
        given:
        def file = new File(getClass().getResource("/day1input.txt").file)
        def moduleMasses = file.readLines()
        def totalFuel = 0

        when:
        moduleMasses.forEach({mass ->
            totalFuel += Day1.calculateFuelForMass(Integer.parseInt(mass))
        })

        then:
        println "total fuel required $totalFuel"
    }

    def "calculate fuel for mass and fuel"(int mass, int fuel) {
        when:
        def result = Day1.calculateTotalFuelForMass(mass)

        then:
        result == fuel

        where:
        mass   | fuel
        12     | 2
        1969   | 966
        100756 | 50346
    }

    def "calculate spaceship fuel for all modules and their required fuel"() {
        given:
        def file = new File(getClass().getResource("/day1input.txt").file)
        def moduleMasses = file.readLines()
        def totalFuel = 0

        when:
        moduleMasses.forEach({mass ->
            totalFuel += Day1.calculateTotalFuelForMass(Integer.parseInt(mass))
        })

        then:
        println "total fuel required $totalFuel"
    }
}
