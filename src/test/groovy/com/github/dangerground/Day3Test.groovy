package com.github.dangerground

import spock.lang.Specification
import spock.lang.Unroll

class Day3Test extends Specification {

    @Unroll
    def "find nearest point #line1 -> #line2 = #distance"(String line1, String line2, Integer distance) {
        given:
        def day3 = new Day3(line1.split(","), line2.split(","))

        when:
        def result = day3.findNearestIntersection()

        then:
        result == distance

        where:
        line1                                         | line2                                  | distance
        "R8,U5,L5,D3"                                 | "U7,R6,D4,L4"                          | 6
        "R75,D30,R83,U83,L12,D49,R71,U7,L72"          | "U62,R66,U55,R34,D71,R55,D58,R83"      | 159
        "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51" | "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7" | 135
    }

    def "calculate manhatten distance of puzzle input"() {
        given:
        def inputLines = new File(getClass().getResource("/day3input.txt").file).text.readLines()

        and:
        def day3 = new Day3(inputLines.get(0).split(","), inputLines.get(1).split(","))

        when:
        def result = day3.findNearestIntersection()

        then:
        println "nearest distance: ${result}"
    }


    @Unroll
    def "find minimal steps to intersection"(String line1, String line2, Integer steps) {
        given:
        def day3 = new Day3(line1.split(","), line2.split(","))

        when:
        def result = day3.findShortestStepCount()

        then:
        result == steps

        where:
        line1                                         | line2                                  | steps
        "R8,U5,L5,D3"                                 | "U7,R6,D4,L4"                          | 30
        "R75,D30,R83,U83,L12,D49,R71,U7,L72"          | "U62,R66,U55,R34,D71,R55,D58,R83"      | 610
        "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51" | "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7" | 410
    }

    def "calculate minimal steps to intersection input"() {
        given:
        def inputLines = new File(getClass().getResource("/day3input.txt").file).text.readLines()

        and:
        def day3 = new Day3(inputLines.get(0).split(","), inputLines.get(1).split(","))

        when:
        def result = day3.findShortestStepCount()

        then:
        println "shortest step count: ${result}"
    }

}
