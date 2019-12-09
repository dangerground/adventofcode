package com.github.dangerground

import spock.lang.Specification

import static com.github.dangerground.util.HelperUtil.convert

class Day6Test extends Specification {

    def "count orbits"(String orbits, int count) {
        given:
        def day6 = new Day6(orbits.split(",").toList())

        when:
        def result = day6.countOrbits()

        then:
        result == count

        where:
        orbits                                          | count
        "COM)B,B)C,C)D"                                 | 6
        "COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L" | 42
    }

    def "calculate distance - part 1"() {
        given:
        def inputString = new File(getClass().getResource("/day6input.txt").file).text

        and:
        def day6 = new Day6(inputString.readLines())

        when:
        def result = day6.countOrbits()

        then:
        println "orbit count: ${result}"
    }

    def "shortest orbit distance"(String orbits, int count) {
        given:
        def day6 = new Day6(orbits.split(",").toList())

        when:
        def result = day6.countMinimalDistance()

        then:
        result == count

        where:
        orbits                                          | count
        "COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L,K)YOU,I)SAN" | 4
    }

    def "calculate distance between you and santa"() {
        given:
        def inputString = new File(getClass().getResource("/day6input.txt").file).text

        and:
        def day6 = new Day6(inputString.readLines())

        when:
        def result = day6.countMinimalDistance()

        then:
        println "orbit distance: ${result}"
    }
}
