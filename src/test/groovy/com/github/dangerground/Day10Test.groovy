package com.github.dangerground

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.dangerground.util.HelperUtil.convertL

class Day10Test extends Specification {


    public static final String MAP0 = """#####"""
    public static final String MAP1 = """.#..#
.....
#####
....#
...##"""

    public static final String MAP2 = """......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####"""
    public static final String MAP3 = """#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###."""

    public static final String MAP4 = """.#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#.."""

    public static final String MAP5 = """.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##"""



    @Unroll
    def "find best asteroid on map #distance"(String input, int distance) {
        given:
        def day10 = new Day10()

        when:
        day10.readMap(input)

        then:
        for (def y in 0..4) {
            for (def x in 0..4) {
                def p = false
                for (asteroid in day10.asteroidList) {
                    if (asteroid.x == x && asteroid.y == y) {
                        print asteroid.seeableAsteroids
                        p = true
                        break
                    }
                }
                if (!p) {
                    print "."
                }
            }
            println()
        }
        day10.findBestAsteroid() == distance

        where:
        input | distance
        MAP0  | 2
        MAP1  | 8
        MAP2  | 33
        MAP3  | 35
        MAP4  | 41
        MAP5  | 210
    }


    def "find best distance"() {
        given:
        def day10 = new Day10()
        def input = new File(getClass().getResource("/day10input.txt").file).text

        when:
        day10.readMap(input)

        then:
        def result = day10.findBestAsteroid()

        then:
        println "distance: $result"
    }
}
