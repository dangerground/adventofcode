package com.github.dangerground

import spock.lang.Specification

class Day12Test extends Specification {

    def "test example #totalEnergy"(moons, steps, totalEnergy) {
        given:
        def day12 = new Day12(moons.toArray(new Moon[0]))

        when:
        def result = day12.simulate(steps)

        then:
        result == totalEnergy

        where:
        moons                                                                              | steps | totalEnergy
        [new Moon(-1, 0, 2), new Moon(2, -10, -7), new Moon(4, -8, 8), new Moon(3, 5, -1)] |  10 | 179
    }

    def "test puzzle input"() {
        given:

        def io = new Moon(-6, 2, -9)
        def europa = new Moon(12, -14, -4)
        def ganymede = new Moon(9, 5, -6)
        def callisto = new Moon(-1, -4, 9)

        def moons = [io, europa, ganymede, callisto]
        def day12 = new Day12(moons.toArray(new Moon[0]))

        when:
        def result = day12.simulate(1000)

        then:
        println("total Energy $result")
    }

    def "test example #totalEnergy"() {
        given:
        def moons = [new Moon(-1, 0, 2), new Moon(2, -10, -7), new Moon(4, -8, 8), new Moon(3, 5, -1)]
        def day12 = new Day12(moons.toArray(new Moon[0]))

        when:
        def result = day12.findPreviousPointInTime()

        then:
        println("total steps $result")
    }

    def "test puzzle input repeating"() {
        given:

        def io = new Moon(-6, 2, -9)
        def europa = new Moon(12, -14, -4)
        def ganymede = new Moon(9, 5, -6)
        def callisto = new Moon(-1, -4, 9)

        def moons = [io, europa, ganymede, callisto]
        def day12 = new Day12(moons.toArray(new Moon[0]))

        when:
        def result = day12.findPreviousPointInTime()

        then:
        println("total steps $result")
    }
}
