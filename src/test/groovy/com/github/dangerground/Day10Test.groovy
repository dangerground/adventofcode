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


    public static final String MAP6 = """.#....#####...#..
##...##.#####..##
##...#...#.#####.
..#.....X...###..
..#.#.....#....##"""


    @Unroll
    def "find best asteroid on map #distance"(String input, int distance) {
        when:
        def day10 = new Day10(input)

        then:
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
        def input = new File(getClass().getResource("/day10input.txt").file).text

        when:
        def day10 = new Day10(input)

        then:
        def result = day10.findBestAsteroid()

        then:
        println "distance: $result"
    }

    @Unroll
    def "find best asteroid on map #startX, #startY"(String input, int startX, startY) {
        when:
        def day10 = new Day10(input)

        then:
        day10.destroyAllAsteroids(startX, startY)

        where:
        input | startX | startY
//        MAP0  | 2      | 0
        MAP5  | 11      | 13
//        MAP6  | 8      | 3
    }

    def "find 200th destroyed asteroid"() {
        given:
        def input = new File(getClass().getResource("/day10input.txt").file).text

        when:
        def day10 = new Day10(input)

        then:
        day10.destroyAllAsteroids(11, 11)
    }
}
